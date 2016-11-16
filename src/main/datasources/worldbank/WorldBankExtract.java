package main.datasources.worldbank;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class WorldBankExtract {
    
    private String restRequest;

    public WorldBankExtract(String restRequest) {
        this.restRequest = restRequest;
    }
    
    public List<WorldBankData> extractData() throws JAXBException, IOException{
        URL xmlData = new URL(restRequest);
        JAXBContext jc = JAXBContext.newInstance(WorldBankRoot.class);
        Unmarshaller u = jc.createUnmarshaller();
        WorldBankRoot countryPops = (WorldBankRoot) u.unmarshal(xmlData);
        return countryPops.getCountryPop();
    }
}