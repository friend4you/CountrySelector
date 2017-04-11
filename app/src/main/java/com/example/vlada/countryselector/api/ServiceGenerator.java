package com.example.vlada.countryselector.api;

/**
 * Created by vlada on 10.04.2017.
 */

public class ServiceGenerator  {

    private static CountriesService service;

    public static CountriesService getCountiesService(){
        if(service == null){
            service = new CountriesService();
        }
        return service;
    }
}
