package main.dao;

import main.datasources.geonames.GeoNeighborCountry;
import main.domain.Country;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
/**
 *The DAO class for updating the neighbor relation in persistent storage
 */
public class NeighborsDAO extends ConnectDB{
    /**
     *This method is used to update the Neighbors relation with respect to source data. The method does not implement
     * deletion - it can only add data to the corresponding relation.
     * @param countries is a list of transfer objects
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void NeighborsUpdate(List<Country> countries) throws SQLException, ClassNotFoundException {
        Connection conn = connect();
        Statement st = conn.createStatement();
        st.execute("CREATE TABLE temp ("
                + "country_code character varying,"
                + "neighbor character varying,"
                + "PRIMARY KEY(country_code,neighbor)"
                + ");"
        );
        Iterator<Country> it = countries.iterator();
        while(it.hasNext()){
            Country count = it.next();
            List<GeoNeighborCountry> n = count.getNeighbors();
            Iterator<GeoNeighborCountry> nIt = n.iterator();
            while(nIt.hasNext()){
                GeoNeighborCountry neighbor = nIt.next();
                st.execute("INSERT INTO temp ("
                        + "country_code,"
                        + "neighbor"
                        + ")"
                        + "VALUES ("
                        + "'" + count.getInfo().getCode_a2() + "',"
                        + "'" + neighbor.getNeighbor_code() + "'"
                        + ");"
                );
            }
        }
        st.execute("INSERT INTO neighbors"
                + "  (SELECT temp.country_code,temp.neighbor"
                + "   FROM temp"
                + "   LEFT OUTER JOIN neighbors"
                + "   ON temp.country_code=neighbors.country_code"
                + "   AND temp.neighbor=neighbors.neighbor"
                + "   WHERE neighbors.country_code IS NULL"
                + "   );"
                + "DROP TABLE temp;"
        );
        st.close();
        conn.close();
    }
}
