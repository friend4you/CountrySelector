package com.example.vlada.countryselector.api;

public class ServiceGenerator  {

    private static CountriesService service;

    public static CountriesService getCountiesService(){
        if(service == null){
            service = new CountriesService();
        }
        return service;
    }
}
