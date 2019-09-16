package com.absent.villaapp;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.icu.lang.UScript;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NewDatabaseHelper<T> extends OrmLiteSqliteOpenHelper {
    Context context;
    T t;
    private Dao<T,Integer> dao;

    public void getVilla(T t)
    {

    }



    public NewDatabaseHelper(Context context, T t)
   {
        super(context,"VillDB1",null,1);
        this.context=context;
        this.t=t;
   }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {

        try {
            TableUtils.createTableIfNotExists(connectionSource,t.getClass());
        }
        catch(Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {

    }
}
