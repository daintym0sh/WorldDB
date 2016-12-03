package main.datasources.geonames;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The data of interest from a country element in the Geonames Countryinfo XML REST response, returned by the Geonames API
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "country")
public class GeoInfoCountry {

    @XmlElement(name="countryName")
    private String country_name;
    @XmlElement(name="countryCode")
    private String code_a2;
    @XmlElement(name="isoAlpha3")
    private String code_a3;
    @XmlElement(name="isoNumeric")
    private String code_num;
    @XmlElement(name="areaInSqKm")
    private double area;
    @XmlElement(name="capital")
    private String capital;
    @XmlElement(name="fipsCode")
    private String fips;
    @XmlElement(name="continentName")
    private String continent;
    @XmlElement(name="currencyCode")
    private String currency;

    public String getCountry_name() {
        return country_name;
    }

    public String getCode_a2() {
        return code_a2;
    }

    public String getCode_a3() {
        return code_a3;
    }

    public String getCode_num() {
        return code_num;
    }

    public double getArea() {
        return area;
    }

    public String getCapital() {
        return capital;
    }

    public String getFips() {
        return fips;
    }

    public String getContinent() {
        return continent;
    }

    public String getCurrency() {
        return currency;
    }
}
