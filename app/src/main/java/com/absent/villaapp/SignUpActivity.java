package com.absent.villaapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class SignUpActivity extends AppCompatActivity {
    public static final String  BASE_URL = "http://84.241.1.59:9191/";
    private APIs apIs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apIs = retrofit.create(APIs.class);

    }
    public void onclick_btnSignup(View view)
    {
        Users user=new Users();

        String username=
        ((EditText)(findViewById(R.id.m_usernameSignUP)))
                .getText().toString();

        String password=
                ((EditText)(findViewById(R.id.m_passwordSignUP)))
                        .getText().toString();

        String phonenumber=
                ((EditText)(findViewById(R.id.m_phonSignUP)))
                        .getText().toString();

        user.setUsername(username);
        user.setPassword(password);
        user.setPhoneNumber(phonenumber);
        user.setType(1);

        Addusers(user);

       // new SignUpTask().execute(username,password,phonenumber);
    }

    public class SignUpTask extends AsyncTask<String,Object, List<Users>>
    {
        @Override
        protected List<Users> doInBackground(String... strings) {
            try {

                List<Users> users=new ArrayList<>();
                String strApi = new OkHttpClient().newCall(
                        new Request.Builder()
                                .url("http://84.241.1.59:9191/users/add?" +
                                        "username="+strings[0]+
                                        "&password="+strings[1]+"" +
                                        "&phone="+strings[2]+"&type=1")
                                .build()
                )
                        .execute()
                        .body()
                        .string();
                /*Call back Fill list IF SUCCESS*/
                JSONArray jsonArray=new JSONArray(strApi);
                for (int i=0;i<jsonArray.length();i++) {
                    Users users1=new Users();
                    JSONObject jsonObject =jsonArray.getJSONObject(i);
                    users1.setUserId(jsonObject.getInt("id"));
                    users1.setUsername(jsonObject.getString("username"));
                    users1.setPassword(jsonObject.getString("password"));
                    users1.setPhoneNumber(jsonObject.getString("phone"));
                    users1.setType(jsonObject.getInt("type"));
                    users.add(users1);

                }

                return users;
            }
            catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Users> users) {
            if (users!=null) {
                Toast.makeText(SignUpActivity.this, "ثبت نام با موفقیت بود  !!", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(SignUpActivity.this, "ثبت نام ناموفق بود  !!", Toast.LENGTH_LONG).show();
            }

        }
    }

    private void Addusers(Users users)
    {
        Call<List<Users>> call=apIs.createuser(users);
        call.enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                List<Users> list=new ArrayList<>();
                list=response.body();
                if (list!=null) {
                    Toast.makeText(SignUpActivity.this, "ثبت نام با موفقیت بود  !!", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(SignUpActivity.this, "ثبت نام ناموفق بود  !!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {
                Log.d("","Error");
            }
        });

    }
}
