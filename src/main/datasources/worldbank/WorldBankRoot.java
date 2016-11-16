package main.datasources.worldbank;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "data")
public class WorldBankRoot {
    
    @XmlElement(name = "data", type = WorldBankData.class)
    private List<WorldBankData> countryPops = new ArrayList<>();
    
    public WorldBankRoot(){
    }
    
    public WorldBankRoot(List<WorldBankData> countryPops) {
        this.countryPops = countryPops;
    }
    
    public List<WorldBankData> getCountryPop() {
        return countryPops;
    }
}
