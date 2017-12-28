package com.akhil.akhildixit.offlineatten.activities.attendance;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.akhil.akhildixit.offlineatten.R;
import com.akhil.akhildixit.offlineatten.activities.studentdetails.ViewStudent;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Akhil Dixit on 8/28/2017.
 */

public class AttendanceCustomList extends BaseAdapter {

    Context context;
    ArrayList<String> rollNumber;
    ArrayList<Bitmap> bitmapArrayList;
    ArrayList<String> name;
    LayoutInflater inflater;
    String statusString;
    ArrayList<String> keysOfHashMap;

    public  AttendanceCustomList(Context context, ArrayList<String> rollNumber, ArrayList<String> name,String status,ArrayList<Bitmap> bitmapArrayList)
    {
        this.context=context;
        this.rollNumber=rollNumber;
        this.name=name;
        this.statusString=status;
        this.bitmapArrayList=bitmapArrayList;
        inflater= (LayoutInflater)this.context.getSystemService(this.context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return rollNumber.size();
    }

    @Override
    public Object getItem(int position) {
        return rollNumber.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {


        view=inflater.inflate(R.layout.customlist,null);
        TextView roll=(TextView)view.findViewById(R.id.customRoll);
        TextView nameT=(TextView)view.findViewById(R.id.customName);
        TextView status=(TextView)view.findViewById(R.id.customStatus);
        final ImageView imageView=(ImageView)view.findViewById(R.id.customImageView);


        Log.e("The image arrayl","Size is :"+bitmapArrayList.size());



        if (bitmapArrayList!=null)
        {
        if (bitmapArrayList.size()>0)
        {
            Log.e("Arraylist","Not null");
            imageView.setImageBitmap(bitmapArrayList.get(position));
        }}
       // circularImage(imageView,bitmapArrayList.get(position));
//        imageView.setImageBitmap(bitmapArrayList.get(position));
        //imageView.setImageResource(R.drawable.user);

        //Log.e("Keys of hashmap are:",keysOfHashMap.get(position));

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePopup imagePopup=new ImagePopup(context);
                imagePopup.setWindowHeight(500); // Optional
                imagePopup.setWindowWidth(600); // Optional
                imagePopup.setBackgroundColor(Color.TRANSPARENT);  // Optional
                imagePopup.setHideCloseIcon(true);  // Optional
                imagePopup.setImageOnClickClose(true);  // Optional
                imagePopup.initiatePopup(imageView.getDrawable());
            }
        });

        // imageView.setImageBitmap(hashMap.get("Hsh"));

        roll.setText(rollNumber.get(position));
        nameT.setText(name.get(position));
        status.setText(statusString);

        return view;
    }

    public void changeStatus(String name)
    {
        statusString=name;
        notifyDataSetChanged();
    }

    public void circularImage(final ImageView imageView, Bitmap bitmap1)
    {
        byte[] demoArray=getBitmapAsByteArray(bitmap1);
        Glide.with(context).load(demoArray).asBitmap().into(new BitmapImageViewTarget(imageView) {
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });
    }
    public byte[] getBitmapAsByteArray(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,0,byteArrayOutputStream);

        return byteArrayOutputStream.toByteArray();
    }


}
