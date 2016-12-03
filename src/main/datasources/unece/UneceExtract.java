package main.datasources.unece;


import com.opencsv.CSVReader;

import javax.xml.bind.JAXBException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to extract city data from the UN/LOCODE CSV file
 */
public class UneceExtract {

    /**
     * This method extracts city data from the UN/LOCODE CSV and creates objects with this data for each city
     * @return a List of UneceCity objects containing data from the UN/LOCODE data source
     * @throws IOException
     * @throws JAXBException
     */
    public List<UneceCity> extractCity() throws IOException, JAXBException {
        CSVReader reader = new CSVReader(new FileReader("data/CodeList.csv"));
        String [] nextLine;
        List<UneceCity> cities = new ArrayList<>();
        List<String> names = new ArrayList<>();
        while ((nextLine = reader.readNext()) != null) {
            if(names.contains(nextLine[1]+nextLine[2])==false){
                if(nextLine[10].isEmpty() == false){
                    cities.add(new UneceCity(nextLine[1],nextLine[2],nextLine[4],nextLine[10].substring(0,5),nextLine[10].substring(6,12)));
                }
                else if(nextLine[2].isEmpty() == false && nextLine[10].isEmpty() == true){
                    cities.add(new UneceCity(nextLine[1],nextLine[2],nextLine[4],null,null));
                }
            }
            names.add(nextLine[1]+nextLine[2]);
        }
        return cities;
    }
}
