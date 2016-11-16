package test;

import main.datasources.geonames.GeoInfoCountry;
import main.datasources.geonames.GeoInfoExtract;

import javax.xml.bind.JAXBException;
import java.net.MalformedURLException;
import java.util.List;

/**
 * Created by John on 11/14/2016.
 */
public class GeoInfoExtractTest {
    public static void main(String[] args) throws JAXBException, MalformedURLException {
        List<GeoInfoCountry> countryData = new GeoInfoExtract().extractInfo();
        System.out.println(countryData.size());
    }
}
