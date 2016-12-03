package main.datasources.geonames;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * The class is used to unmarshall XML data from the Geonames REST webservice
 */
public class GeoNeighborExtract {

    /**
     * This method unmarshalls the Geonames XML data regarding country neighbors into GeoNeighborCountry objects and
     * returns them in an ArrayList
     * @return an ArrayList of GeoInfoCountry objects
     * @param code_a2 is an ISO-3611-1 country code
     * @return
     * @throws JAXBException
     * @throws MalformedURLException
     */
    public List<GeoNeighborCountry> extractNeighbor(String code_a2) throws JAXBException, MalformedURLException {
        JAXBContext jc = JAXBContext.newInstance(GeoNeighborRoot.class);
        Unmarshaller u = jc.createUnmarshaller();
        URL url = new URL("http://api.geonames.org/neighbours?country=" + code_a2 + "&username=meierj15");
        GeoNeighborRoot neighbors = (GeoNeighborRoot) u.unmarshal(url);
        return neighbors.getNeighbor();
    }
}
