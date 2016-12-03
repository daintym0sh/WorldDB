package main.datasources.geonames;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * The root element in the Geonames Neighbors XML REST response, returned by the Geonames API
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "geonames")
public class GeoNeighborRoot {
    @XmlElement(name = "geoname", type = GeoNeighborCountry.class)
    private List<GeoNeighborCountry> Neighbors = new ArrayList<>();

    public GeoNeighborRoot(){
    }
    
    public GeoNeighborRoot(List<GeoNeighborCountry> Neighbors) {
        this.Neighbors = Neighbors;
    }
        
    public List<GeoNeighborCountry> getNeighbor() {
        return Neighbors;
    }
}
