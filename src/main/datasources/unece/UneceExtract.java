package main.datasources.unece;


import com.opencsv.CSVReader;

import javax.xml.bind.JAXBException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UneceExtract {
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
