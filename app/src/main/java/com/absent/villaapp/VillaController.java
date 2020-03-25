package com.absent.villaapp;

import android.os.AsyncTask;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VillaController {

    public static final String BASE_URL = "http://192.168.1.42:8080/";
    public boolean AddVilla(Villa villa)
    {
        try{
            return new Addvilla_task().execute(villa).get();

        }catch (Exception e)
        {
            return false;
        }
    }
    public JSONObject getJson_villa(Villa villa) throws JSONException {
        // create your json here
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title",villa.getTitle());
        jsonObject.put("address",villa.getAddress());
        jsonObject.put("lat",String.valueOf(villa.getLat()));
        jsonObject.put("lon",String.valueOf(villa.getLon()));
        jsonObject.put("cost",String.valueOf(villa.getCost()));
        jsonObject.put("roomcnt",String.valueOf(villa.getRoomCount()));
        jsonObject.put("capacity",String.valueOf(villa.getCapacity()));
        jsonObject.put("area",String.valueOf(villa.getArea()));
        jsonObject.put("cover", villa.getCover());
        jsonObject.put("galleryid", String.valueOf(villa.getGalleryid()));
        jsonObject.put("providerid",String.valueOf(villa.getAdminUserId()));

        return jsonObject;

    }
    public class Addvilla_task extends AsyncTask<Villa,Object,Boolean>
    {
        @Override
        protected Boolean doInBackground(Villa... villas) {
            try {

                OkHttpClient client = new OkHttpClient();
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                // put your json here
                RequestBody userBody = RequestBody.create(JSON,getJson_villa(villas[0]).toString());


                String strApi=new OkHttpClient().newCall(
                        new Request.Builder()
                                .url(BASE_URL+"villas/add")
                                .post(userBody)
                                .build()
                )
                        .execute()
                        .body()
                        .string();


                    return true;
            }
            catch (JSONException e) {
                return false;
            }
            catch (Exception e)
            {
                return false;
            }
        }
    }
    public ArrayList<Villa> GetAdminVilla(Integer AdminId)
    {
        try {
            return new GetVillasTask().execute(AdminId).get();
        }catch (Exception e)
        {
            return null;
        }
    }
    public class GetVillasTask extends AsyncTask<Integer,Object, ArrayList<Villa>>
    {
        @Override
        protected ArrayList<Villa> doInBackground(Integer... integers) {
            try {

                ArrayList<Villa> villas=new ArrayList<>();
                String strApi = new OkHttpClient().newCall(
                        new Request.Builder()
                                .url(BASE_URL+"/villas/provider?" +
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
                    villa.setCover(jsonObject.getString("cover"));
                    villa.setCost(jsonObject.getInt("cost"));
                    villa.setGalleryid(jsonObject.getInt("galleryid"));
                    villa.setAdminUserId(jsonObject.getInt("providerid"));
                    villas.add(villa);

                }



                return villas;
            }
            catch (Exception e) {
                return null;
            }
        }

    }
    public List<Villa> getAllVilles()
    {
        try {
            return new GetAllVillasTask().execute().get();
        }catch (Exception e)
        {
            return null;
        }
    }
    public class GetAllVillasTask extends AsyncTask<Object,Object, ArrayList<Villa>>
    {
        @Override
        protected ArrayList<Villa> doInBackground(Object... objects) {
            try {

                ArrayList<Villa> villas=new ArrayList<>();
                String strApi = new OkHttpClient().newCall(
                        new Request.Builder()
                                .url(BASE_URL+"villas/all")
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
                    villa.setCover(jsonObject.getString("cover"));
                    villa.setCost(jsonObject.getInt("cost"));
                    villa.setGalleryid(jsonObject.getInt("galleryid"));
                    villa.setAdminUserId(jsonObject.getInt("providerid"));
                    villas.add(villa);

                }



                return villas;
            }
            catch (Exception e) {
                return null;
            }
        }
    }
}
