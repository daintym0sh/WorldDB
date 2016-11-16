package test;

import main.datasources.geonames.GeoInfoCountry;
import main.datasources.geonames.GeoInfoExtract;
import main.datasources.worldbank.WorldBankData;
import main.datasources.worldbank.WorldBankExtract;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class WorldBankExtractTest {
    public static void main(String[] args) throws JAXBException, IOException, SQLException{

        List<GeoInfoCountry> countryData = new GeoInfoExtract().extractInfo();
        List<String> countries = new ArrayList<>();
        for(GeoInfoCountry c : countryData){
            countries.add(c.getCode_a2());
        }
        List<WorldBankData> countryPop = new WorldBankExtract("http://api.worldbank.org/countries/all/indicators/SP.POP.TOTL?format=xml&per_page=20000").extractData();
        List<WorldBankData> countryDeath = new WorldBankExtract("http://api.worldbank.org/countries/all/indicators/SP.DYN.CDRT.IN?format=xml&per_page=20000").extractData();
        List<WorldBankData> countryGrowth = new WorldBankExtract("http://api.worldbank.org/countries/all/indicators/SP.POP.GROW?format=xml&per_page=20000").extractData();
        Iterator<WorldBankData> pop = countryPop.iterator();
        Iterator<WorldBankData> death = countryDeath.iterator();
        Iterator<WorldBankData> birth = countryGrowth.iterator();
        while(pop.hasNext()) {
            WorldBankData popValue = pop.next();
            //  WorldBankData deathValue = death.next();
            //WorldBankData growthValue = birth.next();
            if (countries.contains(popValue.getCode_a2())) {
                int year = popValue.getDate();
                String code_a2 = popValue.getCode_a2();
                double population = popValue.getValue();
                System.out.println(year + code_a2 + population);
                // double growthRate = growthValue.getValue();
                // double mortalityRate = deathValue.getValue();
            }
        }
    }
}
