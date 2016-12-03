package main.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
/**
 *The super class for DAO's used to interact with relations in persistent storage that
 * contain demographic data from the CIA Factbook
 */
public class DemogrDAO extends ConnectDB {
    /**
     * This method creates a temporary table used for updating a relation
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void demographicTemp() throws SQLException, ClassNotFoundException {
        Connection conn = connect();
        Statement st = conn.createStatement();
        st.execute("CREATE TABLE temp(" +
                "code character varying," +
                "demogr character varying," +
                "percent numeric," +
                "PRIMARY KEY(code,demogr)" +
                ");");
        st.close();
        conn.close();
    }

    /**
     * This method inserts the data held by the transfer objects into the temporary table used for the update
     * @param conn is the connection object to connect with the database
     * @param code is the type referring the demographic of interest
     * @param demogr is the list of demographic data as a String array with length 2
     *               (index 0: demographic, index 1: population percent)
     * @throws SQLException
     */
    public void demographicAdd(Connection conn, String code, List<String[]> demogr) throws SQLException {
        Statement st = conn.createStatement();
        Iterator<String[]> it = demogr.iterator();
        while(it.hasNext()){
            String[] d = it.next();
            String demographic = d[0];
            demographic = demographic.replace("'","''");
            String percent = d[1];

            st.execute("INSERT INTO temp (" +
                        "code," +
                        "demogr," +
                        "percent" +
                        ")" +
                        "VALUES (" +
                        "'" + code + "'," +
                        "'" + demographic + "'," +
                        "'" + percent + "'" +
                        ");"
            );
        }
        st.close();
    }

    /**
     * This method updates a table residing in persistent storage with the data in the newly created temporary table
     * @param type is the type referring the demographic of interest
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void add(String type) throws SQLException, ClassNotFoundException {
        Connection conn = connect();
        Statement st = conn.createStatement();
        st.execute("INSERT INTO " + type +
                "  (SELECT temp.code,temp.demogr,temp.percent" +
                "   FROM temp" +
                "   LEFT OUTER JOIN " + type +
                "   ON temp.code=" + type + ".country_code" +
                "   WHERE " + type + ".country_code IS NULL" +
                "   );" +
                "DROP TABLE temp;"
        );
        st.close();
        conn.close();
    }
}
