package main.datasources.worldbank;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The data of interest from a country element in a World Bank indicator XML REST response, returned by the World Bank API
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "country")
public class WorldBankCountry {
	
    @XmlAttribute(name="id")
	private String code_a2;
	    
	public String getCode_a2() {
		return code_a2;
	}
}
