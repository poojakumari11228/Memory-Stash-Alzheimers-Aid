package com.example.expo2019.alzheimerapp.AppPeople;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.example.expo2019.alzheimerapp.DataBase;

import java.io.ByteArrayOutputStream;

class SavePeopleData extends AsyncTask<String,Void,String> {

    byte[] image;
    //constructor

    Context context;
    public SavePeopleData(Context context){
        this.context = context;
    }
    @Override
    protected String doInBackground(String... strings) {
       // Toast.makeText(getApplicationContext(),"do in background called",Toast.LENGTH_SHORT).show();
        try {
            // coonverrt String to bitmap
            byte [] encodeByte=Base64.decode(strings[3],Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            //convert bitmap to Array of byte
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
            image = stream.toByteArray();

        } catch(Exception e) {
            e.getMessage();
            return null;
        }
        Log.d("CHECK: ", "doInBackground: name "+strings[0]+" no: "+strings[1]);
                        People p = new People();
                        p.setName(strings[0]);
                        p.setPhone(""+strings[1]);
                        p.setAddresss(strings[2]);
                        p.setRelation(strings[4]);
                        p.setImage(image);
                        DataBase.getAppDatabase(context)
                                .peopleDao()
                                .insert(p);

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        Toast.makeText(context, " DATA SAVED!", Toast.LENGTH_SHORT).show();
    }
}
