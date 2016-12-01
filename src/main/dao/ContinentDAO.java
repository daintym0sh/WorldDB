package main.dao;

import main.domain.Country;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
/**
 *The DAO for updating the continent relation in persistent storage
 */
public class ContinentDAO extends ConnectDB {

    public void continentUpdate(List<Country> countries) throws ParserConfigurationException, SAXException, IOException, JAXBException, SQLException {
        Statement st = conn.createStatement();
        st.execute("CREATE TABLE temp ("
                + "country_code character varying PRIMARY KEY,"
                + "name character varying"
                + ");"
        );
        Iterator<Country> it = countries.iterator();
        while(it.hasNext()){
            Country c = it.next();
            String name = c.getInfo().getContinent();
            st.execute("INSERT INTO temp ("
                    + "country_code,"
                    + "name"
                    + ")"
                    + "VALUES ("
                    + "'" + c.getInfo().getCode_a2() + "',"
                    + "'" + name + "'"
                    + ");"
            );
        }
        st.execute("INSERT INTO continent" +
                "  (SELECT temp.country_code,temp.name" +
                "   FROM temp" +
                "   LEFT OUTER JOIN continent" +
                "   ON temp.country_code=continent.country_code" +
                "   WHERE continent.country_code IS NULL" +
                "   );" +
                "DROP TABLE temp;"
        );
        st.close();
    }
}
