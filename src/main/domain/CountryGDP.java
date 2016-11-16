package main.domain;

import main.datasources.worldbank.WorldBankData;

import java.util.List;

/**
 * Created by John on 11/15/2016.
 */
public class CountryGDP {
    private List<WorldBankData> gdp;
    private List<WorldBankData> agGdp;
    private List<WorldBankData> indGdp;
    private List<WorldBankData> servGdp;

    public CountryGDP(List<WorldBankData> gdp,
                      List<WorldBankData> agGdp,
                      List<WorldBankData> indGdp,
                      List<WorldBankData> servGdp) {
        this.gdp = gdp;
        this.agGdp = agGdp;
        this.indGdp = indGdp;
        this.servGdp = servGdp;
    }

    public List<WorldBankData> getGdp() {
        return gdp;
    }

    public List<WorldBankData> getAgGdp() {
        return agGdp;
    }

    public List<WorldBankData> getIndGdp() {
        return indGdp;
    }

    public List<WorldBankData> getServGdp() {
        return servGdp;
    }
}
