package com.absent.villaapp;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Edit_Dialog extends DialogFragment {
    private Context context;
    private Villa villa;
    private VillaListOwner villaListOwner;

    public void setVillaListOwner(VillaListOwner villaListOwner) {
        this.villaListOwner = villaListOwner;
    }

    public void setVilla(Villa villa) {
        this.villa = villa;
    }

    public void setContext(Context context) {
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.edit_dialog,container);
//        villa.setCost(Integer.valueOf(
//                ((EditText)view.findViewById(R.id.txt_price)).getText().toString()));
//
//        villa.setArea(Integer.valueOf(
//                ((EditText)view.findViewById(R.id.txt_area)).getText().toString()));
//
//        villa.setCapacity(Integer.valueOf(
//                ((EditText)view.findViewById(R.id.txt_capacity)).getText().toString()));

        ((EditText)view.findViewById(R.id.txt_address_editDlg)).setText(villa.getAddress());
        ((EditText)view.findViewById(R.id.txt_price_editDlg)).setText(String.valueOf(villa.getCost()));
        ((EditText)view.findViewById(R.id.txt_roomcnt_editDlg)).setText(String.valueOf(villa.getRoomCount()));

        ((Button)view.findViewById(R.id.btn_update)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //new DatabaseHelper(context).updateVilla(villa);
                villaListOwner.filllist();
                Toast.makeText(context,"Villa Edited",Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
        ((Button)view.findViewById(R.id.btn_dismiss)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return view;
    }

}
