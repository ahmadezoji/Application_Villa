package com.apsent.villapp;

import androidx.annotation.Nullable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable
public class Users implements Serializable {
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String PHONE = "phone";

    @DatabaseField(generatedId = true,canBeNull = false)
    private int id;
    @DatabaseField(canBeNull = false)
    private String name;
    @DatabaseField(canBeNull = false)
    private String phone;
    @DatabaseField
    private Integer type;

    public Users() {
    }

    public Users(int id, String name, String phone, Integer type) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.type = type;
    }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
