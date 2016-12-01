package main.dao;

import main.domain.Country;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
/**
 *The DAO for updating the ethnicity relation in persistent storage
 */
public class EthnicityDAO extends DemogrDAO {
    private String type = "ethnicity";
    public void ethnicityUpdate(List<Country> countries) throws SQLException {
        demographicTemp();
        Iterator<Country> it = countries.iterator();
        while(it.hasNext()) {
            Country c = it.next();
            demographicAdd(c.getInfo().getCode_a2(),c.getDemogrData().getEthnicity());
        }
        add(type);
    }
}
