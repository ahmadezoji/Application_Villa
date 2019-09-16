package com.absent.villaapp;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class CustomerReservation {
    @DatabaseField(generatedId = true)
    private int Id;
    @DatabaseField
    private int userId;
    @DatabaseField
    private int villaId;
    @DatabaseField
    private String ReservevDate;
    @DatabaseField
    private String ReservevDuration;


    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setVillaId(int villaId) {
        this.villaId = villaId;
    }

    public void setReservevDate(String reservevDate) {
        ReservevDate = reservevDate;
    }

    public int getUserId() {
        return userId;
    }

    public int getVillaId() {
        return villaId;
    }

    public String getReservevDate() {
        return ReservevDate;
    }
}
