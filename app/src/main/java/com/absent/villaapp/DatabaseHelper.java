package com.absent.villaapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.icu.lang.UScript;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private Context context;
    private Dao<Villa,Integer> villaDao;
    private Dao<Users,Integer> userDao;
    private Dao<CustomerReservation,Integer> CustomerReservationDao;

    /*Table Users*/
    public List<Users> getUser(String username,String password)
    {
        try {
            List<Users>users=new ArrayList<>();
            QueryBuilder<Users, Integer> qb = getUserDao().queryBuilder();
            Where where = qb.where();
            where.eq(Users.USERNAME_FIELD, username);
            where.and();
            where.eq(Users.PASSWORD_FIELD, password);
            PreparedQuery<Users> preparedQuery = qb.prepare();
            final Iterator<Users>  usersIterator = getUserDao().query(preparedQuery).iterator();

            // Iterate through the StudentDetails object iterator and populate the comma separated String
            while (usersIterator.hasNext()) {
                users.add(usersIterator.next());
            }

            return users;
        }
        catch(Exception e){
            return null;
        }
    }
    public void  AddUser(Users users)
    {
        try {
            getUserDao()
                    .create(users);
        }
        catch(Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void updateUser(Users users){
        try {
            getUserDao()
                    .update(users);
        }
        catch(Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void deleteUser(Users users){
        try{
            getUserDao()
                    .delete(users);
        }
        catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /*-----------------------------------------------------*/
    /*Table CustomerReservation*/
    public void  AddReservation(CustomerReservation customerReservation)
    {
        try {
            getCustomerReservationDao()
                    .create(customerReservation);
        }
        catch(Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void updateReservation(CustomerReservation customerReservation){
        try {
            getCustomerReservationDao()
                    .update(customerReservation);
        }
        catch(Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void deleteReservation(CustomerReservation customerReservation){
        try{
            getCustomerReservationDao()
                    .delete(customerReservation);
        }
        catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    /*-----------------------------------------------------*/
    /*Table CustomerReservation*/
    public void  AddVilla(Villa villa)
    {
        try {
            getVillaDao()
                    .create(villa);
        }
        catch(Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void updateVilla(Villa villa){
        try {
            getVillaDao()
                    .update(villa);
        }
        catch(Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void deleteVilla(Villa villa){
        try{
            getVillaDao()
                    .delete(villa);
        }
        catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public List<Villa> getVilla_Customer(){
        try {
            return getVillaDao()
                    .queryForAll();
        }
        catch(Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }
    public List<Villa> getVilla_Admin(int id){
        try {
            List<Villa>villas=new ArrayList<>();
            QueryBuilder<Villa, Integer> qb = getVillaDao().queryBuilder();
            Where where = qb.where();
            where.eq(Villa.ADMINUSER_ID, id);
            PreparedQuery<Villa> preparedQuery = qb.prepare();
            final Iterator<Villa>  villaIterator = getVillaDao().query(preparedQuery).iterator();

            // Iterate through the StudentDetails object iterator and populate the comma separated String
            while (villaIterator.hasNext()) {
                villas.add(villaIterator.next());
            }

            return villas;
        }
        catch(Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }
    /*-----------------------------------------------------*/
    /*This Function Initialize for DB*/
    ////
    ///////Data Base Init
    ///
    public Dao<Users, Integer> getUserDao() {
        if (userDao==null){
            try {
                userDao = getDao(Users.class);
            }
            catch(Exception e){
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        return userDao;
    }

    public Dao<CustomerReservation, Integer> getCustomerReservationDao() {
        if (CustomerReservationDao==null){
            try {
                CustomerReservationDao = getDao(CustomerReservation.class);
            }
            catch(Exception e){
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        return CustomerReservationDao;
    }

    public Dao<Villa, Integer> getVillaDao() {
        if (villaDao==null){
            try {
                villaDao = getDao(Villa.class);
            }
            catch(Exception e){
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        return villaDao;
    }

    public DatabaseHelper(Context context ){
        super(context,"VillaDB",null,1);
        this.context=context;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource,Users.class);
            TableUtils.createTableIfNotExists(connectionSource,Villa.class);
            TableUtils.createTableIfNotExists(connectionSource,CustomerReservation.class);

        }
        catch(Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {

    }

}
