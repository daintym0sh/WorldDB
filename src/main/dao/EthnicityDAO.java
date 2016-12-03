package main.dao;

import main.domain.Country;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
/**
 *The DAO class for updating the ethnicity relation in persistent storage
 */
public class EthnicityDAO extends DemogrDAO {
    private String type = "ethnicity";

    /**
     *This method is used to update the Ethnicity relation with respect to source data. The method does not implement
     * deletion - it can only add data to the corresponding relation.
     * @param countries is a list of transfer objects
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void ethnicityUpdate(List<Country> countries) throws SQLException, ClassNotFoundException {
        demographicTemp();
        Connection conn = connect();
        Iterator<Country> it = countries.iterator();
        while(it.hasNext()) {
            Country c = it.next();
            demographicAdd(conn, c.getInfo().getCode_a2(), c.getDemogrData().getEthnicity());
        }
        conn.close();
        add(type);
    }
}
