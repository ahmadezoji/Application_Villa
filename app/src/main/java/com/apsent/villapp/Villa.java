package com.apsent.villapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable
public class Villa implements Serializable {
    public static final String ADMINUSER_ID = "AdminUserId";
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    String title;
    @DatabaseField
    private String address;
    @DatabaseField
    float lat;
    @DatabaseField
    float lon;
    @DatabaseField
    private int cost;
    @DatabaseField
    private int roomcnt;
    @DatabaseField
    private int capacity;
    @DatabaseField
    private int area;
    @DatabaseField
    private String cover;
    @DatabaseField
    private Integer galleryid;
    @DatabaseField
    private int providerid;

    public Villa()
    {

    }
    public Integer getGalleryid() {
        return galleryid;
    }

    public void setGalleryid(Integer galleryid) {
        this.galleryid = galleryid;
    }
    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setVillaId(int villaId) {
        this.id = villaId;
    }

    public void setAdminUserId(int adminUserId) {
        this.providerid = adminUserId;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setRoomCount(int roomCount) {
        this.roomcnt = roomCount;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public float getLat() {
        return lat;
    }

    public float getLon() {
        return lon;
    }

    public String getTitle() {
        return title;
    }

    public int getArea() {
        return area;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getRoomCount() {
        return roomcnt;
    }


    public String getAddress() {
        return address;
    }

    public int getCost() {
        return cost;
    }

    public int getAdminUserId() {
        return providerid;
    }

    public int getVillaId() {
        return id;
    }
}
