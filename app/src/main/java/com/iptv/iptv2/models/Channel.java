package com.iptv.iptv2.models;

public class Channel {
    private String name;
    private String url;
    private String tvgId;
    private String tvgName;
    private String tvgLogo;
    private String groupTitle;

    public Channel(String name, String url, String tvgId, String tvgName, String tvgLogo, String groupTitle) {
        this.name = name;
        this.url = url;
        this.tvgId = tvgId;
        this.tvgName = tvgName;
        this.tvgLogo = tvgLogo;
        this.groupTitle = groupTitle;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getTvgId() {
        return tvgId;
    }

    public String getTvgName() {
        return tvgName;
    }

    public String getTvgLogo() {
        return tvgLogo;
    }

    public String getGroupTitle() {
        return groupTitle;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", tvgId='" + tvgId + '\'' +
                ", tvgName='" + tvgName + '\'' +
                ", tvgLogo='" + tvgLogo + '\'' +
                ", groupTitle='" + groupTitle + '\'' +
                '}';
    }
}
