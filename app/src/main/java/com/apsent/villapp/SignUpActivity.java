package com.apsent.villapp;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.absent.villaapp.R;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.util.Calendar;

public class SignUpActivity extends AppCompatActivity implements Ownerstate{
    private APIs apIs;
    private int AutenticatKey;
    Users currentuser;

    public UserCotroller usercontroller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        usercontroller=new UserCotroller();
    }
    public void onclick_btnSignup(View view)
    {
        currentuser=new Users();

        String username=
        ((EditText)(findViewById(R.id.m_usernameSignUP)))
                .getText().toString();

        String phonenumber=
                ((EditText)(findViewById(R.id.m_phonSignUP)))
                        .getText().toString();


        if(!usercontroller.IsExist(phonenumber)) {
            currentuser.setName(username);
            currentuser.setPhone(phonenumber);
            currentuser.setType(1);


            AutenticatKey = getAutenticateKey();
            Sms sms = new Sms("mehr_afraz", "Samtema Key : " + String.valueOf(AutenticatKey), currentuser.getPhone());
            if (Sms.IsValidPhone(currentuser.getPhone())) {
                if (sms.Send()) {
                    AutenticatInsertDialog dialog=new AutenticatInsertDialog();
                    dialog.setContext(SignUpActivity.this);
                    dialog.setStyle(DialogFragment.STYLE_NO_TITLE,0);
                    dialog.setAutheticatekey(AutenticatKey);
                    FragmentManager fm =(SignUpActivity.this).getFragmentManager();
                    dialog.show(fm,"");

                }
            }
        }
        else {
            Toast.makeText(this, "شما قبلا ثبت نام کرده اید", Toast.LENGTH_SHORT).show();
        }



    }
       public class SendKeytoPhoneTask extends AsyncTask<String,Object,Integer>
    {
        @Override
        protected void onPostExecute(Integer integer) {
            if (integer==1)//send To Phone Success
            {
                AutenticatInsertDialog dialog=new AutenticatInsertDialog();
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

    }

    public int getAutenticateKey()
    {
        int max=9999;
        int min=1000;


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
        if(usercontroller.AddUser(currentuser))
            Toast.makeText(this, "User sign up success !!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "User sign up error !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void set_date(String date) {

    }

    @Override
    public int daysBetween(Calendar startDate, Calendar endDate) {
    return 0;
    }

}
