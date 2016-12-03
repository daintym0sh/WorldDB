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
public class GeoInfoExtract {

    private static String restRequest = "http://api.geonames.org/countryInfo?username=meierj15";

    /**
     * This method unmarshalls the Geonames XML data into GeoInfoCountry objects and returns them in an ArrayList
     * @return an ArrayList of GeoInfoCountry objects
     * @throws JAXBException
     * @throws MalformedURLException
     */
    public List<GeoInfoCountry> extractInfo() throws JAXBException, MalformedURLException{
        JAXBContext jc = JAXBContext.newInstance(GeoInfoRoot.class);
        Unmarshaller u = jc.createUnmarshaller();
        URL url = new URL(restRequest);
        GeoInfoRoot data = (GeoInfoRoot) u.unmarshal(url);
        return data.getCountryData();
    }  
}

