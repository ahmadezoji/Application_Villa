package com.absent.villaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }
    public void onclick_btnSignup(View view)
    {
        String username=
        ((EditText)(findViewById(R.id.m_usernameSignUP)))
                .getText().toString();

        String password=
                ((EditText)(findViewById(R.id.m_passwordSignUP)))
                        .getText().toString();

        String phonenumber=
                ((EditText)(findViewById(R.id.m_phonSignUP)))
                        .getText().toString();

        new SignUpTask().execute(username,password,phonenumber);
    }

    public class SignUpTask extends AsyncTask<String,Object, List<Users>>
    {
        @Override
        protected List<Users> doInBackground(String... strings) {
            try {

                List<Users> users=new ArrayList<>();
                String strApi = new OkHttpClient().newCall(
                        new Request.Builder()
                                .url("http://84.241.1.59:9191/ServiceVilla/" +
                                        "adduser?username="+strings[0]+
                                        "&password="+strings[1]+"" +
                                        "&phoneNumber="+strings[2]+"&type=1")
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
                    users1.setPhoneNumber(jsonObject.getString("phoneNumber"));
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
}
