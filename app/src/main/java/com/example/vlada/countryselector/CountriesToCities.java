package com.example.vlada.countryselector;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;


public interface CountriesToCities {

    @Headers("Content-Type: application/json")
    @GET("David-Haim/CountriesToCitiesJSON/master/countriesToCities.json")
    Call<Map<String, List<String>>> getCountriesToCities();
}
