package com.apsent.villapp;

import android.os.AsyncTask;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserCotroller {

    public static final String BASE_URL = "http://192.168.1.42:8080/";

    public boolean IsExist(String phone)
    {
        try {
            if(new IsExistUserTask().execute(phone).get().size() != 0)
                return true;
            else
                return false;
        }
        catch (Exception e)
        {
            return false;
        }

    }
    public JSONObject getJson_user(Users users) throws JSONException {
        // create your json here
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name",users.getName());
        jsonObject.put("phone",users.getPhone());
        jsonObject.put("type",String.valueOf(users.getType()));

        return jsonObject;

    }
    public class AddUserTask extends AsyncTask<Users,Object,Boolean>
    {
        @Override
        protected Boolean doInBackground(Users... users) {
            try {

                OkHttpClient client = new OkHttpClient();
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                // put your json here
                RequestBody userBody = RequestBody.create(JSON,getJson_user(users[0]).toString());

                List<Users> users1=new ArrayList<>();
                String strApi = new OkHttpClient().newCall(
                        new Request.Builder()
                                .url(BASE_URL+"/users/add")
                                .post(userBody)
                                .build()
                )
                        .execute()
                        .body()
                        .string();
                /*Call back Fill list IF SUCCESS*/
                JSONArray jsonArray=new JSONArray(strApi);
                for (int i=0;i<jsonArray.length();i++) {
                    Users user=new Users();
                    JSONObject jsonObject =jsonArray.getJSONObject(i);
                    user.setId(jsonObject.getInt("id"));
                    user.setName(jsonObject.getString("name"));
                    user.setPhone(jsonObject.getString("phone"));
                    user.setType(jsonObject.getInt("type"));
                    users1.add(user);
                }

                if (users1.size() != 0)
                    return true;
                else
                    return false;


            }catch (Exception e)
            {
                return false;
            }
        }
    }
    public Boolean AddUser(Users users)
    {
        try {
            return new AddUserTask().execute(users).get();
        }
        catch (Exception e)
        {
            return false;
        }
    }
    public class IsExistUserTask extends AsyncTask<String,Object,List<Users>>
    {
        @Override
        protected List<Users> doInBackground(String... strings) {
            try {
                List<Users> users=new ArrayList<>();
                String strApi = new OkHttpClient().newCall(
                        new Request.Builder()
                                .url(BASE_URL+"users/findByPhone?phone="+strings[0])
                                .build()
                )
                        .execute()
                        .body()
                        .string();
                /*Call back Fill list IF SUCCESS*/
                JSONArray jsonArray=new JSONArray(strApi);
                for (int i=0;i<jsonArray.length();i++) {
                    Users user=new Users();
                    JSONObject jsonObject =jsonArray.getJSONObject(i);
                    user.setId(jsonObject.getInt("id"));
                    user.setName(jsonObject.getString("name"));
                    user.setPhone(jsonObject.getString("phone"));
                    user.setType(jsonObject.getInt("type"));
                    users.add(user);
                }
                return users;

            }catch (Exception e)
            {
                return null;
            }
        }
    }

    public Users getUser(String phone)
    {
        try {
            return new IsExistUserTask().execute(phone).get().get(0);
        }
        catch (Exception e)
        {
            return null;
        }

    }

}
