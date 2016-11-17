package main.doa;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;

public class DemogrDOA extends ConnectDB {
    public void demographicTemp() throws SQLException {
        Statement st = conn.createStatement();
        st.execute("CREATE TABLE temp(" +
                "code character varying," +
                "demogr character varying," +
                "percent numeric," +
                "PRIMARY KEY(code,demogr)" +
                ");");
        st.close();
    }
    public void demographicAdd(String code, List<String[]> demogr) throws SQLException {
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
    }

    public void add(String type) throws SQLException {
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
    }
}
