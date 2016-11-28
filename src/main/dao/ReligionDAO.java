package main.dao;

import main.domain.Country;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

public class ReligionDAO extends DemogrDAO {
    private String type = "religion";
    public void religionUpdate(List<Country> countries) throws SQLException {
        demographicTemp();
        Iterator<Country> it = countries.iterator();
        while(it.hasNext()) {
            Country c = it.next();
            demographicAdd(c.getInfo().getCode_a2(),c.getDemogrData().getReligion());
        }
        add(type);
    }
}
