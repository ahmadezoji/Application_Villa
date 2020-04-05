package com.apsent.villapp;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.apsent.villapp.R;

public class AutenticatInsertDialog extends DialogFragment {
    private Context context;
    private Integer Autheticatekey;

    public void setAutheticatekey(Integer autheticatekey)
    {
        Autheticatekey=autheticatekey;
    }
    public void setContext(Context context) {
        this.context = context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.insert_autenticate_key,container);




        ((Button)(view.findViewById(R.id.Btn_Ok)))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String Inserted=
                                ((EditText)(view.findViewById(R.id.txt_autenticate_key)))
                                        .getText().toString();
                        if (String.valueOf(Autheticatekey).equals(Inserted))
                        {
                            ((Ownerstate)context).checkAutenticateUser();
                            dismiss();
                        }
                        else {
                            Toast.makeText(context, "Mismatch Text", Toast.LENGTH_LONG).show();
                        }

                    }
                });


        ((Button)(view.findViewById(R.id.Btn_Cancle)))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                });

        return  view;
    }
}

