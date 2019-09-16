package com.absent.villaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class UserLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new DatabaseHelper(this).getReadableDatabase();


    }

    public class GetApi extends AsyncTask<String,Object,List<Users>>
    {
        @Override
        protected List<Users> doInBackground(String... strings) {
            try {
                List<Users> users=new ArrayList<>();
                String strApi = new OkHttpClient().newCall(
                        new Request.Builder()
                                .url("http://84.241.1.59:9191/ServiceVilla/searchuser" +
                                        "?uname=" + strings[0] + "&upass=" + strings[1] + "")
                                .build()
                )
                        .execute()
                        .body()
                        .string();

                JSONArray jsonArray=new JSONArray(strApi);
                JSONObject jsonObject=jsonArray.getJSONObject(0);
                /*Add To User*/
                Users users1= new Users();
                users1.setUserId(jsonObject.getInt("id"));
                users1.setUsername(jsonObject.getString("username"));
                users1.setPassword(jsonObject.getString("password"));
                users1.setPhoneNumber(jsonObject.getString("phoneNumber"));
                users1.setType(jsonObject.getInt("type"));

                users.add(users1);
            return  users;
            }
            catch (Exception e)
            {
                return null;
            }

        }
        @Override
        protected void onPostExecute(List<Users> users) {
            if (users!=null) {
                if (users.get(0).getType() == 0)//admin
                {
                    Intent intent = new Intent(UserLoginActivity.this, MainActivityAdmin.class);
                    intent.putExtra("user", users.get(0));
                    startActivity(intent);
                }
                if (users.get(0).getType() == 1)//Customer
                {
                    Intent intent = new Intent(UserLoginActivity.this, MainActivityCustomer.class);
                    intent.putExtra("user", users.get(0));
                    startActivity(intent);
                }
            }
            else
            {
                Toast.makeText(UserLoginActivity.this,"UserName Or Password Are Incorrect !!",Toast.LENGTH_LONG).show();
            }
        }
    }
    public void onclick_btnSignup_Show(View view)
    {
        Intent intent = new Intent(UserLoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }
    public void onclick_btn(View view)
    {
        EditText uname=
                ((EditText)(findViewById(R.id.m_name)));
        EditText upass =
                ((EditText)(findViewById(R.id.m_pass)));

       new GetApi().execute(new String[]{uname.getText().toString(),upass.getText().toString()});

    }
}
