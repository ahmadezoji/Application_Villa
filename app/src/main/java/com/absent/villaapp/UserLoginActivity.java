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
    private APIs jsonAPI ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        new DatabaseHelper(this).getReadableDatabase();

//        prepareBuy("1250000");

    }
    void prepareBuy(String price)
    {
//        ZarinPal purchase = ZarinPal.getPurchase(this);
//        PaymentRequest payment  = ZarinPal.getPaymentRequest();
//
//        payment.setMerchantID("71c705f8-bd37-11e6-aa0c-000c295eb8fc");
//        payment.setAmount(Integer.valueOf(price));
//        payment.setDescription("خرید اشتراک");
//        payment.setCallbackURL("app://app");     /* Your App Scheme */
//        payment.setMobile("09195835135");            /* Optional Parameters */
//
//        purchase.startPayment(payment,  new OnCallbackRequestPaymentListener() {
//            @Override
//            public void onCallbackResultPaymentRequest(int status, String authority, Uri paymentGatewayUri, Intent intent) {
//
//
//                if (status == 100) {
//                   /*
//                   When Status is 100 Open Zarinpal PG on Browser
//                   */
//                    startActivity(intent);
//                } else {
//                    Toast.makeText(getApplicationContext(), "به درگاه متصل نشد :(", Toast.LENGTH_LONG).show();
//                }
//
//            }
//        });

    }

    public class GetApi extends AsyncTask<String,Object,List<Users>>
    {
        @Override
        protected List<Users> doInBackground(String... strings) {
            try {
                List<Users> users=new ArrayList<>();
                String strApi = new OkHttpClient().newCall(
                        new Request.Builder()
                                .url("http://84.241.1.59:9191/users/find" +
                                        "?username=" + strings[0] + "&password=" + strings[1] + "")
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
                users1.setPhoneNumber(jsonObject.getString("phone"));
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

    private void getusers(){
        Call<List<Users>> call = jsonAPI.getusers();
        call.enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                if (response.isSuccessful())
                {
                    List<Users> users=new ArrayList<>();
                    users=response.body();
                }
            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {

            }
        });
    }
}
