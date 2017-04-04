package main.dao;

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
 *The DAO class for updating the country relation in persistent storage
 */
public class CountryDAO extends ConnectDB {
    /**
     * This method is used to update the Country relation with respect to source data. The method does not implement
     * deletion - it can only add data to the corresponding relation.
     * @param countries is a list of transfer objects
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     * @throws JAXBException
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void countryUpdate(List<Country> countries) throws ParserConfigurationException, SAXException, IOException, JAXBException, SQLException, ClassNotFoundException {
        Connection conn = connect();
        Statement st = conn.createStatement();
        st.execute("CREATE TABLE temp ("
                + "code character varying PRIMARY KEY,"
                + "name character varying,"
                + "area numeric,"
                + "capital character varying,"
                + "continent character varying,"
                + "currency character varying,"
                + "latitude numeric,"
                + "longitude numeric"
                + ");"
        );
        Iterator<Country> it = countries.iterator();
        while(it.hasNext()){
            Country c = it.next();
            String name = c.getInfo().getCountry_name();
            name = name.replaceAll("'","''");
            String code = c.getInfo().getCode_a2();
            double area = c.getInfo().getArea();
            String capital = c.getInfo().getCapital();
            capital=capital.replaceAll("'","''");
            String continent = c.getInfo().getContinent();
            String currency = c.getInfo().getCurrency();
            double latitude = Double.parseDouble(c.getLocation()[0]);
            double longitude = Double.parseDouble(c.getLocation()[1]);

            st.execute("INSERT INTO temp ("
                    + "code,"
                    + "name,"
                    + "area,"
                    + "capital,"
                    + "continent,"
                    + "currency,"
                    + "latitude,"
                    + "longitude"
                    + ")"
                    + "VALUES ("
                    + "'" + code + "',"
                    + "'" + name + "',"
                    + "'" + area + "',"
                    + "'" + capital + "',"
                    + "'" + continent + "',"
                    + "'" + currency + "',"
                    + "'" + latitude + "',"
                    + "'" + longitude + "'"
                    + ");"
            );

        }
        st.execute("INSERT INTO country" +
                "  (SELECT temp.code,temp.name,temp.area,temp.capital,temp.continent,temp.currency,temp.latitude,temp.longitude" +
                "   FROM temp" +
                "   LEFT OUTER JOIN country" +
                "   ON temp.code=country.code" +
                "   WHERE country.code IS NULL" +
                "   );" +
                "DROP TABLE temp;"
        );
        st.close();
        conn.close();
    }
}
