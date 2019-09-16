package com.absent.villaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class AdminInsertctivity extends AppCompatActivity {
    public Users CurrentUser;
    private Button btnCapture_Cam;
    private Button btnCapture_Gallery;
    private ImageView imgCapture;
    private Bitmap villaimgBMP;
    private static final int Image_Capture_Code = 1;
    public static final int RESULT_GALLERY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_insert);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        CurrentUser=(Users) bundle.get("user");
        ((TextView)(findViewById(R.id.m_Username_ShowVilla)))
                .setText(CurrentUser.getUsername());

        imgCapture = (ImageView) findViewById(R.id.capturedImage);
        /*click Capture By Gallery*/
        btnCapture_Gallery =(Button)findViewById(R.id.m_uploadBtn);
        btnCapture_Gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent , RESULT_GALLERY );
            }
        });

        /*Click Capture By Camera*/
        btnCapture_Cam =(Button)findViewById(R.id.btnTakePicture);

        btnCapture_Cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cInt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cInt,Image_Capture_Code);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Image_Capture_Code) {
            if (resultCode == RESULT_OK) {
                villaimgBMP = (Bitmap) data.getExtras().get("data");
                imgCapture.setImageBitmap(villaimgBMP);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == RESULT_GALLERY)     {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                if (cursor == null || cursor.getCount() < 1) {
                    return; // no cursor or no record. DO YOUR ERROR HANDLING
                }
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                if(columnIndex < 0) // no column index
                    return; // DO YOUR ERROR HANDLING
                String picturePath = cursor.getString(columnIndex);
                cursor.close(); // close cursor
                /*Set Image Into IMAGE_VIEW*/
                try{
                    File f= new File(picturePath);
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;

                    villaimgBMP = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
                    imgCapture.setImageBitmap(villaimgBMP);
                }
                catch (Exception e)
                {
                    imgCapture.setImageBitmap(null);
                    Toast.makeText(this,e.getMessage().toString(),Toast.LENGTH_LONG).show();
                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            }
        }
    }


    public void Onclick_BtnAdd(View view)
    {
        try{
                Villa villa=new Villa();

                EditText VCost=(EditText)(findViewById(R.id.VCost));
                EditText VRoomCnt=(EditText)(findViewById(R.id.VRoomCnt));
                EditText VCapacity=(EditText)(findViewById(R.id.VCapacity));
                EditText VArea=(EditText)(findViewById(R.id.VArea));
                EditText VAdderss=(EditText)(findViewById(R.id.VAdderss));

                villa.setCost(Integer.valueOf(VCost.getText().toString()));
                villa.setRoomCount(Integer.valueOf(VRoomCnt.getText().toString()));
                villa.setCapacity(Integer.valueOf(VCapacity.getText().toString()));
                villa.setArea(Integer.valueOf(VArea.getText().toString()));
                villa.setAddress(VAdderss.getText().toString());

                villa.setAdminUserId(CurrentUser.getUserId());

                String Coverpic="";
            if (villaimgBMP!=null) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                villaimgBMP.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArrayimg = stream.toByteArray();
//                Coverpic=Base64.getEncoder().encodeToString(byteArrayimg);
//                Coverpic = new Base64.Encoder().encode(byteArrayimg);
                villa.setPic(byteArrayimg);
            }
            else {
                villa.setPic(null);
            }



            new DatabaseHelper(this).AddVilla(villa);
            new AddVillaTask().execute(
                    VRoomCnt.getText().toString(),
                    VCapacity.getText().toString(),
                    VArea.getText().toString(),
                    VAdderss.getText().toString(),
                    Coverpic,
                    VCost.getText().toString(),
                    String.valueOf(CurrentUser.getUserId()));


            VCost.setText("");
            VRoomCnt.setText("");
            VCapacity.setText("");
            VArea.setText("");
            VAdderss.setText("");
            imgCapture.setImageBitmap(null);


        }
        catch (Exception e)
        {
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
   public class AddVillaTask extends AsyncTask<String,Object,List<Villa>>
   {
       @Override
       protected List<Villa> doInBackground(String... strings) {
           try {

               List<Villa> villas=new ArrayList<>();
               String strApi = new OkHttpClient().newCall(
                       new Request.Builder()
                               .url("http://84.241.1.59:9191/ServiceVilla/addvilla?" +
                                       "roomcnt="+strings[0]+"&capacity="+strings[1]+"&area="+strings[2]+
                                       "&address="+strings[3]+"&coverpic="+strings[4]+"" +
                                       "&cost="+strings[5]+"&ownerId="+strings[6]+"")
                               .build()
               )
                       .execute()
                       .body()
                       .string();
               /*Call back Fill list IF SUCCESS*/
               JSONArray jsonArray=new JSONArray(strApi);
               for (int i=0;i<jsonArray.length();i++) {
                   Villa villa=new Villa();
                   JSONObject jsonObject =jsonArray.getJSONObject(i);
                   villa.setVillaId(jsonObject.getInt("id"));
                   villa.setRoomCount(jsonObject.getInt("roomcnt"));
                   villa.setCapacity(jsonObject.getInt("capacity"));
                   villa.setArea(jsonObject.getInt("area"));
                   villa.setAddress(jsonObject.getString("address"));
                   villa.setPic((jsonObject.getString("coverpic")).getBytes());
                   villa.setCost(jsonObject.getInt("cost"));
                   villa.setAdminUserId(jsonObject.getInt("ownerId"));
                   villas.add(villa);

               }

               return villas;
           }
           catch (Exception e) {
               return null;
           }
       }

       @Override
       protected void onPostExecute(List<Villa> villas) {
           super.onPostExecute(villas);
       }
   }

    public void Onclick_BtnCancle(View view) {

        Intent intent=new Intent(this,MainActivityAdmin.class);
        intent.putExtra("user",CurrentUser);
        startActivity(intent);

    }

}
