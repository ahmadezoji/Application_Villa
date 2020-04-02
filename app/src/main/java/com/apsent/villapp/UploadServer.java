package com.apsent.villapp;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import java.io.File;
import java.util.UUID;

public class UploadServer {

    private Context context;

    public UploadServer() {
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public class UploadTask extends AsyncTask<File,Object,String>
    {
        private final MediaType MEDIA_TYPE_IMAGE = MediaType.parse("image/*");
        @Override
        protected String doInBackground(File... files) {
            try {

                String fileName = UUID.randomUUID().toString() + ".jpg";
                RequestBody requestBody = new MultipartBuilder()
                        .type(MultipartBuilder.FORM)
                        .addFormDataPart("file", fileName, RequestBody.create(MEDIA_TYPE_IMAGE, files[0]))
                        .build();

                String strApi = new OkHttpClient().newCall(
                        new Request.Builder()
                                .url(Utils.BASE_URL + "upload/image?file")
                                .post(requestBody)
                                .build()
                )
                        .execute()
                        .body()
                        .string();


                return strApi;

            } catch (Exception e)
            {
                return null;
            }
        }
    }
    public String uploadImage(Uri uri) {
        try {
            File file = new File(getPath(uri));
            return new UploadTask().execute(file).get();
        }catch (Exception e) {
            return null;
        }
    }
    //method to get the file path from uri
    public String getPath(Uri uri) {
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = context.getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

}
