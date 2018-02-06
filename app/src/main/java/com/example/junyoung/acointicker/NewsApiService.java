package com.example.junyoung.acointicker;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApiService {
    @GET("everything")
    Call<NewsCollection> newsCollection(@Query("q") String keyword,
                                        @Query("sortBy") String order,
                                        @Query("pageSize") String size,
                                        @Query("apiKey") String apiKey);
}
