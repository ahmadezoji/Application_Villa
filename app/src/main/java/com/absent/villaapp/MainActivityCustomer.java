package com.absent.villaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivityCustomer extends AppCompatActivity implements VillaListOwner{
    public Users CurrentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_customer);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        CurrentUser=(Users) bundle.get("user");
        ((TextView)(findViewById(R.id.m_UserName_Customer)))
                .setText(CurrentUser.getUsername());

        this.filllist();
    }

    @Override
    public void filllist() {
        try {
            List<Villa> villas=new ArrayList<>();
            villas=new DatabaseHelper(this).getVilla_Customer();
            ((ListView)(findViewById(R.id.m_List_Customer)))
                    .setAdapter(new VillaAdapterCustomer(this,villas));
        }
        catch (Exception e)
        {
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    public class GetVillasTask extends AsyncTask<Integer,Object, List<Villa>>
    {
        @Override
        protected List<Villa> doInBackground(Integer... integers) {
            try {

                List<Villa> villas=new ArrayList<>();
                String strApi = new OkHttpClient().newCall(
                        new Request.Builder()
                                .url("http://84.241.1.59:9191/ServiceVilla/getvillas")
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
                    villa.setRoomCount(jsonObject.getInt("roomcnt"));
                    villa.setCapacity(jsonObject.getInt("capacity"));
                    villa.setArea(jsonObject.getInt("area"));
                    villa.setAddress(jsonObject.getString("address"));
                    villa.setPic((jsonObject.getString("coverpic")).getBytes());
                    villa.setCost(jsonObject.getInt("cost"));
                    villa.setAdminUserId(jsonObject.getInt("ownerId"));
                    villas.add(villa);

                }

                return villas;
            }
            catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Villa> villas) {
            if (villas!=null) {
                ((ListView)(findViewById(R.id.m_List_Admin)))
                        .setAdapter(new VillaAdapterAdmin(MainActivityCustomer.this,villas));
            }
            else {
                Toast.makeText(MainActivityCustomer.this, "ثبت نام ناموفق بود  !!", Toast.LENGTH_LONG).show();
            }

        }
    }
}
