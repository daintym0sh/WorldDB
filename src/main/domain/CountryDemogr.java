package main.domain;

import java.util.List;

public class CountryDemogr {
    private List<String[]> language;
    private List<String[]> religion;
    private List<String[]> ethnicity;

    public CountryDemogr(List<String[]> language, List<String[]> religion, List<String[]> ethnicity) {
        this.language = language;
        this.religion = religion;
        this.ethnicity = ethnicity;

    }

    public List<String[]> getLanguage() {
        return language;
    }

    public List<String[]> getReligion() {
        return religion;
    }

    public List<String[]> getEthnicity() {
        return ethnicity;
    }

}
