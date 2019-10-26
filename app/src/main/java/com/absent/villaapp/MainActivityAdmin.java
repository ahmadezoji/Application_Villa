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
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivityAdmin extends AppCompatActivity implements VillaListOwner{

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    public static final String BASE_URL = "http://84.241.1.59:9191/";
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
                .setText(CurrentUser.getUsername());


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apIs = retrofit.create(APIs.class);

        this.filllist();
    }

    public class GetVillasTask extends AsyncTask<Integer,Object, ArrayList<Villa>>
    {
        @Override
        protected ArrayList<Villa> doInBackground(Integer... integers) {
            try {

                ArrayList<Villa> villas=new ArrayList<>();
                String strApi = new OkHttpClient().newCall(
                        new Request.Builder()
                                .url("http://84.241.1.59:9191/villas/provider?" +
                                        "PID="+integers[0]+"")
                                .build()
                )
                        .execute()
                        .body()
                        .string();
                /*Call back Fill list IF SUCCESS*/
                JSONArray jsonArray=new JSONArray(strApi);
                for (int i=0;i<jsonArray.length();i++) {
                    Villa villa=new Villa();
                    JSONObject jsonObject =jsonArray.getJSONObject(i);
                    villa.setVillaId(jsonObject.getInt("id"));
                    villa.setTitle(jsonObject.getString("title"));
                    villa.setRoomCount(jsonObject.getInt("roomcnt"));
                    villa.setCapacity(jsonObject.getInt("capacity"));
                    villa.setLat(jsonObject.getInt("lat"));
                    villa.setLon(jsonObject.getInt("lon"));
                    villa.setAddress(jsonObject.getString("address"));
                    villa.setPic((jsonObject.getString("cover")).getBytes());
                    villa.setCost(jsonObject.getInt("cost"));
                    villa.setAdminUserId(jsonObject.getInt("providerid"));
                    villas.add(villa);

                }

                return villas;
            }
            catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Villa> villas) {
            if (villas!=null) {
//                ((ListView)(findViewById(R.id.m_List_Admin)))
//                        .setAdapter(new VillaAdapterCustomer(MainActivityAdmin.this,villas));

                recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view_Admin);
                recyclerView.setHasFixedSize(true);

                /*Recycle view set adapter*///show Villas
                layoutManager = new LinearLayoutManager(MainActivityAdmin.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                adapter = new CardAdapterAdmin(villas,MainActivityAdmin.this);
                recyclerView.setAdapter(adapter);
            }
            else {
                Toast.makeText(MainActivityAdmin.this,"ویلایی برای مشهاده موجود نیست !!", Toast.LENGTH_LONG).show();
            }

        }
    }

    @Override
    public void filllist() {
        new GetVillasTask().execute(CurrentUser.getUserId());
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
                        filllist();
                    }
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });

    }

    public void Onclick_BtnAdd(View view)
    {
        Intent intent=new Intent(this, AdminInsertctivity.class);
        intent.putExtra("user",CurrentUser);
        startActivity(intent);
    }
}
