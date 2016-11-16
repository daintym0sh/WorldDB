package main.domain;

import main.datasources.worldbank.WorldBankData;

import java.util.List;

public class CountryTime {
    private List<WorldBankData> population;
    private List<WorldBankData> growth;
    private List<WorldBankData> mortality;
    private List<WorldBankData> infl;
    private List<WorldBankData> unempl;

    public CountryTime(List<WorldBankData> population,
                       List<WorldBankData> growth,
                       List<WorldBankData> mortality,
                       List<WorldBankData> infl,
                       List<WorldBankData> unempl) {
        this.population = population;
        this.growth = growth;
        this.mortality = mortality;
        this.infl = infl;
        this.unempl = unempl;
    }

    public List<WorldBankData> getPopulation() {
        return population;
    }

    public List<WorldBankData> getGrowth() {
        return growth;
    }

    public List<WorldBankData> getMortality() {
        return mortality;
    }

    public List<WorldBankData> getInfl() {
        return infl;
    }

    public List<WorldBankData> getUnempl() {
        return unempl;
    }
}
