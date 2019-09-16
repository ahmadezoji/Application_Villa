package com.absent.villaapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.types.ByteArrayType;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable
public class Villa implements Serializable {
    public static final String ADMINUSER_ID = "AdminUserId";
    @DatabaseField(generatedId = true)
    private int villaId;
    @DatabaseField
    private int roomCount;
    @DatabaseField
    private int capacity;
    @DatabaseField
    private int Area;
    @DatabaseField
    private String address;
    @DatabaseField(dataType = DataType.BYTE_ARRAY)
    private byte[] pic;
    @DatabaseField
    private int Cost;
    @DatabaseField
    private int AdminUserId;

   // @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
   // private int  userId;

//    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
//    public Users users;


    public Villa()
    {

    }

    public void setVillaId(int villaId) {
        this.villaId = villaId;
    }

    public void setAdminUserId(int adminUserId) {
        AdminUserId = adminUserId;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setArea(int area) {
        Area = area;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setRoomCount(int roomCount) {
        this.roomCount = roomCount;
    }

    public void setPic(byte[] pic) {
        this.pic = pic;
    }

    public void setCost(int cost) {
        Cost = cost;
    }

    public int getArea() {
        return Area;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getRoomCount() {
        return roomCount;
    }

    public Bitmap getPic() {
        return BitmapFactory.decodeByteArray(pic,0,pic.length);
    }

    public String getAddress() {
        return address;
    }

    public int getCost() {
        return Cost;
    }

    public int getAdminUserId() {
        return AdminUserId;
    }

    public int getVillaId() {
        return villaId;
    }
}
