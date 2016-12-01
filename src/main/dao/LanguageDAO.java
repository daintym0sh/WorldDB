package main.dao;

import main.domain.Country;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
/**
 *The DAO for updating the language relation in persistent storage
 */
public class LanguageDAO extends DemogrDAO {
    private String type = "language";
    public void ethnicityUpdate(List<Country> countries) throws SQLException {
        demographicTemp();
        Iterator<Country> it = countries.iterator();
        while(it.hasNext()) {
            Country c = it.next();
            demographicAdd(c.getInfo().getCode_a2(),c.getDemogrData().getLanguage());
        }
        add(type);
    }
}
