package main.datasources.worldbank;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class WorldBankExtract {
    
    private String restRequest;

    /**
     * Constructor
     * @param restRequest is the URL used to make a REST request to the World Bank API
     */
    public WorldBankExtract(String restRequest) {
        this.restRequest = restRequest;
    }

    /**
     * This method unmarshalls the World Bank indicator XML data
     * @return a List of WorldBankData holding the data of interest from the REST response
     * @throws JAXBException
     * @throws IOException
     */
    public List<WorldBankData> extractData() throws JAXBException, IOException{
        URL xmlData = new URL(restRequest);
        JAXBContext jc = JAXBContext.newInstance(WorldBankRoot.class);
        Unmarshaller u = jc.createUnmarshaller();
        WorldBankRoot countryPops = (WorldBankRoot) u.unmarshal(xmlData);
        return countryPops.getCountryPop();
    }
}