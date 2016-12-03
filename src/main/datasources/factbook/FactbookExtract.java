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

/**
 * This class is used to extract data from the CIA Factbook data source
 */
public class FactbookExtract {

    private String fieldURL = null;
    private String[] values = null;

    /**
     * Constructor
     * @param fieldURL a String containing the URL pointing to the location of the html file containing the data of
     *                 interest. The CIA Factbook holds all of its
     */
    public FactbookExtract(String fieldURL) {
        this.fieldURL = fieldURL;
    }

    /**
     * This method finds and extracts the section data in a field html file corresponding to a FIPS country code.
     * @param fips is a country code referring to a country
     * @return a String containing the section of data corresponding to the FIPS country code
     * @throws IOException
     */
    public String connect(String fips) throws IOException {
        if(fips.isEmpty()==false) {
            String value = null;
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

    /**
     * This method extracts latitude and longitude corresponding to a FIPS country code
     * @param fips is a country code referring to a country
     * @return a String array containing the latitude (index: 0) and longitude (index: 1)
     * @throws IOException
     */
    public String[] extractCountryLoc(String fips) throws IOException{
        String value = connect(fips);
        String lat = null;
        String latREGEX = "(\\d+\\s\\d+\\s(N|S)){1}";
        String lon = null;
        String lonREGEX = "(\\d+\\s\\d+\\s(E|W)){1}";
        Pattern pattern;
        Matcher matcher;
        String[] pair = new String[]{"0","0"};
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

    /**
     * This method extracts demographic data corresponding to a FIPS country code
     * @param fips is a country code referring to a country
     * @return an ArrayList of String arrays containing demographic data. The String arrays are of length with length 2
     *               (index 0: demographic, index 1: population percent)
     * @throws IOException
     */
    public List<String[]> extractDemogr(String fips) throws IOException {
        String value = connect(fips);
        Pattern pattern;
        Matcher matcher;
        String[] pair = new String[]{"0","0"};
        List<String[]> pairs = new ArrayList<>();
        String note = ".*(?=\\s*(note|overseas))";
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
            String demogr = null;
            String demogrREGEX = "((\\D+\\s+)+)(?=\\d+\\.?\\d*%)";
            String percent = null;
            String percentREGEX = "\\d+\\.?\\d*(?=%)";
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