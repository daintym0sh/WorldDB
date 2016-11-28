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

public class CurrencyDAO extends ConnectDB{
    public void currencyUpdate(List<Country> countries) throws ParserConfigurationException, SAXException, IOException, JAXBException, SQLException {
        Statement st = conn.createStatement();
        st.execute("CREATE TABLE temp ("
                + "country_code character varying PRIMARY KEY,"
                + "currency character varying"
                + ");"
        );
        Iterator<Country> it = countries.iterator();
        while(it.hasNext()){
            Country c = it.next();
            String name = c.getInfo().getCurrency();
            st.execute("INSERT INTO temp ("
                    + "country_code,"
                    + "currency"
                    + ")"
                    + "VALUES ("
                    + "'" + c.getInfo().getCode_a2() + "',"
                    + "'" + name + "'"
                    + ");"
            );
        }
        st.execute("INSERT INTO currency" +
                "  (SELECT temp.country_code,temp.currency" +
                "   FROM temp" +
                "   LEFT OUTER JOIN currency" +
                "   ON temp.country_code=currency.country_code" +
                "   WHERE currency.country_code IS NULL" +
                "   );" +
                "DROP TABLE temp;"
        );
        st.close();
    }
}
