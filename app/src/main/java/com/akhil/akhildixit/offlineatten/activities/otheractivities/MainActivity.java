package com.akhil.akhildixit.offlineatten.activities.otheractivities;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.akhil.akhildixit.offlineatten.R;
import com.akhil.akhildixit.offlineatten.activities.attendance.ViewAttendance;
import com.akhil.akhildixit.offlineatten.activities.attendance.quickatten.TakeQuickAttendance;
import com.akhil.akhildixit.offlineatten.activities.attendance.takeAttendance;
import com.akhil.akhildixit.offlineatten.activities.branchandsection.AddBatch;
import com.akhil.akhildixit.offlineatten.activities.studentdetails.AddStudent;
import com.akhil.akhildixit.offlineatten.database.Connection;
import com.akhil.akhildixit.offlineatten.database.Schedule;
import com.akhil.akhildixit.offlineatten.database.StoreAttendance;
import com.akhil.akhildixit.offlineatten.database.StoreStudentDetails;
import com.akhil.akhildixit.offlineatten.settergetter.scheduleDateAndTime;
import com.akhil.akhildixit.offlineatten.settergetter.setaddBatchData;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.Random;

import static android.graphics.Color.rgb;

public class MainActivity extends AppCompatActivity {

  //  FloatingActionButton floatingActionButton;
    //com.github.clans.fab.FloatingActionButton importExcel,studentDetails,schedule,addSubject;
    //FloatingActionMenu floatingActionMenu;

    FloatingActionButton floatingActionButton;
    float dx,dy;
    int lastAction;
    ViewGroup insert;
    ViewGroup scheduleViewGroup;
    Bundle st;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        st=savedInstanceState;

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 8);

        floatingActionButton=(FloatingActionButton)findViewById(R.id.main_floating);

        setFloatingActionButton();
        insertViews();
        fontAwesomeAppbar();
        GradientDrawable gd = new GradientDrawable();/*set corner radius for dynamic views*/
        View appbar=findViewById(R.id.appbar_layout);
        gd.setColor(Color.parseColor("#3F4247"));
       gd.setCornerRadius(10);
        appbar.setBackground(gd);
        /**/

        setAppBarPopUp();

    }
    public void setAppBarPopUp()
    {
        final TextView dots=(TextView)findViewById(R.id.appbar_dots);
        dots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                PopupMenu popupMenu=new PopupMenu(MainActivity.this,dots);
                popupMenu.getMenuInflater().inflate(R.menu.main_appbar,popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                      /*  MenuItem teacher = item.getSubMenu().getItem(0);
                        MenuItem student = item.getSubMenu().getItem(1);
                        MenuItem sync = item.getSubMenu().getItem(2);*/
                        Snackbar.make(insert,"Sorry this feature is under development..", BaseTransientBottomBar.LENGTH_SHORT).show();

                       //teacher.setChecked(true);
                        switch (item.getItemId())
                        {
                            case R.id.teacher:
                               // MenuItem subMenuItem = item.getSubMenu().getItem(0);
                                //teacher.setChecked(!teacher.isChecked());
                                MenuItem item1=(MenuItem)findViewById(R.id.teacher);
                              // item.getSubMenu().getItem(0) .setChecked(true);
                                break;
                            case R.id.student:
                              //  student.setChecked(!teacher.isChecked());
                                break;

                            case R.id.sync:
                                //sync.setChecked(!teacher.isChecked());
                                break;


                        }
                        return false;
                    }
                });

            }
        });
    }
    public void setFloatingActionButton()
    {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AddBatch.class);
                intent.putExtra("from","direct");
                startActivity(intent);

                finish();
            }
        });

    }



    public void insertViews()
    {


        LayoutInflater inflater= (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);/*Set height and width here of view*/
      //  lp.setMargins(0,10,5,0);
        //lp.setMargins(5,10,1,100);

        lp.setMargins(8,10,12,40);


        ArrayList<setaddBatchData> arrayList=new ArrayList<>();

        Connection connection=new Connection(this);
        arrayList=connection.getRecordFromMasterTable();

        if (arrayList!=null) {

            LinearLayout linearLayout=(LinearLayout)findViewById(R.id.main_linear);
            linearLayout.removeAllViews();

            YoYo.with(Techniques.FadeIn).duration(1000).playOn(linearLayout);
            for (int i = 0; i < arrayList.size(); i++) {


                insert = (ViewGroup) findViewById(R.id.main_linear);
                View view = inflater.inflate(R.layout.mainactivity_customviews, null);

                TextView branch = (TextView) view.findViewById(R.id.custom_branch);
                TextView batch = (TextView) view.findViewById(R.id.custom_batch);
                TextView year = (TextView) view.findViewById(R.id.custom_year);
                TextView course = (TextView) view.findViewById(R.id.custom_course);
                TextView type = (TextView) view.findViewById(R.id.custom_type);

                setDynamicViewBtn(view);
                setaddBatchData set = new setaddBatchData();
                set = arrayList.get(i);

                branch.setText(set.branch);
                batch.setText(set.batch);
                year.setText(set.year);
                course.setText(set.course);
                type.setText(set.type);


                fontAwesome(view);
                /*Changes*/

                int position=com.akhil.akhildixit.offlineatten.settergetter.Color.position;
                GradientDrawable gd = new GradientDrawable();

                if (position== com.akhil.akhildixit.offlineatten.settergetter.Color.color.length)
                {
                    com.akhil.akhildixit.offlineatten.settergetter.Color.position=0;
                    position=0;
                }
               gd.setColor(com.akhil.akhildixit.offlineatten.settergetter.Color.color[position]);
              //  gd.setColor(Color.RED);
                gd.setCornerRadius(25);
                view.setBackground(gd);

                /**/
                YoYo.with(Techniques.FlipInX).duration(2000).delay(10).playOn(view);

                insert.addView(view, i, lp);
                com.akhil.akhildixit.offlineatten.settergetter.Color.position++;
                Log.e("The i is ", "i " + i + " " + view);

            }
            Log.e("Arraylist sizw", "" + arrayList.size());

            insert=(ViewGroup)findViewById(R.id.main_linear);
            View view=inflater.inflate(R.layout.space,null);
            insert.addView(view);

        }
        else {
            insert = (ViewGroup) findViewById(R.id.main_linear);
            View view = inflater.inflate(R.layout.mainactivity_nothing_present, null);

            Typeface fontAwesomeFont = Typeface.createFromAsset(getAssets(), "star.ttf");
            TextView txt=(TextView)view.findViewById(R.id.nothing);
            txt.setTypeface(fontAwesomeFont);


            insert.addView(view);

        }


    }

   public void setDynamicViewBtn(final View view)
    {

        Button attendance=(Button)view.findViewById(R.id.main_btn_atten);
        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView branch=(TextView)view.findViewById(R.id.custom_branch);
                TextView batch=(TextView)view.findViewById(R.id.custom_batch);
                TextView year=(TextView)view.findViewById(R.id.custom_year);
                TextView course=(TextView)view.findViewById(R.id.custom_course);
                TextView type=(TextView)view.findViewById(R.id.custom_type);

                String branchName=branch.getText().toString();
                String typeName=type.getText().toString();
                String batchName=batch.getText().toString();
                String yearName=year.getText().toString();
                String courseName=course.getText().toString();

                setaddBatchData set=new setaddBatchData();
                set.branch=branchName;
                set.batch=batchName;
                set.year=yearName;
                set.course=courseName;
                set.type=typeName;

                String[] branchSplit=branch.getText().toString().toUpperCase().trim().split("\\s+");
                String[] batchSplit=batch.getText().toString().toUpperCase().trim().split("\\s+");
                String[] courseSplit=course.getText().toString().toUpperCase().trim().split("\\s+");

                String branchString="",batchString="",courseString="";

                for (int i=0;i<branchSplit.length;i++)
                {
                    branchString+=branchSplit[i];
                }

                for (int i=0;i<batchSplit.length;i++)
                {
                    batchString+=batchSplit[i];
                }

                for (int i=0;i<courseSplit.length;i++)
                {
                    courseString+=courseSplit[i];
                }

                String tableName= branchString+"_"+batchString+"_"+courseString+"_"+yearName+"_"+typeName+"_STUDENT";


                Intent intent=new Intent(MainActivity.this,takeAttendance.class);
                intent.putExtra("attendanceDetails",set);
                intent.putExtra("studenttable",tableName);

                StoreStudentDetails st=new StoreStudentDetails(MainActivity.this,tableName);
                Boolean isAnyDataPresent=st.isAnyStudentAdded();
                if (isAnyDataPresent) {
                    startActivity(intent);
                }
                else {
                    AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("SORRY");
                    builder.setMessage("Sorry no students are added");
                    builder.show();
                }

            }
        });

        attendance.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setPositiveButton("VIEW ATTENDANCE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TextView branch=(TextView)view.findViewById(R.id.custom_branch);
                        TextView batch=(TextView)view.findViewById(R.id.custom_batch);
                        TextView year=(TextView)view.findViewById(R.id.custom_year);
                        TextView course=(TextView)view.findViewById(R.id.custom_course);
                        TextView type=(TextView)view.findViewById(R.id.custom_type);

                        String branchName=branch.getText().toString();
                        String typeName=type.getText().toString();
                        String batchName=batch.getText().toString();
                        String yearName=year.getText().toString();
                        String courseName=course.getText().toString();

                        setaddBatchData set=new setaddBatchData();
                        set.branch=branchName;
                        set.batch=batchName;
                        set.year=yearName;
                        set.course=courseName;
                        set.type=typeName;

                        String[] branchSplit=branch.getText().toString().toUpperCase().trim().split("\\s+");
                        String[] batchSplit=batch.getText().toString().toUpperCase().trim().split("\\s+");
                        String[] courseSplit=course.getText().toString().toUpperCase().trim().split("\\s+");

                        String branchString="",batchString="",courseString="";

                        for (int i=0;i<branchSplit.length;i++)
                        {
                            branchString+=branchSplit[i];
                        }

                        for (int i=0;i<batchSplit.length;i++)
                        {
                            batchString+=batchSplit[i];
                        }

                        for (int i=0;i<courseSplit.length;i++)
                        {
                            courseString+=courseSplit[i];
                        }

                        String tableName= branchString+"_"+batchString+"_"+courseString+"_"+yearName+"_"+typeName;

                        String checkingIfAnyAttendanceThere= branchString+"_"+batchString+"_"+courseString+"_"+yearName+"_"+typeName+"_STUDENT_ATTENDANCE";

                        /**/
                        StoreAttendance storeAttendance=new StoreAttendance(MainActivity.this);
                        Boolean isTablePresent=storeAttendance.checkIfAttendanceExistForView(checkingIfAnyAttendanceThere);
                        /**/


                        if (isTablePresent) {
                            Intent intent = new Intent(MainActivity.this, ViewAttendance.class);
                            intent.putExtra("viewAttendance", tableName);
                            startActivity(intent);
                        }
                        else {
                            AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle("SORRY");
                            builder.setMessage("You have not yet taken up any attendance");
                            builder.show();
                        }



                    }
                });
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });

                builder.setMessage("Please choose your choice...");
                builder.setTitle("Choose");
                builder.show();
                return false;

            }


        });
        final Button show=(Button)view.findViewById(R.id.main_btn_show_hide);
        show.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                scheduleViewGroup = (ViewGroup) view.findViewById(R.id.main_dynamic_schedule_add);
                LinearLayout showSchedule=(LinearLayout)view.findViewById(R.id.main_custom_schedule);

                LayoutInflater inflater= (LayoutInflater)MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                //  lp.setMargins(8,10,12,40);

               if (showSchedule.getVisibility()==showSchedule.GONE)

               {
                   YoYo.with(Techniques.SlideInLeft).duration(700).repeat(0).playOn(showSchedule);
                   TransitionManager.beginDelayedTransition(insert);
                showSchedule.setVisibility(View.VISIBLE);
                   show.setText(R.string.arrowup);
                   /*changes here*/




                   TextView branch=(TextView)view.findViewById(R.id.custom_branch);
                   TextView batch=(TextView)view.findViewById(R.id.custom_batch);
                   TextView year=(TextView)view.findViewById(R.id.custom_year);
                   TextView course=(TextView)view.findViewById(R.id.custom_course);

                   TextView type=(TextView)view.findViewById(R.id.custom_type);

                  // String branchName=branch.getText().toString().toUpperCase();
                   //String batchName=batch.getText().toString().toUpperCase();
                   String yearName=year.getText().toString().toUpperCase();
                   //String courseName=course.getText().toString().toUpperCase();


                   String[] branchSplit=branch.getText().toString().toUpperCase().trim().split("\\s+");
                   String[] batchSplit=batch.getText().toString().toUpperCase().trim().split("\\s+");
                   String[] courseSplit=course.getText().toString().toUpperCase().trim().split("\\s+");

                   String branchString="",batchString="",courseString="";

                   for (int i=0;i<branchSplit.length;i++)
                   {
                       branchString+=branchSplit[i];
                   }

                   for (int i=0;i<batchSplit.length;i++)
                   {
                       batchString+=batchSplit[i];
                   }

                   for (int i=0;i<courseSplit.length;i++)
                   {
                       courseString+=courseSplit[i];
                   }

                   String tableName= branchString+"_"+batchString+"_"+courseString+"_"+yearName+"_"+type.getText().toString()+"_SCHEDULE";
                  // String tableName="ECE_C_DIGITAL_1st_LECTURE";
                   Schedule schedule=new Schedule(MainActivity.this,tableName);
                   ArrayList<scheduleDateAndTime> arrayList=schedule.getSchedule();


                   if (arrayList!=null ) {

                       Log.e("above null", "w");
                       if (true) {
                           for (int i = 0; i < arrayList.size(); i++) {


                               View view1 = inflater.inflate(R.layout.mainactivity_customviews_schedule, null);
                               Log.e("ArrayList not null", "works");
                               scheduleDateAndTime sc = arrayList.get(i);


                               Log.e("The day is :",sc.day);

                               TextView day = (TextView) view1.findViewById(R.id.schedule_day);
                               TextView time = (TextView) view1.findViewById(R.id.schedule_time);

                               day.setText(sc.day);
                               time.setText(sc.time);
                               scheduleViewGroup.addView(view1,0, lp);

                           }
                       }
                   }

                   /*Here*/

               }
               else {
                   YoYo.with(Techniques.SlideOutLeft).duration(700).repeat(0).playOn(showSchedule);
                   TransitionManager.beginDelayedTransition(insert);
                   showSchedule.setVisibility(View.GONE);
                   show.setText(R.string.arrowdown);
                   scheduleViewGroup.removeAllViews();
               }
            }
        });




        Button delete=(Button)view.findViewById(R.id.main_btn_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {


                TextView branch=(TextView)view.findViewById(R.id.custom_branch);
                TextView batch=(TextView)view.findViewById(R.id.custom_batch);
                TextView year=(TextView)view.findViewById(R.id.custom_year);
                TextView course=(TextView)view.findViewById(R.id.custom_course);
                TextView type=(TextView)view.findViewById(R.id.custom_type);

                String branchName=branch.getText().toString();
                String typeName=type.getText().toString();
                String batchName=batch.getText().toString();
                String yearName=year.getText().toString();
                String courseName=course.getText().toString();

                setaddBatchData set=new setaddBatchData();
                set.branch=branchName;
                set.batch=batchName;
                set.year=yearName;
                set.course=courseName;
                set.type=typeName;

                showDialogBox(set,view);

            }
        });


        Button edit=(Button)view.findViewById(R.id.main_btn_edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                TextView branch=(TextView)view.findViewById(R.id.custom_branch);
                TextView batch=(TextView)view.findViewById(R.id.custom_batch);
                TextView year=(TextView)view.findViewById(R.id.custom_year);
                TextView course=(TextView)view.findViewById(R.id.custom_course);
                TextView type=(TextView)view.findViewById(R.id.custom_type);

                String branchName=branch.getText().toString();
                String typeName=type.getText().toString();
                String batchName=batch.getText().toString();
                String yearName=year.getText().toString();
                String courseName=course.getText().toString();

                final setaddBatchData set=new setaddBatchData();
                set.branch=branchName;
                set.batch=batchName;
                set.year=yearName;
                set.course=courseName;
                set.type=typeName;




                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);

                builder.setMessage("Are you sure you want to edit...");
              final  String tableName= branchName+"_"+batchName+"_"+courseName+"_"+yearName+"_"+typeName+"_SCHEDULE";
                final String oldTableName=branchName+"_"+batchName+"_"+courseName+"_"+yearName+"_"+typeName;

                builder.setCancelable(true);

                builder.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            public void onClick(DialogInterface dialog, int id) {
                                Schedule schedule=new Schedule(MainActivity.this,tableName);
                                ArrayList<scheduleDateAndTime> arrayList=schedule.getSchedule();

                                Intent intent=new Intent(MainActivity.this,AddBatch.class);
                                intent.putExtra("mytablename",oldTableName);
                                intent.putExtra("batch_data", set);
                                intent.putExtra("from","main");
                                Bundle bundle=new Bundle();
                                bundle.putSerializable("timetable",arrayList);
                                intent.putExtras(bundle);
                                startActivity(intent);

                                finish();
                            }
                        });

                builder.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog=builder.create();
                alertDialog.show();


            }
        });


        Button addStudent=(Button)view.findViewById(R.id.main_btn_addStudents);
        addStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                TextView branch=(TextView)view.findViewById(R.id.custom_branch);
                TextView batch=(TextView)view.findViewById(R.id.custom_batch);
                TextView year=(TextView)view.findViewById(R.id.custom_year);
                TextView course=(TextView)view.findViewById(R.id.custom_course);
                TextView type=(TextView)view.findViewById(R.id.custom_type);

                /*String branchName=branch.getText().toString().toUpperCase();*/
                String typeName=type.getText().toString().toUpperCase();
               // String batchName=batch.getText().toString().toUpperCase();
                String yearName=year.getText().toString().toUpperCase();
               // String courseName=course.getText().toString().toUpperCase();

                String[] branchSplit=branch.getText().toString().toUpperCase().trim().split("\\s+");
                String[] batchSplit=batch.getText().toString().toUpperCase().trim().split("\\s+");
                String[] courseSplit=course.getText().toString().toUpperCase().trim().split("\\s+");

                String branchString="",batchString="",courseString="";

                for (int i=0;i<branchSplit.length;i++)
                {
                    branchString+=branchSplit[i];
                }

                for (int i=0;i<batchSplit.length;i++)
                {
                    batchString+=batchSplit[i];
                }

                for (int i=0;i<courseSplit.length;i++)
                {
                    courseString+=courseSplit[i];
                }

                String tableName= branchString+"_"+batchString+"_"+courseString+"_"+yearName+"_"+typeName;

                //String tableName=branchName+"_"+batchName+"_"+courseName+"_"+yearName+"_"+typeName;
                Intent intent=new Intent(MainActivity.this, AddStudent.class);
                intent.putExtra("studentTableName",tableName);
                startActivity(intent);
            }
        });

        addStudent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Dialog dialog=new Dialog(MainActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.quickattendancenumber);


                dialog.setCancelable(false);

                dialog.show();

               return false;
        }}
        );


    }




    public void showDialogBox(final setaddBatchData set,final View view)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);

        builder.setMessage("Are you sure you want to delete the record..");
        builder.setCancelable(true);

        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    public void onClick(DialogInterface dialog, int id) {


                        Connection connection=new Connection(MainActivity.this);
                        connection.deleteRecord(set);

                        Handler handler=new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                TransitionManager.beginDelayedTransition(insert);
                                insert.removeView(view);

                            }
                        },500);

                    }
                });

        builder.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

    public void fontAwesome(View view)
    {
        Typeface fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");

        TextView user=(TextView) view.findViewById(R.id.main_text_year);
        TextView course=(TextView) view.findViewById(R.id.main_text_course);
        TextView clock=(TextView) view.findViewById(R.id.main_text_clock);

        Button btn1=(Button)view.findViewById(R.id.main_btn_atten);
        Button btn2=(Button)view.findViewById(R.id.main_btn_edit);
        Button btn3=(Button)view.findViewById(R.id.main_btn_delete);
        Button btn4=(Button)view.findViewById(R.id.main_btn_addStudents);
        Button btn5=(Button)view.findViewById(R.id.main_btn_show_hide);






        btn1.setTypeface(fontAwesomeFont);
        btn2.setTypeface(fontAwesomeFont);

        btn3.setTypeface(fontAwesomeFont);
        btn4.setTypeface(fontAwesomeFont);
        btn5.setTypeface(fontAwesomeFont);

        course.setTypeface(fontAwesomeFont);
        clock.setTypeface(fontAwesomeFont);
        user.setTypeface(fontAwesomeFont);


    }

    public void fontAwesomeAppbar()
    {
        Typeface fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
        Typeface fontAwesomeFont1 = Typeface.createFromAsset(getAssets(), "mysubject.ttf");

        TextView books=(TextView)findViewById(R.id.main_activity_books);
        TextView appbardots=(TextView)findViewById(R.id.appbar_dots);
        TextView subject=(TextView)findViewById(R.id.mainac_mysubje);
        subject.setTypeface(fontAwesomeFont1);




        appbardots.setTypeface(fontAwesomeFont);

        books.setTypeface(fontAwesomeFont);
    }
    @Override
    protected void onResume() {
        super.onResume();


    }
}
