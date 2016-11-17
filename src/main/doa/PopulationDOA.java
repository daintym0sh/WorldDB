package main.doa;

import main.datasources.worldbank.WorldBankData;
import main.domain.Country;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;

public class PopulationDOA extends ConnectDB{
    public void PopulationUpdate(List<Country> countries) throws ParserConfigurationException, SAXException, IOException, JAXBException, SQLException {
        Statement st = conn.createStatement();
        st.execute("CREATE TABLE temp ("
                + "country_code character varying,"
                + "year numeric,"
                + "population integer,"
                + "growth_rate numeric,"
                + "mortality_rate numeric,"
                + "PRIMARY KEY(country_code,year)"
                + ");"
        );
        Iterator<Country> it = countries.iterator();
        while(it.hasNext()){
            Country count = it.next();
            List<WorldBankData> p = count.getTimeData().getPopulation();
            Iterator<WorldBankData> pIt = p.iterator();
            List<WorldBankData> g = count.getTimeData().getGrowth();
            List<WorldBankData> m = count.getTimeData().getMortality();
            while(pIt.hasNext()){
                WorldBankData pop = pIt.next();
                String country_code = pop.getCode_a2();
                int year = pop.getDate();
                int population = (int) pop.getValue();
                double growth_rate = g.stream()
                        .filter(WorldBankData -> WorldBankData.getDate()==pop.getDate())
                        .findFirst().orElse(new WorldBankData()).getValue();
                double mortality_rate = m.stream()
                        .filter(WorldBankData -> WorldBankData.getDate()==pop.getDate())
                        .findFirst().orElse(new WorldBankData()).getValue();

                st.execute("INSERT INTO temp ("
                        + "country_code,"
                        + "year,"
                        + "population,"
                        + "growth_rate,"
                        + "mortality_rate"
                        + ")"
                        + "VALUES ("
                        + "'" + country_code + "',"
                        + "'" + year + "',"
                        + "'" + population + "',"
                        + "'" + growth_rate + "',"
                        + "'" + mortality_rate + "'"
                        + ");"
                );
            }
        }
        st.execute("INSERT INTO countrypopulation" +
                "  (SELECT temp.country_code,temp.year,temp.population,temp.growth_rate,temp.mortality_rate" +
                "   FROM temp" +
                "   LEFT OUTER JOIN countrypopulation" +
                "   ON temp.country_code=countrypopulation.country_code" +
                "   AND temp.year=countrypopulation.year" +
                "   WHERE countrypopulation.country_code IS NULL" +
                "   OR countrypopulation.year IS NULL" +
                "   );" +
                "DROP TABLE temp;"
        );
        st.close();
    }
}
