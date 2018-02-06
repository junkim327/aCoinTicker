package com.example.junyoung.acointicker;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CoinMarketCapService {
    @GET("ticker")
    Call<List<Cryptocurrency>> listCryptocurrency(@Query("limit") String limit);
}
