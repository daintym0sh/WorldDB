package main.domain;

import main.datasources.factbook.FactbookExtract;
import main.datasources.geonames.GeoInfoCountry;
import main.datasources.geonames.GeoInfoExtract;
import main.datasources.geonames.GeoNeighborCountry;
import main.datasources.geonames.GeoNeighborExtract;
import main.datasources.unece.UneceCity;
import main.datasources.unece.UneceExtract;
import main.datasources.worldbank.WorldBankData;
import main.datasources.worldbank.WorldBankExtract;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class CountryBuild {
    private Country country;
    private ArrayList<Country> countries = new ArrayList<>();
    private List<GeoInfoCountry> countryData;
    private FactbookExtract loc;
    private List<UneceCity> cities;
    private List<GeoNeighborCountry> neighbors;
    private FactbookExtract ethn;
    private FactbookExtract lang;
    private FactbookExtract rel;
    private List<WorldBankData> population;
    private List<WorldBankData> growth;
    private List<WorldBankData> mortality;
    private List<WorldBankData> infl;
    private List<WorldBankData> unempl;
    private List<WorldBankData> gdp;
    private List<WorldBankData> agGdp;
    private List<WorldBankData> indGdp;
    private List<WorldBankData> servGdp;
    private CountryDemogr demographicData;
    private CountryTime timeData;
    private CountryGDP gdpData;

    public ArrayList<Country> extractCountry() throws JAXBException, IOException {
        countryData = new GeoInfoExtract().extractInfo();
        loc = new FactbookExtract("data/factbook/fields/2011.html");
        cities = new UneceExtract().extractCity();
        ethn = new FactbookExtract("data/factbook/fields/2075.html");
        lang = new FactbookExtract("data/factbook/fields/2098.html");
        rel = new FactbookExtract("data/factbook/fields/2122.html");
        population = new WorldBankExtract("http://api.worldbank.org/countries/all/indicators/SP.POP.TOTL?format=xml").extractData();
        growth = new WorldBankExtract("http://api.worldbank.org/countries/all/indicators/SP.POP.GROW?format=xml").extractData();
        mortality = new WorldBankExtract("http://api.worldbank.org/countries/all/indicators/SP.DYN.CDRT.IN?format=xml&per_page=20000").extractData();
        infl = new WorldBankExtract("http://api.worldbank.org/countries/all/indicators/NY.GDP.DEFL.KD.ZG?format=xml").extractData();
        unempl = new WorldBankExtract("http://api.worldbank.org/countries/all/indicators/SL.UEM.TOTL.ZS?format=xml").extractData();
        gdp = new WorldBankExtract("http://api.worldbank.org/countries/all/indicators/NY.GDP.MKTP.CD?format=xml").extractData();
        agGdp = new WorldBankExtract("http://api.worldbank.org/countries/all/indicators/NV.AGR.TOTL.ZS?format=xml").extractData();
        indGdp = new WorldBankExtract("http://api.worldbank.org/countries/all/indicators/NV.IND.TOTL.ZS?format=xml").extractData();
        servGdp = new WorldBankExtract("http://api.worldbank.org/countries/all/indicators/NV.SRV.TETC.ZS?format=xml").extractData();
        Iterator<GeoInfoCountry> it = countryData.iterator();
        while(it.hasNext()) {
            GeoInfoCountry info = it.next();
            String code_a2 = info.getCode_a2();
            String fips = info.getFips();
            String[] location = loc.extractCountryLoc(fips);
            List<UneceCity> c = cities.stream().filter(uneceCity -> uneceCity.getCode_a2().contains(code_a2)).collect(Collectors.toList());
            neighbors = new GeoNeighborExtract().extractNeighbor(code_a2);
            demographicData = new CountryDemogr(
                    ethn.extractDemogr(fips),
                    lang.extractDemogr(fips),
                    rel.extractDemogr(fips));
            timeData = new CountryTime(
                    population.stream().filter(WorldBankData -> WorldBankData.getCode_a2().contains(code_a2)).collect(Collectors.toList()),
                    growth.stream().filter(WorldBankData -> WorldBankData.getCode_a2().contains(code_a2)).collect(Collectors.toList()),
                    mortality.stream().filter(WorldBankData -> WorldBankData.getCode_a2().contains(code_a2)).collect(Collectors.toList()),
                    infl.stream().filter(WorldBankData -> WorldBankData.getCode_a2().contains(code_a2)).collect(Collectors.toList()),
                    unempl.stream().filter(WorldBankData -> WorldBankData.getCode_a2().contains(code_a2)).collect(Collectors.toList())
            );
            gdpData = new CountryGDP(
                    gdp.stream().filter(WorldBankData -> WorldBankData.getCode_a2().contains(code_a2)).collect(Collectors.toList()),
                    agGdp.stream().filter(WorldBankData -> WorldBankData.getCode_a2().contains(code_a2)).collect(Collectors.toList()),
                    indGdp.stream().filter(WorldBankData -> WorldBankData.getCode_a2().contains(code_a2)).collect(Collectors.toList()),
                    servGdp.stream().filter(WorldBankData -> WorldBankData.getCode_a2().contains(code_a2)).collect(Collectors.toList())
            );
            country = new Country(info,location,cities,neighbors,demographicData,timeData,gdpData);
            countries.add(country);
        }
        return countries;
    }
}
