/*
package com.akhil.akhildixit.offlineatten.activities.branchandsection;

import android.Manifest;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.akhil.akhildixit.offlineatten.R;
import com.akhil.akhildixit.offlineatten.activities.otheractivities.MainActivity;
import com.akhil.akhildixit.offlineatten.database.Connection;
import com.akhil.akhildixit.offlineatten.database.Schedule;
import com.akhil.akhildixit.offlineatten.settergetter.scheduleDateAndTime;
import com.akhil.akhildixit.offlineatten.settergetter.setaddBatchData;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.wefika.horizontalpicker.HorizontalPicker;

import java.util.ArrayList;
import java.util.Date;

*/
/**
 * Created by Akhil Dixit on 8/21/2017.
 *//*


public class AddBatch extends AppCompatActivity {

    EditText branch,batch,course;
    RadioGroup radioGroup;
    Button addnew;
    LinearLayout linearLayout;
    HorizontalPicker horizontalPicker;
    FloatingActionButton floatingActionButton;
    TextView appbarCross;

    String time;
    String day;
    String newtabeName;
    String oldTableName;
    Intent intent;
    setaddBatchData set;

    ArrayList<setaddBatchData > scheduleData =new ArrayList<>();
    ArrayList<scheduleDateAndTime> setDate=new ArrayList<>();
    ViewGroup insert=null;



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbatch);

        appbarCross=(TextView)findViewById(R.id.addbatch_appbar_cross);

        intent =getIntent();

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);

        branch=(EditText)findViewById(R.id.addbatch_branch);
        batch=(EditText)findViewById(R.id.addbatch_batch);
        course=(EditText)findViewById(R.id.addbatch_course);

        radioGroup=(RadioGroup)findViewById(R.id.addbatch_radiogroup);

        addnew=(Button)findViewById(R.id.addbatch_addnew);

        linearLayout=(LinearLayout)findViewById(R.id.addbatch_addtimetable);

        horizontalPicker=(HorizontalPicker)findViewById(R.id.picker);

        floatingActionButton=(FloatingActionButton)findViewById(R.id.addbatch_savefab);

        fontAwesome();
        animations();

        applyPropertiesToEditextAndRadio();

        if (intent!=null && intent.getStringExtra("from").toString().equals("main"))
        {

            oldTableName=intent.getStringExtra("mytablename");

            TextView bran=(TextView)findViewById(R.id.anim_subjectName);
            TextView batc=(TextView)findViewById(R.id.anim_division);
            TextView cours=(TextView)findViewById(R.id.anim_departName);
            bran.setVisibility(View.VISIBLE);
            batc.setVisibility(View.VISIBLE);
            cours.setVisibility(View.VISIBLE);

            ArrayList<scheduleDateAndTime> st= (ArrayList<scheduleDateAndTime>) intent.getSerializableExtra("timetable");
            set= (setaddBatchData) intent.getSerializableExtra("batch_data");
            Log.e("ddddd","j"+set.course);
            branch.setText(set.branch);
            batch.setText(set.batch);
            course.setText(set.course);

            int id=0;
            switch (set.year)
            {
                case "1st":id=0;break;
                case "2nd":id=1;break;
                case "3rd":id=2;break;
                case "4th":id=3;break;
                case "5th":id=4;break;
                case "6th":id=5;break;
                case "7th":id=6;break;
                case "8th":id=7;break;
                case "9th":id=8;break;
                case "10th":id=9;break;
                case "11th":id=10;break;
                case "12th":id=11;break;

            }
            horizontalPicker.setSelectedItem(id);

            switch (set.type)
            {
                case "Lecture": radioGroup.check(R.id.addbatch_lecture);break;
                case "Practical":radioGroup.check(R.id.addbatch_practical);break;
                case "Seminar":radioGroup.check(R.id.addbatch_seminar);break;
                case "Workshop":radioGroup.check(R.id.addbatch_workshop);break;
                case "Exam":radioGroup.check(R.id.addbatch_exam);break;
            }

            if (st!=null) {

                for (int i=0;i<st.size();i++) {

                   final scheduleDateAndTime sc=st.get(i);
                    LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                    final View view = inflater.inflate(R.layout.scheduletimetable_custom, null);

                    Button time = (Button) view.findViewById(R.id.custom_time);
                    time.setText(sc.time);




                    Button cancel=(Button)view.findViewById(R.id.custom_cancel);


                     Spinner spinner=(Spinner)view.findViewById(R.id.custom_spinner);
                    ArrayList<String> arrayList=new ArrayList();
                    arrayList.add("Monday");arrayList.add("Tuesday");arrayList.add("Wednesday");
                    arrayList.add("Thursday");arrayList.add("Friday");arrayList.add("Saturday");
                    arrayList.add("Sunday");
                    ArrayAdapter arrayAdapter=new ArrayAdapter(AddBatch.this,R.layout.spinnerviewstudent,arrayList);
                    spinner.setAdapter(arrayAdapter);

                    for (int j=0;j<7;j++) {

                        if (spinner.getItemAtPosition(j).equals(sc.day))
                        {spinner.setSelection(j);
                            break;
                        }


                    }



                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            Log.e("No","e");
                            day=parent.getSelectedItem().toString();
                            Log.e("In spiner","w"+parent.getSelectedItem().toString());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            Log.e("No","e");
                        }
                    });





                    insert=(ViewGroup)findViewById(R.id.addbatch_addtimetable);
                    // insert.addView(view,0,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

                    LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    lp.setMargins(0,10,5,0);
                    insert.addView(view,insert.getChildCount()-1,lp);




                    time.setOnClickListener(new View.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onClick(View v) {

                            TimePicker(v);
                        }
                    });

                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            insert.removeView(view);

                        }


            });
        }
            }
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Boolean isValid=checkData();

                    if(isValid)
                    {
                        retrieveData();

                    }
                    else {
                        RelativeLayout layout=(RelativeLayout)findViewById(R.id.addbatch_relative);
                        Snackbar.make(layout,"Look Like Some Fields Are Empty.. ", BaseTransientBottomBar.LENGTH_SHORT).show();
                    }
                }
            });

        }




        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean isValid=checkData();

                if(isValid)
                {
                    retrieveData();

                }
                else {
                    RelativeLayout layout=(RelativeLayout)findViewById(R.id.addbatch_relative);
                    Snackbar.make(layout,"Look Like Some Fields Are Empty.. ", BaseTransientBottomBar.LENGTH_SHORT).show();
                }
            }
        });


        appbarCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(AddBatch.this);

                builder.setTitle("Are you sure you want to discard..");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent=new Intent(AddBatch.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }

        });


        createSchedule();


    }

    public void applyPropertiesToEditextAndRadio()
    {
       radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
           @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
           @Override
           public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {


               RadioButton lec=(RadioButton)findViewById(R.id.addbatch_lecture);
               RadioButton pract=(RadioButton)findViewById(R.id.addbatch_practical);
               RadioButton sem=(RadioButton)findViewById(R.id.addbatch_seminar);
               RadioButton work=(RadioButton)findViewById(R.id.addbatch_workshop);
               RadioButton exam=(RadioButton)findViewById(R.id.addbatch_exam);

               if (!lec.isChecked())
               {
                   lec.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.addbatcheditext)));
               }
               if (!pract.isChecked())
               {
                   pract.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.addbatcheditext)));
               }
               if (!sem.isChecked())
               {
                   sem.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.addbatcheditext)));
               }
               if (!work.isChecked())
               {
                   work.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.addbatcheditext)));
               }
               if (!exam.isChecked())
               {
                   exam.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.addbatcheditext)));
               }

               RadioButton checked;
               Log.e("HHH","GGGG");
               switch (checkedId)
               {
                   case R.id.addbatch_lecture:checked=(RadioButton)findViewById(R.id.addbatch_lecture);
                      checked.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.sunflower)));
                       break;
                   case R.id.addbatch_practical:
                       checked=(RadioButton)findViewById(R.id.addbatch_practical);

                       checked.setButtonTintList(ContextCompat.getColorStateList(AddBatch.this, R.color.sunflower));
                       break;
                   case R.id.addbatch_seminar:
                       checked=(RadioButton)findViewById(R.id.addbatch_seminar);
                       checked.setButtonTintList(ContextCompat.getColorStateList(AddBatch.this, R.color.sunflower));
                       break;
                   case R.id.addbatch_workshop: checked=(RadioButton)findViewById(R.id.addbatch_workshop);
                       checked.setButtonTintList(ContextCompat.getColorStateList(AddBatch.this, R.color.sunflower));
                       break;
                   case R.id.addbatch_exam:
                       checked=(RadioButton)findViewById(R.id.addbatch_exam);
                       checked.setButtonTintList(ContextCompat.getColorStateList(AddBatch.this, R.color.sunflower));
                       break;
               }
           }
       });
    }
    public void retrieveData()
    {
        setaddBatchData set=new setaddBatchData();

        set.branch=branch.getText().toString().trim();
       set.batch= batch.getText().toString().trim();
        set.course=course.getText().toString().trim();
        */
/*Horizontal picker*//*

       int idh= horizontalPicker.getSelectedItem();



        switch (idh)
        {
            case 0:set.year="1st";break;
            case 1:set.year="2nd";break;
            case 2:set.year="3rd";break;
            case 3:set.year="4th";break;
            case 4:set.year="5th";break;
            case 5:set.year="6th";break;
            case 6:set.year="7th";break;
            case 7:set.year="8th";break;
            case 8:set.year="9th";break;
            case 9:set.year="10th";break;
            case 10:set.year="11th";break;
            case 11:set.year="12th";break;

        }
        Log.e("The set type is ","works "+set.year);
        */
/*end*//*

        int id=radioGroup.getCheckedRadioButtonId();

        switch (id)
        {
            case R.id.addbatch_lecture:set.type="Lecture";
                break;


            case R.id.addbatch_seminar:set.type="Seminar";
                break;


            case R.id.addbatch_exam:set.type="Exam";
                break;

            case R.id.addbatch_practical:set.type="Practical";
                break;


            case R.id.addbatch_workshop:set.type="Workshop";
                break;
        }


        if (insert!=null && insert.getChildCount()>0)
        {

            for (int i=0;i<insert.getChildCount();i++)
            {
                scheduleDateAndTime sc=new scheduleDateAndTime();
                View view=insert.getChildAt(i);
                Button btn=(Button)view.findViewById(R.id.custom_time);
                Spinner spinner=(Spinner)view.findViewById(R.id.custom_spinner);


                sc.day=spinner.getSelectedItem().toString();
                sc.time=btn.getText().toString();

                setDate.add(sc);


            }
        }

        storeData(setDate,set,this.set);


    }

    public void createSchedule()
    {

        addnew.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                LayoutInflater inflater= (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                 final View view=inflater.inflate(R.layout.scheduletimetable_custom,null);


                Date date=new Date();
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm");
                String dates=simpleDateFormat.format(date);


                Button time=(Button)view.findViewById(R.id.custom_time);
                time.setText(dates);
                Button cancel=(Button)view.findViewById(R.id.custom_cancel);

               final Spinner spinner=(Spinner)view.findViewById(R.id.custom_spinner);


                */
/**//*

                ArrayList<String> arrayList=new ArrayList();
                arrayList.add("Monday");arrayList.add("Tuesday");arrayList.add("Wednesday");
                arrayList.add("Thursday");arrayList.add("Friday");arrayList.add("Saturday");
                arrayList.add("Sunday");
                ArrayAdapter arrayAdapter=new ArrayAdapter(AddBatch.this,R.layout.spinnerviewstudent,arrayList);
                spinner.setAdapter(arrayAdapter);


                */
/**//*



              //  spinner.setAdapter(arrayAdapter);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        Log.e("No","e");
                        day=parent.getSelectedItem().toString();
                        Log.e("In spiner","w"+parent.getSelectedItem().toString());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        Log.e("No","e");
                    }
                });

                 insert=(ViewGroup)findViewById(R.id.addbatch_addtimetable);
               // insert.addView(view,0,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

               LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                lp.setMargins(0,10,5,0);
                insert.addView(view,insert.getChildCount()-1,lp);




                time.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(View v) {

                        TimePicker(v);
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        insert.removeView(view);

                    }
                });
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void TimePicker(final View viewBtn)
    {
        Calendar calendar=Calendar.getInstance();
        TimePickerDialog timePickerDialog=new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                Button btn=(Button)viewBtn.findViewById(R.id.custom_time);
                btn.setText(""+hourOfDay+":"+minute);
                time =hourOfDay+":"+minute;
            }
        },calendar.HOUR_OF_DAY,calendar.MINUTE,true);

        timePickerDialog.show();
    }

    public void fontAwesome()
    {
        Typeface fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");

        TextView subjectDetails = (TextView) findViewById(R.id.font_subjectDetails);
        TextView selectType=(TextView)findViewById(R.id.font_selectType);
        TextView cancel=(TextView)findViewById(R.id.addbatch_appbar_cross);

        TextView year = (TextView) findViewById(R.id.font_year);
        TextView clock=(TextView)findViewById(R.id.font_schedule);

        TextView next = (TextView) findViewById(R.id.font_next);
        TextView previous=(TextView)findViewById(R.id.font_previous);

        subjectDetails.setTypeface(fontAwesomeFont);
        selectType.setTypeface(fontAwesomeFont);
        cancel.setTypeface(fontAwesomeFont);

        year.setTypeface(fontAwesomeFont);
        clock.setTypeface(fontAwesomeFont);

        next.setTypeface(fontAwesomeFont);
        previous.setTypeface(fontAwesomeFont);


    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void animations()
    {


        final TextView subject=(TextView)findViewById(R.id.anim_subjectName);

        final TextView depart=(TextView)findViewById(R.id.anim_departName);
        final TextView division=(TextView)findViewById(R.id.anim_division);


        course.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (course.hasFocus())
                {

                    course.getBackground().setColorFilter(getResources().getColor(R.color.sunflower),PorterDuff.Mode.SRC_ATOP);
                    if (course.getText().toString().equals(""))
                    {YoYo.with(Techniques.SlideInUp).duration(700).repeat(0).playOn(subject);
                        subject.setVisibility(View.VISIBLE);

                        Handler handler=new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                course.setHint("");
                            }
                        },400);
                    }

                     }
                     else {
                    course.getBackground().setColorFilter(getResources().getColor(R.color.addbatcheditext),PorterDuff.Mode.SRC_ATOP);
                    if (course.getText().toString().equals(""))
                    {
                        YoYo.with(Techniques.SlideOutDown).duration(700).repeat(0).playOn(subject);

                        course.setHint("Subject Name");


                    }
                }
            }
        });

        branch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (branch.hasFocus())
                {

                    branch.getBackground().setColorFilter(getResources().getColor(R.color.sunflower),PorterDuff.Mode.SRC_ATOP);
                    if (branch.getText().toString().equals(""))
                    {YoYo.with(Techniques.SlideInUp).duration(700).repeat(0).playOn(depart);
                        depart.setVisibility(View.VISIBLE);
                        Handler handler=new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                branch.setHint("");
                            }
                        },400);

                    }

                }
                else {
                    branch.getBackground().setColorFilter(getResources().getColor(R.color.addbatcheditext),PorterDuff.Mode.SRC_ATOP);
                    if (branch.getText().toString().equals(""))
                    {
                        YoYo.with(Techniques.SlideOutDown).duration(700).repeat(0).playOn(depart);
                        branch.setHint("Branch");


                    }
                }
            }
        });

        batch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (batch.hasFocus())
                {

                    batch.getBackground().setColorFilter(getResources().getColor(R.color.sunflower),PorterDuff.Mode.SRC_ATOP);
                    if (batch.getText().toString().equals(""))
                    {YoYo.with(Techniques.SlideInUp).duration(700).repeat(0).playOn(division);
                        division.setVisibility(View.VISIBLE);
                        Handler handler=new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                batch.setHint("");
                            }
                        },400);

                    }

                }
                else {
                    batch.getBackground().setColorFilter(getResources().getColor(R.color.addbatcheditext),PorterDuff.Mode.SRC_ATOP);
                    if (batch.getText().toString().equals(""))
                    {
                        batch.setHint("Batch");
                        YoYo.with(Techniques.SlideOutDown).duration(700).repeat(0).playOn(division);


                    }
                }
            }
        });

    }





    public Boolean checkData()
    {
       if (branch.getText().toString().equals(""))
       {
           return false;
       }

        if (batch.getText().toString().equals(""))
        {
            return false;
        }

        if (course.getText().toString().equals(""))
        {
            return false;
        }


        return true;

    }

    public void storeData(ArrayList<scheduleDateAndTime> arrayList,setaddBatchData newData,setaddBatchData oldData)
    {


        String year=newData.year;
        String type=newData.type;
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

        Log.e("The branch string","works "+branchString);

        */
/*Table name*//*

     //this.newtabeName=branch.getText().toString().toUpperCase()+"_"+batch.getText().toString().toUpperCase()+"_"+course.getText().toString().toUpperCase();

        this.newtabeName =branchString+"_"+batchString+"_"+courseString+"_"+year+"_"+type;

*/
/*End*//*



if (intent!=null && intent.getStringExtra("from").toString().equals("main")) {

    */
/*old data *//*



    String obranch="",obatch="",ocourse="";
    String[] oldbranchSplit=oldData.branch.toString().toUpperCase().trim().split("\\s+");
    String[] oldbatchSplit=oldData.batch.toString().toUpperCase().trim().split("\\s+");
    String[] oldcourseSplit=oldData.course.toString().toUpperCase().trim().split("\\s+");

    for (int i=0;i<branchSplit.length;i++)
    {
        obranch+=oldbranchSplit[i];
    }

    for (int i=0;i<batchSplit.length;i++)
    {
        obatch+=oldbatchSplit[i];
    }

    for (int i=0;i<courseSplit.length;i++)
    {
        ocourse+=oldcourseSplit[i];
    }

    String oldTable=obranch+"_"+obatch+"_"+ocourse+"_"+oldData.year+"_"+oldData.type;
        */
/**//*


    Connection connection=new Connection(this);
   Boolean bool= connection.updateRecord(newData,oldData, newtabeName,oldTableName);
    if (bool)
    {
        Snackbar.make(linearLayout,"SuccesFully updated record",BaseTransientBottomBar.LENGTH_SHORT).show();
        Intent intent=new Intent(AddBatch.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
    else if (!bool)
    {
        Snackbar.make(linearLayout,"Unable to update record",BaseTransientBottomBar.LENGTH_SHORT).show();
    }

}
else {
    Connection connection = new Connection(this);
    Boolean bool=connection.addDataToMasterTable(newData);

    Schedule schedule = new Schedule(this, newtabeName +"_SCHEDULE");

    if (arrayList.size() > 0) {
        schedule.addSchedule(arrayList);
    }
    if (bool)
    {
        Snackbar.make(linearLayout,"SuccesFully saved record",BaseTransientBottomBar.LENGTH_SHORT).show();
        Intent intent=new Intent(AddBatch.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
    else if (!bool)
    {
        Snackbar.make(linearLayout,"Unable to save record",BaseTransientBottomBar.LENGTH_SHORT).show();
    }
}
    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
         AlertDialog.Builder builder=new AlertDialog.Builder(AddBatch.this);
        builder.setMessage("Are you sure you want to discard..");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent(AddBatch.this, MainActivity.class);
                startActivity(intent);
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
*/




/*New starts here lets hope it goes well*/

package com.akhil.akhildixit.offlineatten.activities.branchandsection;

import android.Manifest;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.akhil.akhildixit.offlineatten.R;
import com.akhil.akhildixit.offlineatten.activities.otheractivities.MainActivity;
import com.akhil.akhildixit.offlineatten.database.Connection;
import com.akhil.akhildixit.offlineatten.database.Schedule;
import com.akhil.akhildixit.offlineatten.settergetter.scheduleDateAndTime;
import com.akhil.akhildixit.offlineatten.settergetter.setaddBatchData;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.wefika.horizontalpicker.HorizontalPicker;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Akhil Dixit on 8/21/2017.
 */

public class AddBatch extends AppCompatActivity {

    EditText branch,batch,course;
    RadioGroup radioGroup;
    Button addnew;
    LinearLayout linearLayout;
    HorizontalPicker horizontalPicker;
    FloatingActionButton floatingActionButton;
    TextView appbarCross;

    String time;
    String day;
    String newtabeName;
    String oldTableName;
    Intent intent;
    setaddBatchData set;

    ArrayList<setaddBatchData > scheduleData =new ArrayList<>();
    ArrayList<scheduleDateAndTime> setDate=new ArrayList<>();
    ViewGroup insert=null;



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbatch);

        appbarCross=(TextView)findViewById(R.id.addbatch_appbar_cross);

        intent =getIntent();

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);

        branch=(EditText)findViewById(R.id.addbatch_branch);
        batch=(EditText)findViewById(R.id.addbatch_batch);
        course=(EditText)findViewById(R.id.addbatch_course);

        radioGroup=(RadioGroup)findViewById(R.id.addbatch_radiogroup);

        addnew=(Button)findViewById(R.id.addbatch_addnew);

        linearLayout=(LinearLayout)findViewById(R.id.addbatch_addtimetable);

        horizontalPicker=(HorizontalPicker)findViewById(R.id.picker);

        floatingActionButton=(FloatingActionButton)findViewById(R.id.addbatch_savefab);

        fontAwesome();
        animations();

        applyPropertiesToEditextAndRadio();

        if (intent!=null && intent.getStringExtra("from").toString().equals("main"))
        {

            oldTableName=intent.getStringExtra("mytablename");

            TextView bran=(TextView)findViewById(R.id.anim_subjectName);
            TextView batc=(TextView)findViewById(R.id.anim_division);
            TextView cours=(TextView)findViewById(R.id.anim_departName);
            bran.setVisibility(View.VISIBLE);
            batc.setVisibility(View.VISIBLE);
            cours.setVisibility(View.VISIBLE);

            ArrayList<scheduleDateAndTime> st= (ArrayList<scheduleDateAndTime>) intent.getSerializableExtra("timetable");
            set= (setaddBatchData) intent.getSerializableExtra("batch_data");
            Log.e("ddddd","j"+set.course);
            branch.setText(set.branch);
            batch.setText(set.batch);
            course.setText(set.course);

            int id=0;
            switch (set.year)
            {
                case "1st":id=0;break;
                case "2nd":id=1;break;
                case "3rd":id=2;break;
                case "4th":id=3;break;
                case "5th":id=4;break;
                case "6th":id=5;break;
                case "7th":id=6;break;
                case "8th":id=7;break;
                case "9th":id=8;break;
                case "10th":id=9;break;
                case "11th":id=10;break;
                case "12th":id=11;break;

            }
            horizontalPicker.setSelectedItem(id);

            switch (set.type)
            {
                case "Lecture": radioGroup.check(R.id.addbatch_lecture);break;
                case "Practical":radioGroup.check(R.id.addbatch_practical);break;
                case "Seminar":radioGroup.check(R.id.addbatch_seminar);break;
                case "Workshop":radioGroup.check(R.id.addbatch_workshop);break;
                case "Exam":radioGroup.check(R.id.addbatch_exam);break;
            }

            if (st!=null) {

                for (int i=0;i<st.size();i++) {

                    final scheduleDateAndTime sc=st.get(i);
                    LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                    final View view = inflater.inflate(R.layout.scheduletimetable_custom, null);

                    Button time = (Button) view.findViewById(R.id.custom_time);
                    time.setText(sc.time);




                    Button cancel=(Button)view.findViewById(R.id.custom_cancel);


                    Spinner spinner=(Spinner)view.findViewById(R.id.custom_spinner);
                    ArrayList<String> arrayList=new ArrayList();
                    arrayList.add("Monday");arrayList.add("Tuesday");arrayList.add("Wednesday");
                    arrayList.add("Thursday");arrayList.add("Friday");arrayList.add("Saturday");
                    arrayList.add("Sunday");
                    ArrayAdapter arrayAdapter=new ArrayAdapter(AddBatch.this,R.layout.spinnerviewstudent,arrayList);
                    spinner.setAdapter(arrayAdapter);

                    for (int j=0;j<7;j++) {

                        if (spinner.getItemAtPosition(j).equals(sc.day))
                        {spinner.setSelection(j);
                            break;
                        }


                    }



                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            Log.e("No","e");
                            day=parent.getSelectedItem().toString();
                            Log.e("In spiner","w"+parent.getSelectedItem().toString());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            Log.e("No","e");
                        }
                    });





                    insert=(ViewGroup)findViewById(R.id.addbatch_addtimetable);
                    // insert.addView(view,0,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

                    LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    lp.setMargins(0,10,5,0);
                    insert.addView(view,insert.getChildCount()-1,lp);




                    time.setOnClickListener(new View.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onClick(View v) {

                            TimePicker(v);
                        }
                    });

                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            insert.removeView(view);

                        }


                    });
                }
            }
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Boolean isValid=checkData();

                    if(isValid)
                    {
                        retrieveData();

                    }
                    else {
                        RelativeLayout layout=(RelativeLayout)findViewById(R.id.addbatch_relative);
                        Snackbar.make(layout,"Look Like Some Fields Are Empty.. ", BaseTransientBottomBar.LENGTH_SHORT).show();
                    }
                }
            });

        }




        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean isValid=checkData();

                if(isValid)
                {
                    retrieveData();

                }
                else {
                    RelativeLayout layout=(RelativeLayout)findViewById(R.id.addbatch_relative);
                    Snackbar.make(layout,"Look Like Some Fields Are Empty.. ", BaseTransientBottomBar.LENGTH_SHORT).show();
                }
            }
        });


        appbarCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(AddBatch.this);

                builder.setTitle("Are you sure you want to discard..");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent=new Intent(AddBatch.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }

        });


        createSchedule();


    }

    public void applyPropertiesToEditextAndRadio()
    {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {


                RadioButton lec=(RadioButton)findViewById(R.id.addbatch_lecture);
                RadioButton pract=(RadioButton)findViewById(R.id.addbatch_practical);
                RadioButton sem=(RadioButton)findViewById(R.id.addbatch_seminar);
                RadioButton work=(RadioButton)findViewById(R.id.addbatch_workshop);
                RadioButton exam=(RadioButton)findViewById(R.id.addbatch_exam);

                if (!lec.isChecked())
                {
                    lec.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.addbatcheditext)));
                }
                if (!pract.isChecked())
                {
                    pract.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.addbatcheditext)));
                }
                if (!sem.isChecked())
                {
                    sem.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.addbatcheditext)));
                }
                if (!work.isChecked())
                {
                    work.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.addbatcheditext)));
                }
                if (!exam.isChecked())
                {
                    exam.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.addbatcheditext)));
                }

                RadioButton checked;
                Log.e("HHH","GGGG");
                switch (checkedId)
                {
                    case R.id.addbatch_lecture:checked=(RadioButton)findViewById(R.id.addbatch_lecture);
                        checked.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.sunflower)));
                        break;
                    case R.id.addbatch_practical:
                        checked=(RadioButton)findViewById(R.id.addbatch_practical);

                        checked.setButtonTintList(ContextCompat.getColorStateList(AddBatch.this, R.color.sunflower));
                        break;
                    case R.id.addbatch_seminar:
                        checked=(RadioButton)findViewById(R.id.addbatch_seminar);
                        checked.setButtonTintList(ContextCompat.getColorStateList(AddBatch.this, R.color.sunflower));
                        break;
                    case R.id.addbatch_workshop: checked=(RadioButton)findViewById(R.id.addbatch_workshop);
                        checked.setButtonTintList(ContextCompat.getColorStateList(AddBatch.this, R.color.sunflower));
                        break;
                    case R.id.addbatch_exam:
                        checked=(RadioButton)findViewById(R.id.addbatch_exam);
                        checked.setButtonTintList(ContextCompat.getColorStateList(AddBatch.this, R.color.sunflower));
                        break;
                }
            }
        });
    }
    public void retrieveData()
    {
        setaddBatchData set=new setaddBatchData();

        set.branch=branch.getText().toString().trim();
        set.batch= batch.getText().toString().trim();
        set.course=course.getText().toString().trim();
        /*Horizontal picker*/
        int idh= horizontalPicker.getSelectedItem();



        switch (idh)
        {
            case 0:set.year="1st";break;
            case 1:set.year="2nd";break;
            case 2:set.year="3rd";break;
            case 3:set.year="4th";break;
            case 4:set.year="5th";break;
            case 5:set.year="6th";break;
            case 6:set.year="7th";break;
            case 7:set.year="8th";break;
            case 8:set.year="9th";break;
            case 9:set.year="10th";break;
            case 10:set.year="11th";break;
            case 11:set.year="12th";break;

        }
        Log.e("The set type is ","works "+set.year);
        /*end*/
        int id=radioGroup.getCheckedRadioButtonId();

        switch (id)
        {
            case R.id.addbatch_lecture:set.type="Lecture";
                break;


            case R.id.addbatch_seminar:set.type="Seminar";
                break;


            case R.id.addbatch_exam:set.type="Exam";
                break;

            case R.id.addbatch_practical:set.type="Practical";
                break;


            case R.id.addbatch_workshop:set.type="Workshop";
                break;
        }


        if (insert!=null && insert.getChildCount()>0)
        {

            for (int i=0;i<insert.getChildCount();i++)
            {
                scheduleDateAndTime sc=new scheduleDateAndTime();
                View view=insert.getChildAt(i);
                Button btn=(Button)view.findViewById(R.id.custom_time);
                Spinner spinner=(Spinner)view.findViewById(R.id.custom_spinner);


                sc.day=spinner.getSelectedItem().toString();
                sc.time=btn.getText().toString();

                setDate.add(sc);


            }
        }

        storeData(setDate,set,this.set);


    }

    public void createSchedule()
    {

        addnew.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                LayoutInflater inflater= (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                final View view=inflater.inflate(R.layout.scheduletimetable_custom,null);


                Date date=new Date();
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm");
                String dates=simpleDateFormat.format(date);


                Button time=(Button)view.findViewById(R.id.custom_time);
                time.setText(dates);
                Button cancel=(Button)view.findViewById(R.id.custom_cancel);

                final Spinner spinner=(Spinner)view.findViewById(R.id.custom_spinner);


                /**/
                ArrayList<String> arrayList=new ArrayList();
                arrayList.add("Monday");arrayList.add("Tuesday");arrayList.add("Wednesday");
                arrayList.add("Thursday");arrayList.add("Friday");arrayList.add("Saturday");
                arrayList.add("Sunday");
                ArrayAdapter arrayAdapter=new ArrayAdapter(AddBatch.this,R.layout.spinnerviewstudent,arrayList);
                spinner.setAdapter(arrayAdapter);


                /**/


                //  spinner.setAdapter(arrayAdapter);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        Log.e("No","e");
                        day=parent.getSelectedItem().toString();
                        Log.e("In spiner","w"+parent.getSelectedItem().toString());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        Log.e("No","e");
                    }
                });

                insert=(ViewGroup)findViewById(R.id.addbatch_addtimetable);
                // insert.addView(view,0,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

                LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                lp.setMargins(0,10,5,0);
                insert.addView(view,insert.getChildCount()-1,lp);




                time.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(View v) {

                        TimePicker(v);
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        insert.removeView(view);

                    }
                });
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void TimePicker(final View viewBtn)
    {
        Calendar calendar=Calendar.getInstance();
        TimePickerDialog timePickerDialog=new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                Button btn=(Button)viewBtn.findViewById(R.id.custom_time);
                btn.setText(""+hourOfDay+":"+minute);
                time =hourOfDay+":"+minute;
            }
        },calendar.HOUR_OF_DAY,calendar.MINUTE,true);

        timePickerDialog.show();
    }

    public void fontAwesome()
    {
        Typeface fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");

        TextView subjectDetails = (TextView) findViewById(R.id.font_subjectDetails);
        TextView selectType=(TextView)findViewById(R.id.font_selectType);
        TextView cancel=(TextView)findViewById(R.id.addbatch_appbar_cross);

        TextView year = (TextView) findViewById(R.id.font_year);
        TextView clock=(TextView)findViewById(R.id.font_schedule);

        TextView next = (TextView) findViewById(R.id.font_next);
        TextView previous=(TextView)findViewById(R.id.font_previous);

        subjectDetails.setTypeface(fontAwesomeFont);
        selectType.setTypeface(fontAwesomeFont);
        cancel.setTypeface(fontAwesomeFont);

        year.setTypeface(fontAwesomeFont);
        clock.setTypeface(fontAwesomeFont);

        next.setTypeface(fontAwesomeFont);
        previous.setTypeface(fontAwesomeFont);


    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void animations()
    {


        final TextView subject=(TextView)findViewById(R.id.anim_subjectName);

        final TextView depart=(TextView)findViewById(R.id.anim_departName);
        final TextView division=(TextView)findViewById(R.id.anim_division);


        course.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (course.hasFocus())
                {

                    course.getBackground().setColorFilter(getResources().getColor(R.color.sunflower),PorterDuff.Mode.SRC_ATOP);
                    if (course.getText().toString().equals(""))
                    {YoYo.with(Techniques.SlideInUp).duration(700).repeat(0).playOn(subject);
                        subject.setVisibility(View.VISIBLE);

                        Handler handler=new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                course.setHint("");
                            }
                        },400);
                    }

                }
                else {
                    course.getBackground().setColorFilter(getResources().getColor(R.color.addbatcheditext),PorterDuff.Mode.SRC_ATOP);
                    if (course.getText().toString().equals(""))
                    {
                        YoYo.with(Techniques.SlideOutDown).duration(700).repeat(0).playOn(subject);

                        course.setHint("Subject Name");


                    }
                }
            }
        });

        branch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (branch.hasFocus())
                {

                    branch.getBackground().setColorFilter(getResources().getColor(R.color.sunflower),PorterDuff.Mode.SRC_ATOP);
                    if (branch.getText().toString().equals(""))
                    {YoYo.with(Techniques.SlideInUp).duration(700).repeat(0).playOn(depart);
                        depart.setVisibility(View.VISIBLE);
                        Handler handler=new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                branch.setHint("");
                            }
                        },400);

                    }

                }
                else {
                    branch.getBackground().setColorFilter(getResources().getColor(R.color.addbatcheditext),PorterDuff.Mode.SRC_ATOP);
                    if (branch.getText().toString().equals(""))
                    {
                        YoYo.with(Techniques.SlideOutDown).duration(700).repeat(0).playOn(depart);
                        branch.setHint("Branch");


                    }
                }
            }
        });

        batch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (batch.hasFocus())
                {

                    batch.getBackground().setColorFilter(getResources().getColor(R.color.sunflower),PorterDuff.Mode.SRC_ATOP);
                    if (batch.getText().toString().equals(""))
                    {YoYo.with(Techniques.SlideInUp).duration(700).repeat(0).playOn(division);
                        division.setVisibility(View.VISIBLE);
                        Handler handler=new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                batch.setHint("");
                            }
                        },400);

                    }

                }
                else {
                    batch.getBackground().setColorFilter(getResources().getColor(R.color.addbatcheditext),PorterDuff.Mode.SRC_ATOP);
                    if (batch.getText().toString().equals(""))
                    {
                        batch.setHint("Batch");
                        YoYo.with(Techniques.SlideOutDown).duration(700).repeat(0).playOn(division);


                    }
                }
            }
        });

    }





    public Boolean checkData()
    {
        if (branch.getText().toString().trim().equals(""))
        {
            return false;
        }

        if (batch.getText().toString().trim().equals(""))
        {
            return false;
        }

        if (course.getText().toString().trim().equals(""))
        {
            return false;
        }


        return true;

    }

    public void storeData(ArrayList<scheduleDateAndTime> arrayList,setaddBatchData newData,setaddBatchData oldData)
    {


        String year=newData.year;
        String type=newData.type;
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

        Log.e("The branch string","works "+branchString);

        /*Table name*/
        //this.newtabeName=branch.getText().toString().toUpperCase()+"_"+batch.getText().toString().toUpperCase()+"_"+course.getText().toString().toUpperCase();

        this.newtabeName =branchString+"_"+batchString+"_"+courseString+"_"+year+"_"+type;

/*End*/


        if (intent!=null && intent.getStringExtra("from").toString().equals("main")) {

    /*old data */


            String obranch="",obatch="",ocourse="";
            String[] oldbranchSplit=oldData.branch.toString().toUpperCase().trim().split("\\s+");
            String[] oldbatchSplit=oldData.batch.toString().toUpperCase().trim().split("\\s+");
            String[] oldcourseSplit=oldData.course.toString().toUpperCase().trim().split("\\s+");

            for (int i=0;i<branchSplit.length;i++)
            {
                obranch+=oldbranchSplit[i];
            }

            for (int i=0;i<batchSplit.length;i++)
            {
                obatch+=oldbatchSplit[i];
            }

            for (int i=0;i<courseSplit.length;i++)
            {
                ocourse+=oldcourseSplit[i];
            }

            String oldTable=obranch+"_"+obatch+"_"+ocourse+"_"+oldData.year+"_"+oldData.type;
        /**/

            Connection connection=new Connection(this);
            Boolean bool= connection.updateRecord(newData,oldData, newtabeName,oldTableName);
            Schedule schedule=new Schedule(this,newtabeName+"_SCHEDULE");

            if (arrayList.size() > 0) {
                schedule.updateSchedule(arrayList);

            }
            Log.e("The old table name "," "+oldTableName);
            connection.changeAllTableNames(oldTableName,newtabeName);

                       if (bool)
            {
                Snackbar.make(linearLayout,"SuccesFully updated record",BaseTransientBottomBar.LENGTH_SHORT).show();
                Intent intent=new Intent(AddBatch.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
            else if (!bool)
            {
                Snackbar.make(linearLayout,"Unable to update record",BaseTransientBottomBar.LENGTH_SHORT).show();
            }

        }
        else {
            Connection connection = new Connection(this);
            Boolean bool=connection.addDataToMasterTable(newData);

            Schedule schedule = new Schedule(this, newtabeName +"_SCHEDULE");

            if (arrayList.size() > 0) {
                schedule.addSchedule(arrayList);
            }
            if (bool)
            {
                Snackbar.make(linearLayout,"SuccesFully saved record",BaseTransientBottomBar.LENGTH_SHORT).show();
                Intent intent=new Intent(AddBatch.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
            else if (!bool)
            {
                Snackbar.make(linearLayout,"Unable to save record",BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        AlertDialog.Builder builder=new AlertDialog.Builder(AddBatch.this);
        builder.setMessage("Are you sure you want to discard..");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent(AddBatch.this, MainActivity.class);
                startActivity(intent);
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
