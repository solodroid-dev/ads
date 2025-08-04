package com.solodroidx.ads.rest;

import com.solodroidx.ads.callback.CallbackApps;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface ApiInterface {

    String CACHE = "Cache-Control: max-age=0";
    String AGENT = "Data-Agent: Ad Network Sdk";

    @Headers({CACHE, AGENT})
    @GET("apps.json")
    Call<CallbackApps> getApps();

}
