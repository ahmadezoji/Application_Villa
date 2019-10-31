package com.absent.villaapp;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpActivity extends AppCompatActivity implements Ownerstate{
    public static final String BASE_URL = "http://84.241.1.59:9191/";
    private APIs apIs;
    private int AutenticatKey;
    Users currentuser;
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
        currentuser=new Users();

        String username=
        ((EditText)(findViewById(R.id.m_usernameSignUP)))
                .getText().toString();

        String password=
                ((EditText)(findViewById(R.id.m_passwordSignUP)))
                        .getText().toString();

        String phonenumber=
                ((EditText)(findViewById(R.id.m_phonSignUP)))
                        .getText().toString();

        currentuser.setUsername(username);
        currentuser.setPassword(password);
        currentuser.setPhoneNumber(phonenumber);
        currentuser.setType(1);


        IsExist(phonenumber);



    }
   public void IsExist(String phone)
   {
        Call<List<Users>> call=apIs.getuserBYphone(phone);
        call.enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                if (response.isSuccessful())
                {
                    List<Users> users=response.body();
                    if (users==null)
                    {
                        AutenticatKey = getAutenticateKey();
                        SendAutenticatKeyToPhone(AutenticatKey);
                    }
                    else
                        Toast.makeText(SignUpActivity.this, "This Phone Number Registered Later !!", Toast.LENGTH_LONG).show();


                }
            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {

            }
        });

   }
    public class SendKeytoPhoneTask extends AsyncTask<String,Object,Integer>
    {
        @Override
        protected void onPostExecute(Integer integer) {
            if (integer==1)//send To Phone Success
            {
                AutenticatInsert_Dialog dialog=new AutenticatInsert_Dialog();
                dialog.setContext(SignUpActivity.this);
                dialog.setStyle(DialogFragment.STYLE_NO_TITLE,0);
                dialog.setAutheticatekey(AutenticatKey);
                FragmentManager fm =(SignUpActivity.this).getFragmentManager();
                dialog.show(fm,"");
            }
            else
                Toast.makeText(SignUpActivity.this, "Error", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Integer doInBackground(String... strings) {
            try {

                String strApi=new OkHttpClient().newCall(
                        new Request.Builder()
                                .url("https://www.payam-resan.com/APISend.aspx?Username=09195835135&Password=a09367854752A&From=50002060499999&" +
                                        "To="+strings[0]+"&Text="+strings[1]+"")
                                .build()
                )
                        .execute()
                        .body()
                        .string();


                return Integer.valueOf(strApi);

            }
            catch (Exception e)
            {
                return 0;
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

    public int getAutenticateKey()
    {
        int max=999999;
        int min=100000;


        return (int)((Math.random() * ((max - min) + 1)) + min);
    }
    public void SendAutenticatKeyToPhone(int Key)
    {
        String phone=((EditText)(findViewById(R.id.m_phonSignUP))).getText().toString();

        String[] param = {phone,String.valueOf(Key)};
        new SendKeytoPhoneTask().execute(param);
    }
    @Override
    public void checkAutenticateUser() {
        Addusers(currentuser);
    }

    @Override
    public void set_date(String date) {

    }

    @Override
    public int daysBetween(Calendar startDate, Calendar endDate) {
    return 0;
    }

}
