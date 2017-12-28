package com.akhil.akhildixit.offlineatten.activities.studentdetails;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.akhil.akhildixit.offlineatten.R;
import com.akhil.akhildixit.offlineatten.database.StoreAttendance;
import com.akhil.akhildixit.offlineatten.database.StoreStudentDetails;
import com.akhil.akhildixit.offlineatten.settergetter.PieChartData;
import com.akhil.akhildixit.offlineatten.settergetter.StudentEntry;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Akhil Dixit on 8/26/2017.
 */

public class ViewStudent extends AppCompatActivity {

    ImageView imageView;
    Intent intent;
    String rollNumber;
    String table;
    String attendancetable;
    String name;
    Bitmap imageBitmap;
    byte[] bitmaparray;
    TextView classesAttended;
    PieChart pieChart;
    ArrayList<Entry> yvalues=new ArrayList<>();
    int totalAttendance, attendance;

    FloatingActionButton camera;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewstudent_details);
        imageView=(ImageView)findViewById(R.id.viewstudent_details_imageview);
        camera=(FloatingActionButton)findViewById(R.id.viewstudent_details_floatingbtn);

        pieChart=(PieChart)findViewById(R.id.viewstudent_details_piechart);
        classesAttended=(TextView)findViewById(R.id.viewstudent_details_classesattended);



        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                YoYo.with(Techniques.Swing).duration(2000).playOn(imageView);

                 ImagePopup imagePopup=new ImagePopup(ViewStudent.this);
                imagePopup.setWindowHeight(500); // Optional
                imagePopup.setWindowWidth(600); // Optional
                imagePopup.setBackgroundColor(Color.TRANSPARENT);  // Optional
                imagePopup.setHideCloseIcon(true);  // Optional
                imagePopup.setImageOnClickClose(true);  // Optional
                imagePopup.initiatePopup(imageView.getDrawable());

            }
        });


        intent=getIntent();

        if (intent!=null)
        {
            name=intent.getStringExtra("name");
            table=intent.getStringExtra("table");
           rollNumber=intent.getStringExtra("rollnumber");
            attendancetable=table+"_ATTENDANCE";
        }
        TextView rollText=(TextView)findViewById(R.id.viewstudent_details_rollnumber);
        TextView nameText=(TextView)findViewById(R.id.viewstudent_details_name);
        rollText.setText(rollNumber);
        nameText.setText(name);

        getData();

        makeCircleImage();

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,1);
            }
        });
        setAttendanceData();

}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data!=null) {
            Uri uri = data.getData();
            File file = new File(uri.getPath());
            updateImage(uri);
        }
    }


    public void setAttendanceData()
    {
        Log.e("The table name is ","att"+attendancetable);
        StoreAttendance st=new StoreAttendance(this);

 PieChartData pie=st.getPresentAttendance(rollNumber,name,attendancetable);

        if (pie!=null)
        {
            attendance=pie.presentAtten;
            totalAttendance=pie.totalAtten;
        classesAttended.setText(pie.presentAtten+"/"+totalAttendance);
            setPieChart();
        }}
    public void updateImage(Uri uri)
    {
        Bitmap bitmap1=null;
        Bitmap bitmap=null;
        try {
            bitmap1= MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
            bitmap=Bitmap.createScaledBitmap(bitmap1,1000,1000,true);

            byte[] demoArray=getBitmapAsByteArray(bitmap1);
            StoreStudentDetails st=new StoreStudentDetails(this,table);
           Boolean done= st.updateImage(rollNumber,bitmap,name);
            if (done)
            {
                Glide.with(this).load(demoArray).asBitmap().into(new BitmapImageViewTarget(imageView) {
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });

            }
        } catch (IOException e) {

            Log.e("Problem in decoding","prob:: "+e);
        }
    }
    public void getData()
{

    StoreStudentDetails st=new StoreStudentDetails(this,table);
   imageBitmap= st.getImage(rollNumber,name);
    bitmaparray=getBitmapAsByteArray(imageBitmap);

}
    public byte[] getBitmapAsByteArray(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,0,byteArrayOutputStream);

        return byteArrayOutputStream.toByteArray();
    }
public void makeCircleImage()
{
   // Glide.with(this).load(bitmaparray).asBitmap().into(imageView);

    Glide.with(this).load(bitmaparray).asBitmap().into(new BitmapImageViewTarget(imageView) {
        protected void setResource(Bitmap resource) {
            RoundedBitmapDrawable circularBitmapDrawable =
                    RoundedBitmapDrawableFactory.create(getResources(), resource);
            circularBitmapDrawable.setCircular(true);
            imageView.setImageDrawable(circularBitmapDrawable);
    }
    });
    }

    public void setPieChart()
    {
        pieChart.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.BounceInUp).duration(700).playOn(pieChart);
      Float atten=(Float.valueOf(attendance)*100)/(Float.valueOf(totalAttendance));
        Float notatten=100f-atten;

        yvalues.add(new Entry(attendance,0));
        yvalues.add(new Entry(notatten,1));


        pieChart.setUsePercentValues(true);
        PieDataSet dataSet = new PieDataSet(yvalues, "Attendance Results");

        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        ArrayList<String> xVals = new ArrayList<String>();

        xVals.add("Attendance");
        xVals.add("Not attended");


        PieData data = new PieData(xVals, dataSet);
        pieChart.setData(data);

    }
}



