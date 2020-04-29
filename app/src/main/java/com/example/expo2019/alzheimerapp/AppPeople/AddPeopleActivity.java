package com.example.expo2019.alzheimerapp.AppPeople;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.expo2019.alzheimerapp.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddPeopleActivity extends AppCompatActivity {

    EditText et_name, et_phone, et_address,et_relation;
    Button bt_selectImg, bt_captureImg;
    FloatingActionButton bt_addPeople;
    ImageView imageView;
    private static final int REQUEST_IMAGE_CAPTURE = 0;
    private static final int IMG_PICK_CODE = 1;

    //instance variables
    String name,phone,address,relation;
    Uri imageUri;
    Bitmap imageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_people);

        // define views
        et_address = findViewById(R.id.address_et);
        et_phone = findViewById(R.id.phone_et);
        et_name = findViewById(R.id.name_et);
        et_relation = findViewById(R.id.relation_et);

        bt_captureImg = findViewById(R.id.catureImg_btn);
        bt_selectImg = findViewById(R.id.selectimg_btn);
        bt_addPeople = findViewById(R.id.addPeople_btn);

        imageView = findViewById(R.id.imageView);

        // Capture image from camera
        bt_captureImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

        //Select image from gallery
        bt_selectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in_chooseimg = new Intent();
                in_chooseimg.setType("image/*");
                in_chooseimg.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
                in_chooseimg.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(in_chooseimg, "Select Image"), IMG_PICK_CODE);
            }
        });
        bt_addPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = et_name.getText().toString();
                phone = et_phone.getText().toString();
                address = et_address.getText().toString();
                relation = et_relation.getText().toString();


                ByteArrayOutputStream baos=new  ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
                byte [] b=baos.toByteArray();
                String imageString =Base64.encodeToString(b, Base64.DEFAULT);

                // save data in background
                SavePeopleData savePeopleData = new SavePeopleData(getApplicationContext());
                savePeopleData.execute(name,phone,address,imageString,relation);
            }


        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // for captures image
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }
        // for gallery image
        if (requestCode == IMG_PICK_CODE && resultCode == RESULT_OK ){
            imageUri = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageBitmap = bitmap;
            imageView.setImageBitmap(bitmap);

        }
    }
}
