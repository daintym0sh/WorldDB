package main.domain;

import main.datasources.geonames.GeoInfoCountry;
import main.datasources.unece.UneceCity;

import java.util.List;

public class Country {
    private GeoInfoCountry info;
    private String[] location;
    private List<UneceCity> cities;
    private CountryDemogr demogrData;
    private CountryTime timeData;
    private CountryGDP gdpData;

    public Country(GeoInfoCountry info,
                   String[] location,
                   List<UneceCity> cities,
                   CountryDemogr demogrData,
                   CountryTime timeData,
                   CountryGDP gdpData) {
        this.info = info;
        this.location = location;
        this.cities = cities;
        this.demogrData = demogrData;
        this.timeData = timeData;
        this.gdpData = gdpData;
    }

    public GeoInfoCountry getInfo() {
        return info;
    }

    public String[] getLocation() {
        return location;
    }

    public List<UneceCity> getCities() {
        return cities;
    }

    public CountryDemogr getDemogrData() {
        return demogrData;
    }

    public CountryTime getTimeData() {
        return timeData;
    }

    public CountryGDP getGdpData() {
        return gdpData;
    }
}
