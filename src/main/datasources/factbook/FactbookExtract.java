package main.datasources.factbook;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FactbookExtract {

    private String fieldURL = null;
    private String[] values = null;
    private List<String[]> pairs;
    private Pattern pattern = null;
    private Matcher matcher = null;
    private String latREGEX = "(\\d+\\s\\d+\\s(N|S)){1}";
    private String lonREGEX = "(\\d+\\s\\d+\\s(E|W)){1}";
    private String lat = null;
    private String lon = null;;
    private String value = null;;
    private String demogrREGEX = "((\\D+\\s+)+)(?=\\d+\\.?\\d*%)";
    private String percentREGEX = "\\d+\\.?\\d*(?=%)";
    private String demogr = null;
    private String percent = null;
    private String[] pair = new String[]{"0","0"};
    private String note = ".*(?=\\s*(note|overseas))";

    public FactbookExtract(String fieldURL) {
        this.fieldURL = fieldURL;
    }

    public String connect(String fips) throws IOException {
        if(fips.isEmpty()==false) {
            File input = new File(fieldURL);
            Document doc = Jsoup.parse(input, "UTF-8");
            Elements tables = doc.select("table[id=" + fips + "]");
            if (tables.size() > 0) {
                value = tables.select("tr:eq(1)").select("td:eq(1)").get(0).text();
                return value;
            } else {
                return null;
            }
        }
        else {
            return null;
        }
    }
    
    public String[] extractCountryLoc(String fips) throws IOException{
        value = connect(fips);
        if(value!=null){
            pattern = Pattern.compile(latREGEX);
            matcher = pattern.matcher(value);
            while(matcher.find()){
                lat = matcher.group();
                break;
            } 
            if(lat.contains("N")){
                lat = lat.replaceAll(" N","");
                lat = lat.replaceAll(" ",".");
            }
            else{
                lat = lat.replaceAll(" S","");
                lat = lat.replaceAll(" ",".");
                lat = "-" + lat;
            }
            pattern = Pattern.compile(lonREGEX);
            matcher = pattern.matcher(value);
             while(matcher.find()){
                lon = matcher.group();
                break;
            }
            if(lon.contains("E")){
                lon = lon.replaceAll(" E","");
                lon = lon.replaceAll(" ",".");
            }
            else{
                lon = lon.replaceAll(" W","");
                lon = lon.replaceAll(" ",".");
                lon = "-" + lon;
            }
            pair = new String[]{lat,lon};
            return pair;
        }
        else{
            return pair;
        }        
    }

    public List<String[]> extractDemogr(String fips) throws IOException {
        value = connect(fips);
        pairs = new ArrayList<>();
        if(value!=null){
            value = value.replaceAll("\\(unknown\\)","unknown");
            value = value.replaceAll("\\(.+?\\)","");
            value = value.replaceAll("\\[.+?\\]","");
            value = value.replaceAll("\\d+\\.?\\d*%-","");
            value = value.replaceAll("less than ","");
            if(value.contains("note: ")|value.contains("overseas departments:")){
                pattern = Pattern.compile(note);
                matcher = pattern.matcher(value);
                while(matcher.find()){
                    value = matcher.group();
                    break;
                }
            }
            values = value.split(", ");
            List<String> count = new ArrayList<>();
            for(String e : values){
                if(e.contains("%")==true&&e.length()<75){
                    pattern = Pattern.compile(demogrREGEX);
                    matcher = pattern.matcher(e);
                    while(matcher.find()){
                        demogr = matcher.group();
                        demogr = demogr.trim();
                        break;
                    }
                    pattern = Pattern.compile(percentREGEX);
                    matcher = pattern.matcher(e);
                    while(matcher.find()){
                        percent = matcher.group();
                        break;
                    }
                }
                else if(e.length()<20){
                    demogr = e;
                    demogr = demogr.trim();
                    percent = "0";
                }
                pair = new String[] {demogr,percent};
                if(count.contains(demogr)==false && demogr.matches(".*\\d+.*")==false) {
                    pairs.add(pair);
                }
                count.add(demogr);
            }
            return pairs;
        }
        else {
            return pairs;
        }
    }
}