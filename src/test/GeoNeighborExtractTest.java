package test;

import main.datasources.geonames.GeoInfoCountry;
import main.datasources.geonames.GeoInfoExtract;
import main.datasources.geonames.GeoNeighborCountry;
import main.datasources.geonames.GeoNeighborExtract;
import javax.xml.bind.JAXBException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class GeoNeighborExtractTest {
    public static void main(String[] args) throws JAXBException, MalformedURLException {
        List<GeoInfoCountry> countryDatas = new GeoInfoExtract().extractInfo();
        Iterator<GeoInfoCountry> it = countryDatas.iterator();

        List<String> count = new ArrayList<>();
        while(it.hasNext()){
            count.clear();
            GeoInfoCountry next = it.next();
            List<GeoNeighborCountry> neighbors = new GeoNeighborExtract().extractNeighbor(next.getCode_a2());
            Iterator<GeoNeighborCountry> n = neighbors.iterator();
            while(n.hasNext()){
                GeoNeighborCountry nextNeighbor = n.next();
                count.add(nextNeighbor.getNeighbor_code());
            }
            System.out.println(next.getCountry_name());
            System.out.println(count.size());
        }
    }
}
