package com.iptv.iptv2.models;

import java.util.List;

public class Movie {
    private String name;
    private String url;
    private String tvgId;
    private String tvgName;
    private String tvgType;
    private String groupTitle;
    private String tvgLogo;
    private String region;
    private List<String> categories;

    public Movie(String name, String url, String tvgId, String tvgName, String tvgType, String groupTitle, String tvgLogo, String region, List<String> categories) {
        this.name = name;
        this.url = url;
        this.tvgId = tvgId;
        this.tvgName = tvgName;
        this.tvgType = tvgType;
        this.groupTitle = groupTitle;
        this.tvgLogo = tvgLogo;
        this.region = region;
        this.categories = categories;
    }

    // Getters and setters
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
    public String getName() { return name; }
    public String getUrl() { return url; }
    public String getTvgId() { return tvgId; }
    public String getTvgName() { return tvgName; }
    public String getTvgType() { return tvgType; }
    public String getGroupTitle() { return groupTitle; }
    public String getTvgLogo() { return tvgLogo; }
    public void setTvgLogo(String tvgLogo) { this.tvgLogo = tvgLogo; }
    public List<String> getCategories() {
        return categories;
    }
    public void setCategories(){ this.categories = categories;}

    @Override
    public String toString() {
        return "Movie{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", tvgId='" + tvgId + '\'' +
                ", tvgName='" + tvgName + '\'' +
                ", tvgType='" + tvgType + '\'' +
                ", groupTitle='" + groupTitle + '\'' +
                ", tvgLogo='" + tvgLogo + '\'' +
                '}';
    }
}
