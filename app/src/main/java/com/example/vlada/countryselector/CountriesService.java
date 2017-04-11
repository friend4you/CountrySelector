package com.example.vlada.countryselector;


import com.example.vlada.countryselector.model.City;
import com.example.vlada.countryselector.model.InfoResponse;
import com.readystatesoftware.chuck.ChuckInterceptor;

import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;


public class CountriesService {

    private final CountriesToCities countriesToCities;
    private final CityInfo cityInfo;

    public CountriesService() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new ChuckInterceptor(Application.getSharedInstance().getApplicationContext()))
                .build();
        Retrofit retrofitList = new Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        countriesToCities = retrofitList.create(CountriesToCities.class);

        Retrofit retrofitInfo = new Retrofit.Builder()
                .baseUrl("http://api.geonames.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        cityInfo = retrofitInfo.create(CityInfo.class);
    }

    public Observable<Map<String, List<String>>> getCountriesToCities() {
        Call<Map<String, List<String>>> call = countriesToCities.getCountriesToCities();
        return Observable.fromCallable(() -> call.execute().body());
    }

    public Observable<InfoResponse> getCityInfo(String queue) {
        Call<InfoResponse> call = cityInfo.getCityInfo(queue);
        return Observable.fromCallable(() -> call.execute().body());
    }


}
