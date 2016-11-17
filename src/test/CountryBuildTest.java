package test;

import main.datasources.worldbank.WorldBankData;
import main.domain.Country;
import main.domain.CountryBuild;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class CountryBuildTest {
    public static void main(String[] args) throws JAXBException, IOException {
        List<Country> c = new CountryBuild().extractCountry();
        Iterator<Country> it = c.iterator();
        while(it.hasNext()){
            Country count = it.next();
            List<WorldBankData> g = (count.getGdpData().getGdp());
            Iterator<WorldBankData> gIt = g.iterator();
            System.out.println(g.size());
            List<WorldBankData> a = (count.getGdpData().getAgGdp());
            //List<WorldBankData> i = (count.getGdpData().getIndGdp());
            //List<WorldBankData> s = (count.getGdpData().getServGdp());
            //List<WorldBankData> inf = (count.getTimeData().getInfl());
            //List<WorldBankData> u = (count.getTimeData().getUnempl());
           while(gIt.hasNext()){
                WorldBankData gdp = gIt.next();
                String country_code = gdp.getCode_a2();
                int year = gdp.getDate();
                double gdpValue = gdp.getValue();
                double aValue = a.stream()
                        .filter(WorldBankData -> WorldBankData.getDate()==gdp.getDate())
                        .findFirst().orElse(new WorldBankData()).getValue();
                System.out.println(year + " " + gdpValue + " " + aValue + " " + country_code);
            }

        }
    }
}
