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
    public static final String BASE_URL = "http://127.0.0.1:8080/";
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



        this.filllist();
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
    public void deleteVilla(int id) {

        Call<Boolean> call= apIs.deleteVilla(id);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful())
                {
                    boolean btrue=response.body();
                    if (btrue)
                    {
                        Toast.makeText(MainActivityAdmin.this,"Deleted",Toast.LENGTH_SHORT).show();
                        filllist();
                    }
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });

    }

    @Override
    public void editVilla(Villa villa) {
       Call<Boolean> call=apIs.updateVilla(villa);
       call.enqueue(new Callback<Boolean>() {
           @Override
           public void onResponse(Call<Boolean> call, Response<Boolean> response) {
               if(response.isSuccessful())
               {
                   Boolean btrue=response.body();
                   if (btrue)
                   {
                       Toast.makeText(MainActivityAdmin.this,"Updated",Toast.LENGTH_SHORT).show();
                       filllist();
                   }
               }
           }

           @Override
           public void onFailure(Call<Boolean> call, Throwable t) {

           }
       });
    }
    public void OnUser_Click(View view)
    {
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
