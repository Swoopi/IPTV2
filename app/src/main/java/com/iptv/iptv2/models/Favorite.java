package com.iptv.iptv2.models;

public class Favorite {
    private int id;
    private int subuserId;
    private int itemId;
    private String itemType;

    public Favorite(int id, int subuserId, int itemId, String itemType) {
        this.id = id;
        this.subuserId = subuserId;
        this.itemId = itemId;
        this.itemType = itemType;
    }

    public int getId() {
        return id;
    }

    public int getSubuserId() {
        return subuserId;
    }

    public int getItemId() {
        return itemId;
    }

    public String getItemType() {
        return itemType;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSubuserId(int subuserId) {
        this.subuserId = subuserId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }
}
