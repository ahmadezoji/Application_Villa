package com.apsent.villapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

public class DeleteDialog extends DialogFragment {
    private Context context;
    private Villa villa;
    private VillaListOwner villaListOwner;

    public void setVillaListOwner(VillaListOwner villaListOwner) {
        this.villaListOwner = villaListOwner;
    }
    public void setContext(Context context) {
        this.context = context;
    }

    public void setVilla(Villa villa) {
        this.villa = villa;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(context)
                .setMessage("Delete this Villa?")
                .setTitle("Delete")
                .setIcon(android.R.drawable.ic_delete)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        new DatabaseHelper(context).deleteVilla(villa);
                        villaListOwner.deleteVilla(villa);
                        villaListOwner.filllist();


                    }

                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dismiss();
                    }
                })


                .create();
    }


}
