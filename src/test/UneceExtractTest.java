package test;

import main.datasources.geonames.GeoInfoCountry;
import main.datasources.geonames.GeoInfoExtract;
import main.datasources.unece.UneceCity;
import main.datasources.unece.UneceExtract;
import main.datasources.worldbank.WorldBankData;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

class UneceExtractTest {
    public static void main(String[] args) throws IOException, JAXBException {
        UneceExtract c = new UneceExtract();
        List<UneceCity> cities = c.extractCity();
        System.out.println("done");
        List<GeoInfoCountry> countryData = new GeoInfoExtract().extractInfo();
        Iterator<GeoInfoCountry> it = countryData.iterator();
        while(it.hasNext()) {
            GeoInfoCountry info = it.next();
            String code_a2 = info.getCode_a2();
            List<UneceCity> target = cities.stream().filter(uneceCity -> uneceCity.getCode_a2().contains(code_a2)).collect(Collectors.toList());
            System.out.println(target.size());
        }
    }
}
