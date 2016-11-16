package test;

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
            System.out.println(count.getGdpData().getGdp().size());
            System.out.println(count.getGdpData().getServGdp().size());
            System.out.println(count.getGdpData().getAgGdp().size());
            System.out.println(count.getTimeData().getInfl().size());
            System.out.println(count.getTimeData().getUnempl().size());
        }
    }
}
