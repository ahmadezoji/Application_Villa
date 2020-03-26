package com.absent.villaapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivityAdmin extends AppCompatActivity implements VillaListOwner{

    private VillaController villaController;

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    public static final String BASE_URL = "http://192.168.1.42:8080/";
    private APIs apIs;
    public Users CurrentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        CurrentUser=(Users) bundle.get("user");
        ((TextView)(findViewById(R.id.m_UserName)))
                .setText(CurrentUser.getName());

        villaController = new VillaController();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apIs = retrofit.create(APIs.class);
        filllist();
    }
    @Override
    public void filllist() {
        ArrayList<Villa> villas = villaController.GetAdminVilla(CurrentUser.getId());

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view_Admin);
        recyclerView.setHasFixedSize(true);

        /*Recycle view set adapter*///show Villas
        layoutManager = new LinearLayoutManager(MainActivityAdmin.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new CardAdapterAdmin(villas,MainActivityAdmin.this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void deleteVilla(Villa villa) {
        villaController.EditVilla(villa);
    }
    @Override
    public void editVilla(Villa villa) {
        villaController.EditVilla(villa);
    }
    public void OnUser_Click(View view)
    {
        Utils.writePreferences(this,Utils.PFREFRENCE_USER_LOGIN,Utils.PFREFRENCE_USER_LOGIN_KEY,"");
        Intent intent=new Intent(this, UserLoginActivity.class);
        startActivity(intent);
    }
    public void Onclick_BtnAdd(View view)
    {
        Intent intent=new Intent(this, AdminInsertActivity.class);
        intent.putExtra("user",CurrentUser);
        startActivity(intent);
    }

}
