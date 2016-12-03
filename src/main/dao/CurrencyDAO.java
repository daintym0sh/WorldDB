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
 *The DAO class for updating the currency relation in persistent storage
 */
public class CurrencyDAO extends ConnectDB{
    /**
     *This method is used to update the Currency relation with respect to source data. The method does not implement
     * deletion - it can only add data to the corresponding relation.
     * @param countries is a list of transfer objects
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     * @throws JAXBException
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void currencyUpdate(List<Country> countries) throws ParserConfigurationException, SAXException, IOException, JAXBException, SQLException, ClassNotFoundException {
        Connection conn = connect();
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
        conn.close();
    }
}
