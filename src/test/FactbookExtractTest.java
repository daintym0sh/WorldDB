package test;

import main.datasources.factbook.FactbookExtract;
import main.datasources.geonames.GeoInfoCountry;
import main.datasources.geonames.GeoInfoExtract;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

class FactbookExtractTest {
    public static void main (String[] args) throws IOException, JAXBException {

        List<GeoInfoCountry> countryData = new GeoInfoExtract().extractInfo();
        Iterator<GeoInfoCountry> it = countryData.iterator();
        String location = "data/factbook/fields/2011.html";
        String ethn = "data/factbook/fields/2075.html";
        String lang = "data/factbook/fields/2098.html";
        String rel = "factbook/fields/2122.html";
        FactbookExtract loc = new FactbookExtract(location);
        FactbookExtract e = new FactbookExtract(ethn);
        FactbookExtract l = new FactbookExtract(lang);
        FactbookExtract r = new FactbookExtract(rel);

        //while(it.hasNext()){
            //String fips = it.next().getFips();
            for(String[] p : e.extractDemogr("gb")){
                System.out.println(p[0] + " " + p[1]);
            }
        //}
    }
}
