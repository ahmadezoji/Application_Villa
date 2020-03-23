package com.absent.villaapp;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.zarinpal.ewallets.purchase.OnCallbackRequestPaymentListener;
import com.zarinpal.ewallets.purchase.PaymentRequest;
import com.zarinpal.ewallets.purchase.ZarinPal;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserLoginActivity extends AppCompatActivity {
    public UserCotroller usercontroller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        new DatabaseHelper(this).getReadableDatabase();
        usercontroller=new UserCotroller();

    }
    public void onclick_btnSignup_Show(View view)
    {
        Intent intent = new Intent(UserLoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }
    public void onclick_btn(View view)
    {
//        EditText uname=
//                ((EditText)(findViewById(R.id.m_name)));
        EditText phone =
                ((EditText)(findViewById(R.id.Login_phone)));

        Users user=usercontroller.getUser(phone.getText().toString());
        if(user!=null)
        {
            Intent intent;
            if(user.getType() == 0)//Customer
                intent = new Intent(UserLoginActivity.this, MainActivityAdmin.class);

            else
                intent = new Intent(UserLoginActivity.this, MainActivityCustomer.class);

            intent.putExtra("user",user);
            startActivity(intent);
        }

    }
}
