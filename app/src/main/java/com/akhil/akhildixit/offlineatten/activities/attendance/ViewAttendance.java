package com.akhil.akhildixit.offlineatten.activities.attendance;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.akhil.akhildixit.offlineatten.R;
import com.akhil.akhildixit.offlineatten.database.StoreAttendance;
import com.akhil.akhildixit.offlineatten.database.StoreStudentDetails;
import com.akhil.akhildixit.offlineatten.settergetter.Student;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;

public class ViewAttendance extends AppCompatActivity {

    Spinner spinner;
    ListView listView;
    TextView dots;
    Intent intent;
    String mainTable;
    String dateTable;
    ArrayList<String> dateArrayList;
    ArrayList<Bitmap> arrayListBitmap;
    String table;
    Button find;


    String date;
    ArrayList<Student> data;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewattendance);
        listView=(ListView)findViewById(R.id.viewattendance_list);
        spinner=(Spinner) findViewById(R.id.viewattendance_spinner);
        dots=(TextView) findViewById(R.id.viewattendance_dots);
        find=(Button)findViewById(R.id.viewattendance_button);
        intent=getIntent();

        table=intent.getStringExtra("viewAttendance");
        mainTable=intent.getStringExtra("viewAttendance")+"_STUDENT_ATTENDANCE";
        dateTable=mainTable+"_DATES";

        fontAwesome();
        listenerOnDots();

        getdates();
        setSpinnerData();

        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayout=(LinearLayout)findViewById(R.id.viewattendance_linear);
                setListData();
                YoYo.with(Techniques.BounceIn).duration(700).playOn(linearLayout);
                linearLayout.setVisibility(View.VISIBLE);
            }
        });

    }

    public void setSpinnerData()
    {
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,R.layout.spinnerviewstudent,dateArrayList);
        spinner.setAdapter(arrayAdapter);


    }
    public void getdates()
    {
        StoreAttendance st=new StoreAttendance(this);
        dateArrayList= st.getAllDates(dateTable);
    }

    public void listenerOnDots()
    {
        dots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu= new PopupMenu(ViewAttendance.this,dots);
                popupMenu.getMenuInflater().inflate(R.menu.viewattendance,popupMenu.getMenu());
                popupMenu.show();
            }
        });
    }


    public void setListData()
    {
        String date=(String)spinner.getSelectedItem();


        StoreAttendance st=new StoreAttendance(this);
        data=st.getAllAttendance(date,mainTable);

        StoreStudentDetails sd=new StoreStudentDetails(this,table+"_STUDENT");
        arrayListBitmap=sd.getAllImages();
        Log.e("The images arraylist",arrayListBitmap.size()+"");

        listView.setAdapter(new ViewAttendanceCustomList(this,data,arrayListBitmap));
        listView.setClickable(false);
    }

    public void fontAwesome() {
        Typeface fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");

        TextView book=(TextView)findViewById(R.id.viewattendance_appbar_book);
        TextView dots=(TextView)findViewById(R.id.viewattendance_dots);

        book.setTypeface(fontAwesomeFont);
        dots.setTypeface(fontAwesomeFont);
    }

}
