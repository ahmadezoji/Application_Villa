package com.apsent.villapp;

import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;
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

    public Villa AddVilla(Villa villa)
    {
        try{
            return new addvillaTask().execute(villa).get();

        }catch (Exception e)
        {
            return null;
        }
    }
    public JSONObject getJson_villa(Villa villa) throws JSONException {
        // create your json here
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",villa.getVillaId());
        jsonObject.put("title",villa.getTitle());
        jsonObject.put("address",villa.getAddress());
        jsonObject.put("lat",String.valueOf(villa.getLat()));
        jsonObject.put("lon",String.valueOf(villa.getLon()));
        jsonObject.put("cost",String.valueOf(villa.getCost()));
        jsonObject.put("cost_weekend",String.valueOf(villa.getCost_weekend()));
        jsonObject.put("cost_special",String.valueOf(villa.getCost_special()));
        jsonObject.put("roomcnt",String.valueOf(villa.getRoomCount()));
        jsonObject.put("capacity",String.valueOf(villa.getCapacity()));
        jsonObject.put("area",String.valueOf(villa.getArea()));
        jsonObject.put("cover", villa.getCover());
        jsonObject.put("galleryid", String.valueOf(villa.getGalleryid()));
        jsonObject.put("providerid",String.valueOf(villa.getAdminUserId()));

        return jsonObject;

    }
    public class addvillaTask extends AsyncTask<Villa,Object,Villa>
    {

        @Override
        protected Villa doInBackground(Villa... villas) {
            try {
                OkHttpClient client = new OkHttpClient();
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                // put your json here
                RequestBody userBody = RequestBody.create(JSON,getJson_villa(villas[0]).toString());


                String strApi=new OkHttpClient().newCall(
                        new Request.Builder()
                                .url(Utils.BASE_URL+"villas/add")
                                .post(userBody)
                                .build()
                )
                        .execute()
                        .body()
                        .string();

                /*Call back Fill list IF SUCCESS*/
//                JSONArray jsonArray=new JSONArray(strApi);
                Villa villa=new Villa();
//                JSONObject jsonObject =jsonArray.getJSONObject(0);
                JSONObject jsonObject = new JSONObject(strApi);

                villa.setVillaId(jsonObject.getInt("id"));
                villa.setTitle(jsonObject.getString("title"));
                villa.setRoomCount(jsonObject.getInt("roomcnt"));
                villa.setCapacity(jsonObject.getInt("capacity"));
                villa.setLat((float)jsonObject.getDouble("lat"));
                villa.setLon((float)jsonObject.getDouble("lon"));
                villa.setArea(jsonObject.getInt("area"));
                villa.setAddress(jsonObject.getString("address"));
                villa.setCover(jsonObject.getString("cover"));
                villa.setCost(jsonObject.getInt("cost"));
                villa.setCost_weekend(jsonObject.getInt("cost_weekend"));
                villa.setCost_special(jsonObject.getInt("cost_special"));
                villa.setGalleryid(jsonObject.getInt("galleryid"));
                villa.setAdminUserId(jsonObject.getInt("providerid"));

                return villa;
            }
            catch (Exception e)
            {
                return null;
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
                                .url(Utils.BASE_URL+"/villas/provider?" +
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
                    villa.setArea(jsonObject.getInt("area"));
                    villa.setLat((float)jsonObject.getDouble("lat"));
                    villa.setLon((float)jsonObject.getDouble("lon"));
                    villa.setAddress(jsonObject.getString("address"));
                    villa.setCover(jsonObject.getString("cover"));
                    villa.setCost(jsonObject.getInt("cost"));
                    villa.setCost_weekend(jsonObject.getInt("cost_weekend"));
                    villa.setCost_special(jsonObject.getInt("cost_special"));
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
                                .url(Utils.BASE_URL+"villas/all")
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
                    villa.setArea(jsonObject.getInt("area"));
                    villa.setLat((float)jsonObject.getDouble("lat"));
                    villa.setLon((float)jsonObject.getDouble("lon"));
                    villa.setAddress(jsonObject.getString("address"));
                    villa.setCover(jsonObject.getString("cover"));
                    villa.setCost(jsonObject.getInt("cost"));
                    villa.setCost_weekend(jsonObject.getInt("cost_weekend"));
                    villa.setCost_special(jsonObject.getInt("cost_special"));
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
    public boolean EditVilla(Villa villa)
    {
        try {
            return new EditVillaTask().execute(villa).get();
        }
        catch (Exception e)
        {
            return false;
        }

    }
    private class EditVillaTask extends AsyncTask<Villa,Object,Boolean>
    {
        @Override
        protected Boolean doInBackground(Villa... villas) {
            try {

                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                RequestBody userBody = RequestBody.create(JSON,getJson_villa(villas[0]).toString());

                String strApi = new OkHttpClient().newCall(
                        new Request.Builder()
                                .url(Utils.BASE_URL+"villas/update")
                                .post(userBody)
                                .build()
                )
                        .execute()
                        .body()
                        .string();
                if (strApi.contains("true"))
                    return true;
                else return false;
            }
            catch (Exception e)
            {
                return false;
            }
        }
    }
    public List<Villa> getVilla(String address)
    {
        try {
            if (address != null)
                return new getVillaByAddressTask().execute(address).get();
            else
                return null;
        }
        catch (Exception e)
        {
            return null;
        }
    }
    public class getVillaByAddressTask extends AsyncTask<String,Object,List<Villa>>
    {

        @Override
        protected List<Villa> doInBackground(String... strings) {
            try {
                List<Villa>villas=new ArrayList<>();
                String strApi = new OkHttpClient().newCall(
                        new Request.Builder()
                                .url(Utils.BASE_URL+"villas/findByAddress?address="+strings[0]+"")
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
                    villa.setArea(jsonObject.getInt("area"));
                    villa.setLat((float)jsonObject.getDouble("lat"));
                    villa.setLon((float)jsonObject.getDouble("lon"));
                    villa.setAddress(jsonObject.getString("address"));
                    villa.setCover(jsonObject.getString("cover"));
                    villa.setCost(jsonObject.getInt("cost"));
                    villa.setCost_weekend(jsonObject.getInt("cost_weekend"));
                    villa.setCost_special(jsonObject.getInt("cost_special"));
                    villa.setGalleryid(jsonObject.getInt("galleryid"));
                    villa.setAdminUserId(jsonObject.getInt("providerid"));


                    String searchTxt = strings[0];
                    String expression = "(?i).*"+searchTxt+".*";
                    if(villa.getAddress().matches(expression) || villa.getTitle().matches(expression))
                        villas.add(villa);

                }



                return villas;
            }
            catch (Exception e)
            {
                return null;
            }
        }
    }
    public List<Villa> getVilla(LatLng latLng ,float zoom)
    {
        try {
            if (latLng != null)
                return new getVillaByRegionTask().execute((float)latLng.latitude,(float)latLng.longitude,zoom).get();
            else return null;
        }
        catch (Exception e)
        {
            return null;
        }
    }
    public class getVillaByRegionTask extends AsyncTask<Float,Object,List<Villa>>
    {

        @Override
        protected List<Villa> doInBackground(Float... floats) {
            try {
                List<Villa> villas=new ArrayList<>();
                String strApi = new OkHttpClient().newCall(
                        new Request.Builder()
                                .url(Utils.BASE_URL + "villas/findByRegion?lat="+floats[0]+"&lon="+floats[1]+"&zoom="+floats[2]+"")
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
                    villa.setArea(jsonObject.getInt("area"));
                    villa.setLat((float)jsonObject.getDouble("lat"));
                    villa.setLon((float)jsonObject.getDouble("lon"));
                    villa.setAddress(jsonObject.getString("address"));
                    villa.setCover(jsonObject.getString("cover"));
                    villa.setCost(jsonObject.getInt("cost"));
                    villa.setCost_weekend(jsonObject.getInt("cost_weekend"));
                    villa.setCost_special(jsonObject.getInt("cost_special"));
                    villa.setGalleryid(jsonObject.getInt("galleryid"));
                    villa.setAdminUserId(jsonObject.getInt("providerid"));
                    villas.add(villa);

                }



                return villas;

            }catch (Exception e)
            {
                return null;
            }
        }
    }
    public boolean DeleteVilla(Villa villa)
    {
        try {
            return new DeleteVillaTask().execute(villa).get();
        }
        catch (Exception e)
        {
            return false;
        }
    }
    public void DeleteGallery(Integer vid)
    {
        try {
            new deleteGalleryTask().execute(vid);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public class deleteGalleryTask extends AsyncTask<Integer,Object,Boolean>
    {

        @Override
        protected Boolean doInBackground(Integer... integers) {
            try {

                String strApi = new OkHttpClient().newCall(
                        new Request.Builder()
                                .url(Utils.BASE_URL + "gallery/deleteByVID?vid="+integers[0]+"")
                                .build()
                )
                        .execute()
                        .body()
                        .string();

                return true;
            }
            catch (Exception e)
            {
                return false;
            }

        }
    }
    private class DeleteVillaTask extends AsyncTask<Villa,Object,Boolean>
    {
        @Override
        protected Boolean doInBackground(Villa... villas) {
            try {
                String strApi = new OkHttpClient().newCall(
                        new Request.Builder()
                                .url(Utils.BASE_URL + "villas/delete?id="+String.valueOf(villas[0].getVillaId()))
                                .build()
                )
                        .execute()
                        .body()
                        .string();
                if (strApi.contains("true"))
                    return true;
                else return false;
            }
            catch (Exception e)
            {
                return false;
            }
        }
    }
    public Boolean addgallery(String[] strings){
        try {
            return new addgalleryTask().execute(strings).get();
        }catch (Exception e)
        {
            return false;
        }
    }
    public class addgalleryTask extends AsyncTask<String,Object,Boolean>
    {

        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                List<Gallery> galleries=new ArrayList<>();
                String strApi = new OkHttpClient().newCall(
                        new Request.Builder()
                                .url(Utils.BASE_URL + "gallery/add2?vid="+strings[0]+"&img1="+strings[1]+"")
                                .build()
                )
                        .execute()
                        .body()
                        .string();


                if (strApi.contains("true"))
                    return true;
                else return false;
            }catch (Exception e)
            {
                return false;
            }
        }
    }
    public class getGalleryTask extends AsyncTask<Integer,Object,Gallery>
    {

        @Override
        protected Gallery doInBackground(Integer... integers) {
            try {
                List<Gallery> list=new ArrayList<>();
                String strApi = new OkHttpClient().newCall(
                        new Request.Builder()
                                .url(Utils.BASE_URL + "gallery/findGallery?vid="+String.valueOf(integers[0]))
                                .build()
                )
                        .execute()
                        .body()
                        .string();
                /*Call back Fill list IF SUCCESS*/
                JSONArray jsonArray=new JSONArray(strApi);
                for (int i=0;i<jsonArray.length();i++) {
                    Gallery gallery=new Gallery();
                    JSONObject jsonObject =jsonArray.getJSONObject(i);
                    gallery.setId(jsonObject.getInt("id"));
                    gallery.setVid(jsonObject.getInt("vid"));
                    gallery.setImg1(jsonObject.getString("img1"));
                    gallery.setImg2(jsonObject.getString("img2"));
                    gallery.setImg3(jsonObject.getString("img3"));
                    gallery.setImg4(jsonObject.getString("img4"));
                    gallery.setImg5(jsonObject.getString("img5"));
                    gallery.setImg6(jsonObject.getString("img6"));
                    gallery.setImg7(jsonObject.getString("img7"));
                    gallery.setImg8(jsonObject.getString("img8"));
                    gallery.setImg9(jsonObject.getString("img9"));
                    gallery.setImg10(jsonObject.getString("img10"));
                    list.add(gallery);

                }

                return list.get(0);

            }
            catch (Exception e)
            {
                return null;
            }
        }
    }
    public Gallery getGallery(Integer vid)
    {
        try {
            return new getGalleryTask().execute(vid).get();
        }
        catch (Exception e)
        {
            return null;
        }
    }
    public boolean updateGallery(Gallery gallery)
    {
        try {
            return new updateGalleryTask().execute(gallery).get();
        }
        catch (Exception e){
            return false;
        }
    }
    private class updateGalleryTask extends AsyncTask<Gallery,Object,Boolean>
    {

        @Override
        protected Boolean doInBackground(Gallery... galleries) {
            try {


                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                RequestBody galleryBody = RequestBody.create(JSON,getJson_gellery(galleries[0]).toString());
                String strApi = new OkHttpClient().newCall(
                        new Request.Builder()
                                .url(Utils.BASE_URL + "gallery/update")
                                .post(galleryBody)
                                .build()
                )
                        .execute()
                        .body()
                        .string();
                return true;
            }
            catch (Exception e)
            {
                return false;
            }
        }
    }
    public JSONObject getJson_gellery(Gallery gallery) throws JSONException {
        // create your json here
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",gallery.getId());
        jsonObject.put("vid",gallery.getVid());
        jsonObject.put("img1",gallery.getImg1());
        jsonObject.put("img2",gallery.getImg2());
        jsonObject.put("img3",gallery.getImg3());
        jsonObject.put("img4",gallery.getImg4());
        jsonObject.put("img5",gallery.getImg5());
        jsonObject.put("img6",gallery.getImg6());
        jsonObject.put("img7",gallery.getImg7());
        jsonObject.put("img8",gallery.getImg8());
        jsonObject.put("img9",gallery.getImg9());
        jsonObject.put("img10",gallery.getImg10());


        return jsonObject;

    }
}
