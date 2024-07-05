package com.iptv.iptv2.models;

public class Subuser {
    private int id;
    private String name;
    private String imageUrl; // New field

    public Subuser(int id, String name, String imageUrl) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    // New constructor without imageUrl
    public Subuser(int id, String name) {
        this.id = id;
        this.name = name;
        this.imageUrl = null;
    }

    // Getters and setters for id, name, and imageUrl
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
