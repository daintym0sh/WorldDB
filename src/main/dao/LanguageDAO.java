package main.dao;

import main.domain.Country;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
/**
 *The DAO class for updating the language relation in persistent storage
 */
public class LanguageDAO extends DemogrDAO {
    private String type = "language";
    public void ethnicityUpdate(List<Country> countries) throws SQLException, ClassNotFoundException {
        demographicTemp();
        Connection conn = connect();
        Iterator<Country> it = countries.iterator();
        while(it.hasNext()) {
            Country c = it.next();
            demographicAdd(conn, c.getInfo().getCode_a2(), c.getDemogrData().getLanguage());
        }
        conn.close();
        add(type);
    }
}
