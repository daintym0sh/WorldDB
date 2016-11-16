package main.datasources.geonames;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class GeoNeighborExtract {
    public List<GeoNeighborCountry> extractNeighbor(String code_a2) throws JAXBException, MalformedURLException {
        JAXBContext jc = JAXBContext.newInstance(GeoNeighborRoot.class);
        Unmarshaller u = jc.createUnmarshaller();
        URL url = new URL("http://api.geonames.org/neighbours?country=" + code_a2 + "&username=meierj15");
        GeoNeighborRoot neighbors = (GeoNeighborRoot) u.unmarshal(url);
        return neighbors.getNeighbor();
    }
}
