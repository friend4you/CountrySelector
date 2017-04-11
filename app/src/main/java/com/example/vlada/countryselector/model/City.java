package com.example.vlada.countryselector.model;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class City {

    @SerializedName("summary")
    private String summary;
    @SerializedName("elevation")
    private Integer elevation;
    @SerializedName("geoNameId")
    private Integer geoNameId;
    @SerializedName("feature")
    private String feature;
    @SerializedName("lng")
    private Double lng;
    @SerializedName("countryCode")
    private String countryCode;
    @SerializedName("rank")
    private Integer rank;
    @SerializedName("thumbnailImg")
    private String thumbnailImg;
    @SerializedName("lang")
    private String lang;
    @SerializedName("title")
    private String title;
    @SerializedName("lat")
    private Double lat;
    @SerializedName("wikipediaUrl")
    private String wikipediaUrl;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getSummary() {
        return summary;
    }

    public Integer getElevation() {
        return elevation;
    }

    public Integer getGeoNameId() {
        return geoNameId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        City city = (City) o;

        return geoNameId != null ? geoNameId.equals(city.geoNameId) : city.geoNameId == null;

    }

    @Override
    public int hashCode() {
        return geoNameId != null ? geoNameId.hashCode() : 0;
    }

    public String getFeature() {
        return feature;
    }

    public Double getLng() {
        return lng;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public Integer getRank() {
        return rank;
    }

    public String getThumbnailImg() {
        return thumbnailImg;
    }

    public String getLang() {
        return lang;
    }

    public String getTitle() {
        return title;
    }

    public Double getLat() {
        return lat;
    }

    public String getWikipediaUrl() {
        return wikipediaUrl;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

}