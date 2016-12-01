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

/**
 * Builds an ArrayList of Country objects from extracted data
 */
public class CountryBuild {
    /**
     *
     * @return the ArrayList of Country objects, containing all the data needed to update the database
     * @throws JAXBException
     * @throws IOException
     */
    public ArrayList<Country> extractCountry() throws JAXBException, IOException {
        ArrayList<Country> countries = new ArrayList<>();
        List<GeoInfoCountry> countryData = new GeoInfoExtract().extractInfo();
        Iterator<GeoInfoCountry> it = countryData.iterator();
        FactbookExtract loc = new FactbookExtract("data/factbook/fields/2011.html");
        List<UneceCity> cities = new UneceExtract().extractCity();
        FactbookExtract ethn = new FactbookExtract("data/factbook/fields/2075.html");
        FactbookExtract lang = new FactbookExtract("data/factbook/fields/2098.html");
        FactbookExtract rel = new FactbookExtract("data/factbook/fields/2122.html");
        List<WorldBankData> population = new WorldBankExtract("http://api.worldbank.org/countries/all/indicators/SP.POP.TOTL?format=xml&per_page=20000").extractData();
        List<WorldBankData> growth = new WorldBankExtract("http://api.worldbank.org/countries/all/indicators/SP.POP.GROW?format=xml&per_page=20000").extractData();
        List<WorldBankData> mortality = new WorldBankExtract("http://api.worldbank.org/countries/all/indicators/SP.DYN.CDRT.IN?format=xml&per_page=20000").extractData();
        List<WorldBankData> infl = new WorldBankExtract("http://api.worldbank.org/countries/all/indicators/NY.GDP.DEFL.KD.ZG?format=xml&per_page=20000").extractData();
        List<WorldBankData> unempl = new WorldBankExtract("http://api.worldbank.org/countries/all/indicators/SL.UEM.TOTL.ZS?format=xml&per_page=20000").extractData();
        List<WorldBankData> gdp = new WorldBankExtract("http://api.worldbank.org/countries/all/indicators/NY.GDP.MKTP.CD?format=xml&per_page=20000").extractData();
        List<WorldBankData> agGdp = new WorldBankExtract("http://api.worldbank.org/countries/all/indicators/NV.AGR.TOTL.ZS?format=xml&per_page=20000").extractData();
        List<WorldBankData> indGdp = new WorldBankExtract("http://api.worldbank.org/countries/all/indicators/NV.IND.TOTL.ZS?format=xml&per_page=20000").extractData();
        List<WorldBankData> servGdp = new WorldBankExtract("http://api.worldbank.org/countries/all/indicators/NV.SRV.TETC.ZS?format=xml&per_page=20000").extractData();
        while(it.hasNext()) {
            GeoInfoCountry info = it.next();
            String code_a2 = info.getCode_a2();
            String fips = info.getFips();
            String[] location = loc.extractCountryLoc(fips);
            List<UneceCity> c = cities.stream().filter(uneceCity -> uneceCity.getCode_a2().contains(code_a2)).collect(Collectors.toList());
            List<GeoNeighborCountry> neighbors = new GeoNeighborExtract().extractNeighbor(code_a2);
            CountryDemogr demographicData = new CountryDemogr(
                    ethn.extractDemogr(fips),
                    lang.extractDemogr(fips),
                    rel.extractDemogr(fips));
            CountryTime timeData = new CountryTime(
                    population.stream().filter(WorldBankData -> WorldBankData.getCode_a2().contains(code_a2)).collect(Collectors.toList()),
                    growth.stream().filter(WorldBankData -> WorldBankData.getCode_a2().contains(code_a2)).collect(Collectors.toList()),
                    mortality.stream().filter(WorldBankData -> WorldBankData.getCode_a2().contains(code_a2)).collect(Collectors.toList()),
                    infl.stream().filter(WorldBankData -> WorldBankData.getCode_a2().contains(code_a2)).collect(Collectors.toList()),
                    unempl.stream().filter(WorldBankData -> WorldBankData.getCode_a2().contains(code_a2)).collect(Collectors.toList())
            );
            CountryGDP gdpData = new CountryGDP(
                    gdp.stream().filter(WorldBankData -> WorldBankData.getCode_a2().contains(code_a2)).collect(Collectors.toList()),
                    agGdp.stream().filter(WorldBankData -> WorldBankData.getCode_a2().contains(code_a2)).collect(Collectors.toList()),
                    indGdp.stream().filter(WorldBankData -> WorldBankData.getCode_a2().contains(code_a2)).collect(Collectors.toList()),
                    servGdp.stream().filter(WorldBankData -> WorldBankData.getCode_a2().contains(code_a2)).collect(Collectors.toList())
            );
            Country country = new Country(info,location,c,neighbors,demographicData,timeData,gdpData);
            countries.add(country);
        }
        return countries;
    }
}
