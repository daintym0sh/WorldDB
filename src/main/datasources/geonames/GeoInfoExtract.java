package main.datasources.geonames;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class GeoInfoExtract {

    private final String restRequest = "http://api.geonames.org/countryInfo?username=meierj15";

    public List<GeoInfoCountry> extractInfo() throws JAXBException, MalformedURLException{
        JAXBContext jc = JAXBContext.newInstance(GeoInfoRoot.class);
        Unmarshaller u = jc.createUnmarshaller();
        URL url = new URL(restRequest);
        GeoInfoRoot data = (GeoInfoRoot) u.unmarshal(url);
        return data.getCountryData();
    }  
}