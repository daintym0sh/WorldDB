package test;

import main.datasources.unece.UneceCity;
import main.datasources.unece.UneceExtract;
import main.datasources.worldbank.WorldBankData;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

class UneceExtractTest {
    public static void main(String[] args) throws IOException, JAXBException {
        UneceExtract c = new UneceExtract();
        List<UneceCity> cities = c.extractCity();
        List<UneceCity> target;
        System.out.println("done");
        target = cities.stream().filter(uneceCity -> uneceCity.getCode_a2().contains("AD")).collect(Collectors.toList());
        System.out.println(target.size());
    }
}
