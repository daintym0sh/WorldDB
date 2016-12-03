package main.dao;

import main.datasources.unece.UneceCity;
import main.domain.Country;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;

/**
 *The DAO class for updating the city relation in persistent storage
 */
public class CityDAO extends ConnectDB{

    /**
     *This method is used to update the City relation with respect to source data. The method does not implement
     * deletion - it can only add data to the corresponding relation.
     * @param countries is a list of transfer objects
     * @throws SQLException when SQL code is not executed correctly or a database access error occurs
     */
    public void cityUpdate(List<Country> countries) throws SQLException, ClassNotFoundException {
        Connection conn = connect();
        Statement st = conn.createStatement();
        st.execute("CREATE TABLE temp ("
                    + "country_code character varying,"
                    + "city_code character varying,"
                    + "name character varying,"
                    + "latitude character varying,"
                    + "longitude character varying,"
                    + "PRIMARY KEY(country_code,city_code)"
                    + ");"
        );
        Iterator<Country> it = countries.iterator();
        while(it.hasNext()){
            Country c = it.next();
            List<UneceCity> cityList = c.getCities();
            Iterator<UneceCity> itC = cityList.iterator();
            while(itC.hasNext()) {
                UneceCity city = itC.next();
                String country_code = c.getInfo().getCode_a2();
                String city_code = city.getCity_code();
                String name = city.getName();
                name = name.replaceAll("'", "''");
                String latitude = city.getLatitude();
                String longitude = city.getLongitude();
                st.execute("INSERT INTO temp ("
                        + "country_code,"
                        + "city_code,"
                        + "name,"
                        + "latitude,"
                        + "longitude"
                        + ")"
                        + "VALUES ("
                        + "'" + country_code + "',"
                        + "'" + city_code + "',"
                        + "'" + name + "',"
                        + "'" + latitude + "',"
                        + "'" + longitude + "'"
                        + ");"
                );
            }
        }
        st.execute("INSERT INTO city"
                + "  (SELECT temp.country_code,temp.city_code,temp.name,temp.latitude,temp.longitude"
                + "   FROM temp"
                + "   LEFT OUTER JOIN city"
                + "   ON temp.country_code=city.country_code"
                + "   AND temp.city_code=city.city_code"
                + "   WHERE city.name IS NULL"
                + "   );"
                + "DROP TABLE temp;"
        );
        st.close();
        conn.close();
    }
}
