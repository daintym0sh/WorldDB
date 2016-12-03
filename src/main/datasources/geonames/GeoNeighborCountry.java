/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.datasources.geonames;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The data of interest from a geoname element in the Geonames Neighbors XML REST response, returned by the Geonames API
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "geoname")
public class GeoNeighborCountry {
    
    @XmlElement(name="countryCode")
    private String neighbor_code;   

    public String getNeighbor_code() {
        return neighbor_code;
    }
    
}
