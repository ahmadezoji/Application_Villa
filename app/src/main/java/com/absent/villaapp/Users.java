package com.absent.villaapp;

import androidx.annotation.Nullable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable
public class Users implements Serializable {
    public static final String ID = "userId";
    public static final String USERNAME_FIELD = "username";
    public static final String PASSWORD_FIELD = "password";

    @DatabaseField(generatedId = true,canBeNull = false)
    private int userId;
    @DatabaseField(canBeNull = false)
    private String username;
    @DatabaseField(canBeNull = false)
    private String password;
    @DatabaseField
    private  String phone;
    @DatabaseField
    private int type;


    public Users()
    {

    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phone = phoneNumber;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }
    public String getPhoneNumber() {
        return phone;
    }

    public String getUsername() {
        return username;
    }

    public int getType() {
        return type;
    }

    public int getUserId() {
        return userId;
    }
}
