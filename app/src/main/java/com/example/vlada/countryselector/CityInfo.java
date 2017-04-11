package com.example.vlada.countryselector;

import com.example.vlada.countryselector.model.City;
import com.example.vlada.countryselector.model.InfoResponse;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface CityInfo {



    @Headers("Content-Type: application/json")
    @GET("wikipediaSearchJSON?username=vladarseniuk")
    Call<InfoResponse> getCityInfo(
            @Query("q") String city

    );
}
