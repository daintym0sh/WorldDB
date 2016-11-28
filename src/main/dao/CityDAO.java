package main.dao;

import main.datasources.unece.UneceCity;
import main.domain.Country;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;

/**
 * Created by John on 11/15/2016.
 */
public class CityDAO extends ConnectDB{

    public void cityUpdate(List<Country> countries) throws SQLException {
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

    }
}
