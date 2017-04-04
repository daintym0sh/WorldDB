package main;

import main.dao.*;
import main.domain.Country;
import main.domain.CountryBuild;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static main.dao.ConnectDB.connect;

public class Runner{
    public static void main(String[] args) throws SQLException, ParserConfigurationException, JAXBException, SAXException, IOException, ClassNotFoundException {
        String[] tables = new String[]{"country","city","countrypop","economy",
                "language","religion","ethnicity","neighbors"};
        for(String s : tables) {
            System.out.println(s +": " + rowCount(s));
        }
        System.out.println("\n");

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

        for(String s : tables) {
            System.out.println(s +": " + rowCount(s));
        }
    }

    public static String rowCount(String table) throws SQLException, ClassNotFoundException {
        String query = "SELECT COUNT(*) AS total FROM " + table + ";";
        Connection conn = connect();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(query);
        rs.next();
        return rs.getString("total");
    }
}
