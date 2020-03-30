package com.apsent.villapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.absent.villaapp.R;

public class UserLoginActivity extends AppCompatActivity {
    public UserCotroller usercontroller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        new DatabaseHelper(this).getReadableDatabase();
        usercontroller=new UserCotroller();

        String phone = Utils.readPrefrences(this,Utils.PFREFRENCE_USER_LOGIN,Utils.PFREFRENCE_USER_LOGIN_KEY);
        if(!TextUtils.isEmpty(phone))
        {
            Users user=usercontroller.getUser(phone);
            if(user!=null)
            {
                goForward(user);
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

        EditText phone =
                ((EditText)(findViewById(R.id.Login_phone)));

        Users user=usercontroller.getUser(phone.getText().toString());
        if(user!=null)
        {
            goForward(user);
        }

    }
    private void goForward(Users user)
    {
        Intent intent;
        if(user.getType() == 0)//Customer
            intent = new Intent(UserLoginActivity.this, MainActivityAdmin.class);
        else
            intent = new Intent(UserLoginActivity.this, MainActivityCustomer.class);

        Utils.writePreferences(this,Utils.PFREFRENCE_USER_LOGIN,Utils.PFREFRENCE_USER_LOGIN_KEY,user.getPhone());

        intent.putExtra("user",user);
        startActivity(intent);
    }
}
