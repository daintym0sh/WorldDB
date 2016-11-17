package main;

import main.doa.*;
import main.domain.Country;
import main.domain.CountryBuild;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Runner {
    public static void main(String[] args) throws SQLException, ParserConfigurationException, JAXBException, SAXException, IOException, ClassNotFoundException {
        List<Country> countries = new CountryBuild().extractCountry();
        /*CountryDOA countryDoa = new CountryDOA();
        countryDoa.connect();
        countryDoa.countryUpdate(countries);
        EthnicityDOA ethnicityDOA = new EthnicityDOA();
        ethnicityDOA.connect();
        ethnicityDOA.ethnicityUpdate(countries);
        LanguageDOA languageDOA = new LanguageDOA();
        languageDOA.connect();
        languageDOA.ethnicityUpdate(countries);
        ReligionDOA religionDOA = new ReligionDOA();
        religionDOA.connect();
        religionDOA.religionUpdate(countries);*/
        //CityDOA cityDOA = new CityDOA();
        //cityDOA.connect();
        //cityDOA.cityUpdate(countries);
        //EconomyDOA economyDOA = new EconomyDOA();
        //economyDOA.connect();
        //economyDOA.economyUpdate(countries);
        //PopulationDOA populationDOA = new PopulationDOA();
        //populationDOA.connect();
        //populationDOA.PopulationUpdate(countries);
        //NeighborsDOA neighborsDOA = new NeighborsDOA();
        //neighborsDOA.connect();
        //neighborsDOA.NeighborsUpdate(countries);
        ContinentDOA continentDOA = new ContinentDOA();
        continentDOA.connect();
        continentDOA.continentUpdate(countries);
    }
}
