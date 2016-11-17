package main.datasources.worldbank;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class WorldBankData {

    private WorldBankCountry country;
    private String code_a2;
    @XmlElement(name = "date")
    private int date;
    @XmlElement(name = "value")
    private double value;

    public String getCode_a2() {
        code_a2 = country.getCode_a2();
        return code_a2;
    }

    public int getDate() {
        return date;
    }

    public double getValue() {
        return value;
    }
}