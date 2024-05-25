package com.iptv.iptv2.models;

public class Channel {
    private String name;
    private String url;
    private String tvgId;
    private String tvgName;
    private String tvgType;
    private String groupTitle;
    private String tvgLogo;

    public Channel(String name, String url, String tvgId, String tvgName, String tvgType, String groupTitle, String tvgLogo) {
        this.name = name;
        this.url = url;
        this.tvgId = tvgId;
        this.tvgName = tvgName;
        this.tvgType = tvgType;
        this.groupTitle = groupTitle;
        this.tvgLogo = tvgLogo;
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

    public String getTvgType() {
        return tvgType;
    }

    public String getGroupTitle() {
        return groupTitle;
    }

    public String getTvgLogo() {
        return tvgLogo;
    }
    public void setTvgLogo(String tvgLogo) {
        this.tvgLogo = tvgLogo;
    }


    @Override
    public String toString() {
        return "Channel{" +
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
