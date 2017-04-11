package com.example.vlada.countryselector.model;

/**
 * Created by vlada on 10.04.2017.
 */

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

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getElevation() {
        return elevation;
    }

    public void setElevation(Integer elevation) {
        this.elevation = elevation;
    }

    public Integer getGeoNameId() {
        return geoNameId;
    }

    public void setGeoNameId(Integer geoNameId) {
        this.geoNameId = geoNameId;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getThumbnailImg() {
        return thumbnailImg;
    }

    public void setThumbnailImg(String thumbnailImg) {
        this.thumbnailImg = thumbnailImg;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getWikipediaUrl() {
        return wikipediaUrl;
    }

    public void setWikipediaUrl(String wikipediaUrl) {
        this.wikipediaUrl = wikipediaUrl;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}