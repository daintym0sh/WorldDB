package main.datasources.geonames;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "geonames")
/**
 * The root element in the Geonames Countryinfo XML, returned by the Geonames API
 */
public class GeoInfoRoot {
     
    @XmlElement(name = "country", type = GeoInfoCountry.class)
    private List<GeoInfoCountry> countryDatas = new ArrayList<>();
    
    public GeoInfoRoot(){
    }

    public GeoInfoRoot(List<GeoInfoCountry> countryDatas) {
        this.countryDatas = countryDatas;
    }
 
    public List<GeoInfoCountry> getCountryData() {
        return countryDatas;
    }
}
