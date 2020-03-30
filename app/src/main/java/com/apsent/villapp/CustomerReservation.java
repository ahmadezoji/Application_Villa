package com.apsent.villapp;

import androidx.viewpager.widget.PagerAdapter;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class CustomerReservation {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private int uid;
    @DatabaseField
    private int vid;
    @DatabaseField
    private String reservedate;
    @DatabaseField
    private int duration;

    public CustomerReservation() {
    }

    public CustomerReservation(int uid, int vid, String reservedate, int duration) {
        this.uid = uid;
        this.vid = vid;
        this.reservedate = reservedate;
        this.duration = duration;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setVid(int vid) {
        this.vid = vid;
    }

    public void setReservedate(String reservedate) {
        this.reservedate = reservedate;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public int getUid() {
        return uid;
    }

    public int getVid() {
        return vid;
    }

    public String getReservedate() {
        return reservedate;
    }

    public int getDuration() {
        return duration;
    }
}
