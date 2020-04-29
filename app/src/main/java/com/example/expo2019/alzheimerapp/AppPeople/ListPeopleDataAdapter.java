package com.example.expo2019.alzheimerapp.AppPeople;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Geocoder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.expo2019.alzheimerapp.HomeActivity;
import com.example.expo2019.alzheimerapp.R;

import java.util.List;
import java.util.Locale;

public class ListPeopleDataAdapter extends RecyclerView.Adapter<ListPeopleDataAdapter.ListPeopleViewHolder>  {


    Context context;
    List<People> listPeopleData;

     ListPeopleDataAdapter(Context context, List<People> listPeopleData) {
//         Log.d("oooooooooooooo", "ListPeopleDataAdapter: Constructor "+listPeopleData.get(0).getName());
       // Toast.makeText(context,"InSIDE CONTRUCTOR OF ADAPTER ",Toast.LENGTH_SHORT).show();
        this.listPeopleData = listPeopleData;
        this.context = context;
    }

    @NonNull
    @Override
    public ListPeopleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       // Toast.makeText(context,"InSIDE oncreateview OF ADAPTER ",Toast.LENGTH_SHORT).show();
        View view = LayoutInflater.from(context).inflate(R.layout.album_card,viewGroup,false);
        return new ListPeopleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListPeopleViewHolder listPeopleViewHolder, int i) {

        //Toast.makeText(context,"InSIDE onBind ADAPTER "+i,Toast.LENGTH_LONG).show();
        People people = listPeopleData.get(i);

        Log.d("**testttttt", "onBindViewHolder: "+people.getName());
        listPeopleViewHolder.tv_name.setText(people.getName());
        listPeopleViewHolder.tv_relation.setText(people.getRelation());
        listPeopleViewHolder.tv_phone.setText(String.valueOf(people.getPhone()));
        listPeopleViewHolder.tv_address.setText(people.getAddresss());
        listPeopleViewHolder.dial_btn.setTag(String.valueOf(people.getPhone()));


       if (people.getImage()!=null){
           //convert byte array to bitmap
           Bitmap bitmap = BitmapFactory.decodeByteArray(people.getImage(), 0, people.getImage().length);
           listPeopleViewHolder.imageView.setImageBitmap(bitmap);
       }

       //Listener for dial btn
        listPeopleViewHolder.dial_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(Intent.ACTION_DIAL);
                in.setData(Uri.parse("tel:"+listPeopleViewHolder.dial_btn.getTag().toString()));
                context.startActivity(in);
            }
        });

    }



    @Override
    public int getItemCount() {
        //Toast.makeText(context,"InSIDE getintcount OF ADAPTER "+listPeopleData.size(),Toast.LENGTH_SHORT).show();
        Log.d("TeST**", "getItemCount: "+listPeopleData.size());
        return listPeopleData.size();
    }
    class ListPeopleViewHolder extends RecyclerView.ViewHolder {

       public TextView tv_name, tv_phone, tv_address,tv_relation;
       public ImageView imageView;
       public Button dial_btn;


        public ListPeopleViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d("*******CHECK**********", "ListPeopleViewHolder: "+"InSIDE CONSTRUCTOR OF VIEWHOLDER");
            this.tv_name = itemView.findViewById(R.id.name_tv);
            this.tv_relation = itemView.findViewById(R.id.relation_tv);
            this.tv_phone = itemView.findViewById(R.id.phone_tv);
            this.tv_address = itemView.findViewById(R.id.address_tv);
            this.imageView = itemView.findViewById(R.id.image_albumCard);
            this.dial_btn = itemView.findViewById(R.id.dial_btn);
        }
    }
}

