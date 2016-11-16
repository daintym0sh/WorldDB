package main.doa;

import main.domain.Country;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;

public class CountryDOA extends ConnectDB {

    public void countryUpdate(List<Country> countries) throws ParserConfigurationException, SAXException, IOException, JAXBException, SQLException {
        Statement st = conn.createStatement();
        st.execute("CREATE TABLE temp ("
                + "code character varying PRIMARY KEY,"
                + "name character varying,"
                + "area numeric,"
                + "capital character varying,"
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
            double latitude = Double.parseDouble(c.getLocation()[0]);
            double longitude = Double.parseDouble(c.getLocation()[1]);

            st.execute("INSERT INTO temp ("
                    + "code,"
                    + "name,"
                    + "area,"
                    + "capital,"
                    + "latitude,"
                    + "longitude"
                    + ")"
                    + "VALUES ("
                    + "'" + code + "',"
                    + "'" + name + "',"
                    + "'" + area + "',"
                    + "'" + capital + "',"
                    + "'" + latitude + "',"
                    + "'" + longitude + "'"
                    + ");"
            );

        }
        st.execute("INSERT INTO country" +
                "  (SELECT temp.code,temp.name,temp.area,temp.capital,temp.latitude,temp.longitude" +
                "   FROM temp" +
                "   LEFT OUTER JOIN country" +
                "   ON temp.code=country.code" +
                "   WHERE country.code IS NULL" +
                "   );" +
                "   DELETE FROM country" +
                "   WHERE country.code IN" +
                "   (SELECT country.code" +
                "   FROM country" +
                "   LEFT OUTER JOIN temp" +
                "   ON country.code=temp.code" +
                "   WHERE temp.code IS NULL);" +
                "DROP TABLE temp;"
        );
        st.close();
    }
}
