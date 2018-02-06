package com.example.junyoung.acointicker;

import com.google.gson.annotations.SerializedName;

public class Cryptocurrency {
    private String name;
    private String symbol;
    @SerializedName("price_usd")
    private String priceUsd;
    @SerializedName("percent_change_24h")
    private String percentChange;
    @SerializedName("last_updated")
    private String lastUpdated;
    @SerializedName("24h_volume_usd")
    private String volume;

    public Cryptocurrency(String name, String symbol, String price_usd) {
        this.name = name;
        this.symbol = symbol;
        this.priceUsd = price_usd;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getPriceUsd() {
        return priceUsd;
    }

    public String getPercentChange() {
        return percentChange;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public String getVolume() {
        return volume;
    }
}
