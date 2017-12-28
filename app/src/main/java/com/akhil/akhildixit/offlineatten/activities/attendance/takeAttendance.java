package com.akhil.akhildixit.offlineatten.activities.attendance;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.akhil.akhildixit.offlineatten.R;
import com.akhil.akhildixit.offlineatten.activities.otheractivities.MainActivity;
import com.akhil.akhildixit.offlineatten.database.StoreAttendance;
import com.akhil.akhildixit.offlineatten.database.StoreStudentDetails;
import com.akhil.akhildixit.offlineatten.exceltosqlconvert.SQLiteToExcelMe;
import com.akhil.akhildixit.offlineatten.settergetter.Student;
import com.akhil.akhildixit.offlineatten.settergetter.setaddBatchData;
import com.github.clans.fab.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Akhil Dixit on 8/28/2017.
 */

public class takeAttendance extends AppCompatActivity {
    //Button allPresent,allAbsent,save,export;
    ListView listView;
    String branch,batch,course,year,type;
    String tableName;  /*this is the name to get the roll number present in ECE_C*/
    String attendanceTable;  /*this is to save attendance like in ECE_C_Analog_Atten*/

    ArrayList<String> rollnumber=new ArrayList<>();
    ArrayList<String> name=new ArrayList<>();
    View view;
    String status="P";
    AttendanceCustomList customList;
    setaddBatchData set;
    ArrayList<Bitmap> arrayListBitmap;
    int totalCount;

   public static Boolean hasSaved=false;

    FloatingActionButton save,export;

    String imagesTableName;

    HashMap<String,Bitmap> imagesHashmap=new HashMap<>();

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.take_attendance);

      //  allPresent=(Button)findViewById(R.id.allPresent);
        //allAbsent=(Button)findViewById(R.id.allAbsent);
        listView=(ListView)findViewById(R.id.attendanceList);
      //  save=(Button)findViewById(R.id.saveAttendance);
        //export=(Button)findViewById(R.id.exportAttendance);

        save=(FloatingActionButton)findViewById(R.id.saveAttendance);
        export=(FloatingActionButton)findViewById(R.id.exportAttendance);

        applyListeners();
        fontAwesome();

        final  String directoryPath= Environment.getExternalStorageDirectory().toString()+ File.separator+"Mark Up Exported-Excels";


        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        final Intent intent=getIntent();
        set= (setaddBatchData) intent.getSerializableExtra("attendanceDetails");


        branch=set.branch;
        batch=set.batch;
        course=set.course;
        year=set.year;
        type=set.type;

        tableName=intent.getStringExtra("studenttable");
        attendanceTable=tableName+"_ATTENDANCE";
        getRollNameDatabase();
        getDataResult();

/*

        allPresent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (status=="A")
                {
                    status="P";
                    customList.changeStatus(status);
                }

            }
        });

        allAbsent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (status=="P")
                {
                    status="A";
                    customList.changeStatus(status);
                }

            }
        });
*/

        save.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                ArrayList<Student> arrayList=new ArrayList<Student>();

                View view;
                TextView status;
                TextView roll;
                TextView name;

                Log.e("In save btn",""+listView.getCount());

                for (int i=0;i<listView.getCount();i++)
                {
                    String dates="";
                    Date date=new Date();
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MMM dd MM, yyyy");
                    dates=simpleDateFormat.format(date);
                    Log.e("the date is ","Date:"+dates);

                    view=listView.getChildAt(i);
                    status=(TextView)view.findViewById(R.id.customStatus);
                    roll=(TextView)view.findViewById(R.id.customRoll);
                    name=(TextView)view.findViewById(R.id.customName);

                    Log.e("The status is ",""+status.getText().toString());

                    Student student=new Student(roll.getText().toString(),name.getText().toString(),
                            dates,status.getText().toString());
                    arrayList.add(student);
                }
                StoreAttendance st=new StoreAttendance(takeAttendance.this);
                // st.storeAttendance(attendanceTable,arrayList);
                // st.checkIfAttendanceHasAlreadyStored(attendanceTable,arrayList);

                Boolean isTablePresent=st.checkIfTableAlreadyExists(attendanceTable);

                if (isTablePresent)
                {
                    st.checkIfAttendanceHasAlreadyStored(attendanceTable,arrayList);
                }
                else if (isTablePresent==false)
                {
                    st.storeAttendance(attendanceTable,arrayList);
                }


            }
        });

        export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (hasSaved) {
                    SQLiteToExcelMe sqliteToExcel = new SQLiteToExcelMe(takeAttendance.this, "offlineAtten.db", directoryPath);
                    sqliteToExcel.exportSingleTable(attendanceTable, attendanceTable + ".xls", new SQLiteToExcelMe.ExportListener() {
                        @Override
                        public void onStart() {
                            Log.e("In exporting", "In expoerti");
                        }

                        @Override
                        public void onCompleted(String filePath) {

                            Toast.makeText(takeAttendance.this, "Successfully exported data", Toast.LENGTH_LONG).show();
                            Log.e("Completed", "Complete");
                        }

                        @Override
                        public void onError(Exception e) {

                            Log.e("Export fail", "Export failed due to : " + e);
                            Snackbar.make(listView,"Export failed Try again...", BaseTransientBottomBar.LENGTH_LONG).show();

                        }
                    });
                }
                else
                {
                    Snackbar.make(listView,"Looks like you have not yet saved data...", BaseTransientBottomBar.LENGTH_LONG).show();
                }

            }
        });

    }




    public void getRollNameDatabase()
    {

        StoreStudentDetails sd=new StoreStudentDetails(this,tableName);
        rollnumber=sd.getRollNumbers();
        name=sd.getNames();
        totalCount=rollnumber.size();

        arrayListBitmap=sd.getAllImages();
        Log.e("In take atte","s "+arrayListBitmap.size());
    }
    public void getDataResult()
    {
        customList=new AttendanceCustomList(this, rollnumber,name,status,arrayListBitmap);
        listView.setAdapter(customList);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                TextView isPresent=(TextView)view.findViewById(R.id.customStatus);

                if(isPresent.getText().toString().equals("P"))
                {
                    isPresent.setText("A");
                }
                else if (isPresent.getText().toString().equals("A"))
                {
                    isPresent.setText("P");
                }
            }
        });
    }

    public void fontAwesome()
    {
        Typeface fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");

        TextView book = (TextView) findViewById(R.id.attendance_appbar_dots);
        TextView dots=(TextView)findViewById(R.id.attendance_appbar_book);

        book.setTypeface(fontAwesomeFont);
        dots.setTypeface(fontAwesomeFont);
    }

    public void applyListeners()
    {
        final TextView dots=(TextView)findViewById(R.id.attendance_appbar_dots);
        dots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu=new PopupMenu(takeAttendance.this,dots);
                popupMenu.getMenuInflater().inflate(R.menu.takeattendance,popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId())
                        {
                            case R.id.takeAtteAllP:  if (status=="A")
                            {
                                status="P";
                                customList.changeStatus(status);
                            }
                            break;
                            case R.id.takeAtteAllAb:  if (status=="P")
                            {
                                status="A";
                                customList.changeStatus(status);
                            }
                            break;
                            case R.id.takeAttereport:

                                if (hasSaved)
                                {
                                    StoreAttendance st=new StoreAttendance(takeAttendance.this);
                                   int count= st.generateAttendanceReport(tableName+"_ATTENDANCE");
                                    int absent=totalCount-count;
                                   AlertDialog.Builder builder=new AlertDialog.Builder(takeAttendance.this);
                                    builder.setTitle("Report");
                                    builder.setMessage("Hello there .. in your class there are "+totalCount+" students out of which "+count+" are present and "+absent+" are absent");

                                    builder.show();
                                }


                                break;
                        }
                        return false;
                    }
                });

                popupMenu.show();
            }
        });

    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit");
        builder.setTitle("Exit");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent(takeAttendance.this, MainActivity.class);
              //  startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}


