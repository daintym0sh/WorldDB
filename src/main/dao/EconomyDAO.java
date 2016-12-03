package main.dao;


import main.datasources.worldbank.WorldBankData;
import main.domain.Country;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
/**
 *The DAO class for updating the economy relation in persistent storage
 */
public class EconomyDAO extends ConnectDB {

    /**
     * This method is used to update the Economy relation with respect to source data. The method does not implement
     * deletion - it can only add data to the corresponding relation.
     * @param countries is a list of transfer objects
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     * @throws JAXBException
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void economyUpdate(List<Country> countries) throws ParserConfigurationException, SAXException, IOException, JAXBException, SQLException, ClassNotFoundException {
        Connection conn = connect();
        Statement st = conn.createStatement();
        st.execute("CREATE TABLE temp ("
                + "country_code character varying,"
                + "year numeric,"
                + "gdp numeric,"
                + "agriculture numeric,"
                + "industry numeric,"
                + "services numeric,"
                + "inflation numeric,"
                + "unemployment numeric,"
                + "PRIMARY KEY(country_code,year)"
                + ");"
        );
        Iterator<Country> it = countries.iterator();
        while(it.hasNext()){
            Country count = it.next();
            List<WorldBankData> g = count.getGdpData().getGdp();
            Iterator<WorldBankData> gIt = g.iterator();
            List<WorldBankData> a = count.getGdpData().getAgGdp();
            List<WorldBankData> i = count.getGdpData().getIndGdp();
            List<WorldBankData> s = count.getGdpData().getServGdp();
            List<WorldBankData> inf = count.getTimeData().getInfl();
            List<WorldBankData> u = count.getTimeData().getUnempl();
            while(gIt.hasNext()){
                WorldBankData gdp = gIt.next();
                String country_code = gdp.getCode_a2();
                int year = gdp.getDate();
                double gdpValue = gdp.getValue();
                double aValue = a.stream()
                        .filter(WorldBankData -> WorldBankData.getDate()==gdp.getDate())
                        .findFirst().orElse(new WorldBankData()).getValue();
                double iValue = i.stream()
                        .filter(WorldBankData -> WorldBankData.getDate()==gdp.getDate())
                        .findFirst().orElse(new WorldBankData()).getValue();
                double sValue = s.stream()
                        .filter(WorldBankData -> WorldBankData.getDate()==gdp.getDate())
                        .findFirst().orElse(new WorldBankData()).getValue();
                double infValue = inf.stream()
                        .filter(WorldBankData -> WorldBankData.getDate()==gdp.getDate())
                        .findFirst().orElse(new WorldBankData()).getValue();
                double uValue = u.stream()
                        .filter(WorldBankData -> WorldBankData.getDate()==gdp.getDate())
                        .findFirst().orElse(new WorldBankData()).getValue();
                System.out.println(iValue + " " + sValue + " " + infValue + " " + uValue);

                st.execute("INSERT INTO temp ("
                                + "country_code,"
                                + "year,"
                                + "gdp,"
                                + "agriculture,"
                                + "industry,"
                                + "services,"
                                + "inflation,"
                                + "unemployment"
                                + ")"
                                + "VALUES ("
                        + "'" + country_code + "',"
                        + "'" + year + "',"
                        + "'" + gdpValue + "',"
                        + "'" + aValue + "',"
                        + "'" + iValue + "',"
                        + "'" + sValue + "',"
                        + "'" + infValue + "',"
                        + "'" + uValue + "'"
                        + ");"
                );
            }
        }
        st.execute("INSERT INTO economy" +
                "  (SELECT temp.country_code,temp.year,temp.gdp,temp.agriculture,temp.industry,temp.services,temp.inflation,temp.unemployment" +
                "   FROM temp" +
                "   LEFT OUTER JOIN economy" +
                "   ON temp.country_code=economy.country_code" +
                "   AND temp.year=economy.year" +
                "   WHERE economy.country_code IS NULL" +
                "   OR economy.year IS NULL" +
                "   );" +
                "DROP TABLE temp;"
        );
        st.close();
        conn.close();
    }
}
