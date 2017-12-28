package com.akhil.akhildixit.offlineatten.activities.studentdetails;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.akhil.akhildixit.offlineatten.R;
import com.akhil.akhildixit.offlineatten.activities.attendance.takeAttendance;
import com.akhil.akhildixit.offlineatten.activities.otheractivities.MainActivity;
import com.akhil.akhildixit.offlineatten.database.Connection;
import com.akhil.akhildixit.offlineatten.database.StoreStudentDetails;
import com.akhil.akhildixit.offlineatten.exceltosqlconvert.SQLiteToExcelMe;
import com.akhil.akhildixit.offlineatten.settergetter.StudentEntry;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Akhil Dixit on 8/25/2017.
 */

public class AddStudent extends AppCompatActivity {


    FloatingActionButton save;
    String nameString="null",rollnumber="null";
    String tableName;
    ViewGroup insert;
    LayoutInflater layoutInflater;
    TextView dots;
    final  String directoryPath= Environment.getExternalStorageDirectory().toString()+ File.separator+"Mark Up Exported-Excels";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addstudent);

        save=(FloatingActionButton)findViewById(R.id.addstudent_floatBtn);
        setFloatingButton();
        dots=(TextView)findViewById(R.id.addstudent_appbar_dots);

        dots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v7.widget.PopupMenu popupMenu = new android.support.v7.widget.PopupMenu(AddStudent.this, dots);
                popupMenu.getMenuInflater().inflate(R.menu.student, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new android.support.v7.widget.PopupMenu.OnMenuItemClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId()==R.id.student_export)
                        {
                            SQLiteToExcelMe sqliteToExcel = new SQLiteToExcelMe(AddStudent.this, "offlineAtten.db", directoryPath);
                            sqliteToExcel.exportSingleTable(tableName, tableName + ".xls", new SQLiteToExcelMe.ExportListener() {
                                @Override
                                public void onStart() {
                                    Log.e("In exporting", "In expoerti");
                                }

                                @Override
                                public void onCompleted(String filePath) {

                                    Toast.makeText(AddStudent.this, "Successfully exported data", Toast.LENGTH_LONG).show();
                                    Log.e("Completed", "Complete");
                                }

                                @Override
                                public void onError(Exception e) {

                                    Log.e("Export fail", "Export failed due to : " + e);
                                    Snackbar.make(insert,"Export failed Try again...", BaseTransientBottomBar.LENGTH_LONG).show();

                                }
                            });
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }});



        Intent intent=getIntent();
        tableName=intent.getStringExtra("studentTableName")+"_STUDENT";
        Log.e("table is:","works: "+tableName);

        showList();
topFont();
        /*Curve for layout*/


        /**/
    }
    public void setFloatingButton()
    {
        save.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                setsaveAlertDialog();
            }
        });
    }

    public void setUserDefaultImage(StudentEntry studentEntry)

    {
        StoreStudentDetails st=new StoreStudentDetails(this,tableName);
        Bitmap bitmap1=null;
        Bitmap bitmap=null;
        bitmap1= BitmapFactory.decodeResource(getResources(),R.drawable.user);
        bitmap=Bitmap.createScaledBitmap(bitmap1,1000,1000,true);

        st.StoreImage(studentEntry,bitmap);
    }





    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setEditAlertDialog(final String rolls, final String names)
    {
        LayoutInflater inflater=(LayoutInflater)AddStudent.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view=inflater.inflate(R.layout.addstudent_inputdata,null);

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setView(view);


        TextView rollt=(TextView)view.findViewById(R.id.addstudent_inputdata_textroll);
        TextView namet=(TextView)view.findViewById(R.id.addstudent_inputdata_textname);

        rollt.setVisibility(View.VISIBLE);
        namet.setVisibility(View.VISIBLE);

         EditText roll=(EditText)view.findViewById(R.id.addstudent_inputdata_roll);
         EditText name=(EditText)view.findViewById(R.id.addstudent_inputdata_name);

        YoYo.with(Techniques.Landing).duration(2000).repeat(0).playOn(rollt);
        YoYo.with(Techniques.Landing).duration(2000).repeat(0).playOn(namet);

        roll.setText(rolls);
        name.setText(names);


        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Dialog f=(Dialog)dialog;
                final EditText roll=(EditText)f.findViewById(R.id.addstudent_inputdata_roll);
                final EditText name=(EditText)f.findViewById(R.id.addstudent_inputdata_name);

                rollnumber=roll.getText().toString();
                nameString=name.getText().toString();
                Log.e("In ok","works::"+roll.getText().toString());
                if (rollnumber.trim().equals("") | nameString.trim().equals(""))
                {

                    ScrollView scrollView=(ScrollView)findViewById(R.id.addstudent_scrollview);
                    //Toast.makeText(AddStudent.this,"sorry empty values",Toast.LENGTH_LONG).show();
                    Snackbar.make(scrollView,"Sorry you entered empty values..", BaseTransientBottomBar.LENGTH_SHORT).show();

                }
                else {
                    updateData(rollnumber, nameString, rolls, names);
                    removeViews();
                    showList();
                }
            }
        });
        builder.show();


    }
    public void updateData(String rollnew,String namenew,String rollold,String nameold)
    {
        StoreStudentDetails st=new StoreStudentDetails(this,tableName);
       Boolean isUpdated= st.updateRecord(rollnew,namenew,rollold,nameold);
        removeViews();

    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setsaveAlertDialog()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setView(R.layout.addstudent_inputdata);
        LayoutInflater inflater=(LayoutInflater)AddStudent.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.addstudent_inputdata,null);


        TextView rollt=(TextView)view.findViewById(R.id.addstudent_inputdata_textroll);
        TextView namet=(TextView)view.findViewById(R.id.addstudent_inputdata_textname);

        rollt.setVisibility(View.VISIBLE);
        namet.setVisibility(View.VISIBLE);

        YoYo.with(Techniques.SlideInUp).duration(700).delay(3000).repeat(0).playOn(rollt);
        YoYo.with(Techniques.SlideInUp).duration(700).repeat(0).playOn(namet);



        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Dialog f=(Dialog)dialog;
                final EditText roll=(EditText)f.findViewById(R.id.addstudent_inputdata_roll);
                final EditText name=(EditText)f.findViewById(R.id.addstudent_inputdata_name);


                rollnumber=roll.getText().toString();
                nameString=name.getText().toString();
                Log.e("In ok","works::"+roll.getText().toString());

                if (rollnumber.trim().equals("") | nameString.trim().equals(""))
                {

                    ScrollView scrollView=(ScrollView)findViewById(R.id.addstudent_scrollview);
                    //Toast.makeText(AddStudent.this,"sorry empty values",Toast.LENGTH_LONG).show();
                    Snackbar.make(scrollView,"Sorry you entered empty values..", BaseTransientBottomBar.LENGTH_SHORT).show();

                    }
                else
                {
                    storeData();

                    StudentEntry studentEntry = new StudentEntry();
                    studentEntry.rollnumber = rollnumber;
                    studentEntry.name = nameString;
                    setUserDefaultImage(studentEntry);


                    removeViews();
                    showList();


                }
            }
        });
        builder.show();
    }

    public void removeViews()
    {
        insert=(ViewGroup)findViewById(R.id.addstudent_linear);
        if (insert!=null)
        {
            insert.removeAllViews();
        }
    }
    public void showList()
    {

        StoreStudentDetails storeStudentDetails=new StoreStudentDetails(this,tableName);
        ArrayList<StudentEntry> st=storeStudentDetails.getData();

        if (st!=null)
        {
            removeViews();
            for (int i=0;i<st.size();i++)
            {
                StudentEntry studentEntry=st.get(i);
                Log.e("student entrydetailsare","works:: "+studentEntry.rollnumber);
                addViews(studentEntry,i);

            }
        }

    }
    public void addViews(StudentEntry studentEntry,int i)
    {
    layoutInflater=(LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        insert=(ViewGroup)findViewById(R.id.addstudent_linear);
        View view=layoutInflater.inflate(R.layout.addstudent_customview,null);
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.setMargins(50,15,60,15);

        setListenersForList( studentEntry, i,view);

        fontAwesome(view);
        insert.addView(view,i,lp);

    }
    public void setListenersForList(StudentEntry studentEntry, int i, final View view)
    {
        TextView count=(TextView)view.findViewById(R.id.addstudent_customview_count);
        final TextView rollnumeber=(TextView)view.findViewById(R.id.addstudent_customview_roll);
        final View demo=(View)view.findViewById(R.id.demo);
        final TextView nameT=(TextView)view.findViewById(R.id.addstudent_customview_name);
        final Button popup=(Button)view.findViewById(R.id.addstudent_customview_listmenu);
        count.setText((i+1)+"");
        nameT.setText(studentEntry.name);
        rollnumeber.setText(studentEntry.rollnumber);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddStudent.this,ViewStudent.class);
                intent.putExtra("rollnumber",rollnumeber.getText().toString());
                intent.putExtra("table",tableName);
                intent.putExtra("name",nameT.getText().toString());
                startActivity(intent);
            }
        });


        popup.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

                PopupMenu popupMenu=new PopupMenu(AddStudent.this,v, ViewGroup.MarginLayoutParams.WRAP_CONTENT);
                popupMenu.getMenuInflater().inflate(R.menu.addstudent_list_popup,popupMenu.getMenu());
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId()==R.id.addstudent_pop_edit)
                        {

                            setEditAlertDialog(rollnumeber.getText().toString(),nameT.getText().toString());
                        }
                        else if (item.getItemId()==R.id.addstudent_pop_delete)
                        {

                            android.support.v7.app.AlertDialog.Builder builder=new android.support.v7.app.AlertDialog.Builder(AddStudent.this);

                            builder.setMessage("Are you sure you want to delete the record..");
                            builder.setCancelable(true);

                            builder.setPositiveButton(
                                    "Yes",
                                    new DialogInterface.OnClickListener() {
                                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                        public void onClick(DialogInterface dialog, int id) {


                                            StoreStudentDetails storeStudentDetails=new StoreStudentDetails(AddStudent.this,tableName);
                                            Boolean bool=storeStudentDetails.deleteRecord(rollnumeber.getText().toString(),nameT.getText().toString());

                                            if (bool)
                                            {
                                                Toast.makeText(AddStudent.this,"Success delete..",Toast.LENGTH_LONG).show();
                                                insert.removeView(view);
                                            }
                                            Handler handler=new Handler();
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    TransitionManager.beginDelayedTransition(insert);
                                                    insert.removeView(view);

                                                }
                                            },2000);

                                        }
                                    });

                            builder.setNegativeButton(
                                    "No",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                            android.support.v7.app.AlertDialog alertDialog=builder.create();
                            alertDialog.show();
                        }
                        return false;
                    }
                });


            }
        });
    }
    public void storeData()
    {

        StoreStudentDetails storeStudentDetails=new StoreStudentDetails(this,tableName);
        StudentEntry st=new StudentEntry();
        st.rollnumber=rollnumber;
        st.name=nameString;
        Log.e("In store data","works"+rollnumber+" "+nameString);

        Boolean done=storeStudentDetails.insertData(st);
        if (done!=null && done)
        {
            showList();
        }
        else if (done!=null && !done)
        {
            Toast.makeText(this,"Sorry some error occured..",Toast.LENGTH_LONG).show();
        }

    }

    public void fontAwesome(View view)
    {
        Typeface fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
        Button button=(Button) view.findViewById(R.id.addstudent_customview_listmenu);
        button.setTypeface(fontAwesomeFont);

    }
    public void topFont()
    {
        Typeface fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
        TextView student=(TextView)findViewById(R.id.addstudent_appbar_student);
        TextView dots=(TextView)findViewById(R.id.addstudent_appbar_dots);


        student.setTypeface(fontAwesomeFont);
        dots.setTypeface(fontAwesomeFont);

    }
}
