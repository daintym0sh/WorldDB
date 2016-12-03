package main;

import main.dao.*;
import main.domain.Country;
import main.domain.CountryBuild;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Runner{
    public static void main(String[] args) throws SQLException, ParserConfigurationException, JAXBException, SAXException, IOException, ClassNotFoundException {
        List<Country> countries = new CountryBuild().extractCountry();
        CountryDAO countrydao = new CountryDAO();
        countrydao.countryUpdate(countries);
        EthnicityDAO ethnicitydao = new EthnicityDAO();
        ethnicitydao.ethnicityUpdate(countries);
        LanguageDAO languagedao = new LanguageDAO();
        languagedao.ethnicityUpdate(countries);
        ReligionDAO religiondao = new ReligionDAO();
        religiondao.religionUpdate(countries);
        CityDAO cityDAO = new CityDAO();
        cityDAO.cityUpdate(countries);
        EconomyDAO economydao = new EconomyDAO();
        economydao.economyUpdate(countries);
        PopulationDAO populationdao = new PopulationDAO();
        populationdao.PopulationUpdate(countries);
        NeighborsDAO neighborsdao = new NeighborsDAO();
        neighborsdao.NeighborsUpdate(countries);
        ContinentDAO continentdao = new ContinentDAO();
        continentdao.continentUpdate(countries);
        CurrencyDAO currencyDAO = new CurrencyDAO();
        currencyDAO.currencyUpdate(countries);
    }
}
