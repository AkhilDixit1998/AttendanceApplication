package com.akhil.akhildixit.offlineatten.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.akhil.akhildixit.offlineatten.activities.attendance.takeAttendance;
import com.akhil.akhildixit.offlineatten.settergetter.PieChartData;
import com.akhil.akhildixit.offlineatten.settergetter.Student;


import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Akhil Dixit on 8/14/2017.
 */

public class StoreAttendance {


    SQLiteOpenHelper openHelper;
    SQLiteDatabase database;
    Context context;
    String coloumn[]={"ROLLNUMBER","NAME","DATE","STATUS"};

    //new thing so that spinner does not have duplicate dates
    Boolean isDateAlreadyPresent=false;

    public StoreAttendance(Context context)
    {
        this.context=context;
        openHelper=new Connection(context);
        database=openHelper.getWritableDatabase();
    }



    public void storeAttendance(String table, ArrayList<Student> arrayList)
    {

        String query="CREATE TABLE IF NOT EXISTS "+table+"(ROLLNUMBER TEXT ,NAME TEXT,DATE TEXT ,STATUS TEXT)";
        String dateQuery="CREATE TABLE IF NOT EXISTS "+table+"_DATES (DATE TEXT)";
        database.execSQL(dateQuery);
        database.execSQL(query);


        ContentValues contentValues=new ContentValues();


        String date=arrayList.get(0).date;
        for(int i=0;i<arrayList.size();i++)
        {
            Student student=arrayList.get(i);
            contentValues.put("ROLLNUMBER",student.rollnumber);
            contentValues.put("NAME",student.name);
            contentValues.put("DATE",student.date);
            contentValues.put("STATUS",student.status);

            database.insert(table,null,contentValues);
            Log.e("In store Attendance ","Hello : ");
        }

        Cursor cursor = database.query(table, coloumn, null, null, null, null, null);
        if (!isDateAlreadyPresent) {
            ContentValues cv = new ContentValues();
            cv.put("DATE", date);

            database.insert(table + "_DATES", null, cv);
            // Log.e("in getting stored atte",""+cursor.getString(0));

        }
        if (cursor.getCount() > 0) {
            takeAttendance.hasSaved = true;
            Toast.makeText(context, "Succesfully saved data : ", Toast.LENGTH_LONG).show();
        }
    }
    public ArrayList<String > getAllDates(String table)
    {
        ArrayList<String> arrayList=new ArrayList<>();
        Cursor cursor=database.query(table,new String[]{"DATE"},null,null,null,null,null,null );
        if (cursor.getCount()>0)
        {
            while (cursor.moveToNext())
            {
                arrayList.add(cursor.getString(0));
            }
            return arrayList;
        }
        else return null;


    }

    public Boolean checkIfAttendanceExistForView(String table)
    {
        Cursor cursor=database.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name=?",new String[]{table});
        if (cursor.getCount()>0)
        {
            return true;
        }
        else return false;
    }
    public ArrayList<Student> getAllAttendance(String date,String table)
    {
        ArrayList<Student> arrayList=new ArrayList<>();

        Cursor cursor=database.query(table,coloumn,"DATE=? ",new String[]{date},null,null,null);
        if (cursor.getCount()>0)
        {
            while (cursor.moveToNext())
            {
                Student student=new Student(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
                arrayList.add(student);
            }
            return arrayList;
        }
        else return null;
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void checkIfAttendanceHasAlreadyStored(final String table, final ArrayList<Student> arrayList)
    {
        String dates="";
        Date date=new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MMM dd MM, yyyy");
        dates=simpleDateFormat.format(date);

        try {

            Cursor cursor=database.query(table,coloumn,"DATE=?",new String[]{dates},null,null,null);
            if (cursor.getCount()>0)
            {
                AlertDialog.Builder builder=new AlertDialog.Builder(this.context);

                builder.setMessage("A record of this date already exist.. Do you want to replace it??");
                builder.setCancelable(true);

                builder.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                //  storeAttendance(table,arrayList);
                                storeAttendanceIfAlreadyExist(table,arrayList);
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
            else {
                storeAttendance(table,arrayList);
            }
        }
        finally {


        }



    }

    public Boolean checkIfTableAlreadyExists(String tableName)
    {

        try
        {
            Cursor cursor=database.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name=?",new String[]{tableName});
            if (cursor.getCount()>0)
            {
                return true;
            }
            else return false;
        }
        catch (Exception e)
        {

            Toast.makeText(context,"Looks like there is some problem with your database...",Toast.LENGTH_LONG).show();
            Log.e("Exceptionincheckifexist","Not wwork"+e);
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void storeAttendanceIfAlreadyExist(String tableName, ArrayList<Student> arrayList)
    {
        String dates="";
        Date date=new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MMM dd MM, yyyy");
        dates=simpleDateFormat.format(date);
        database.delete(tableName,"DATE=?",new String[]{dates});

        isDateAlreadyPresent=true;

        storeAttendance(tableName,arrayList);
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    public int generateAttendanceReport(String table)
    {
        String dates="";
        Date date=new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MMM dd MM, yyyy");
        dates=simpleDateFormat.format(date);
        Cursor cursor=database.query(table,coloumn,"DATE=? AND STATUS=?",new String[]{dates,"P"},null,null,null);

        if (cursor.getCount()>0)
        {
            int count=cursor.getCount();
            return count;
        }
        else return 0;
    }

    public PieChartData getPresentAttendance(String roll,String name,String table)
    {

        Cursor cursor1;
        Boolean bool=false;
        try{
            cursor1=database.query(table,new String[]{"ROLLNUMBER"},null,null,null,null,null);
            if (cursor1.getCount()>0)
            {
                bool=true;
            }
        }
        catch (RuntimeException e)
        {
            Log.e("Some error occured","not works");
        }
        int attendance=0;
        Log.e("In getPres","above");
        if (bool)
        {
            Log.e("In getPres","w");
        Cursor cursor=database.query(table,new String[]{"ROLLNUMBER,NAME,STATUS"}," ROLLNUMBER=? AND NAME=? ",new String[]{roll,name},null,null,null);

        if (cursor.getCount()>0)
        {
            PieChartData pie=new PieChartData();
            while (cursor.moveToNext())
            {
                ++pie.totalAtten;

                if (cursor.getString(2).equals("P"))
                {
                    pie.presentAtten++;
                }

            }
            Log.e("The tte is ","w"+attendance);
            return pie;
        }
        else
            return null;
        }
        Log.e("In getPres","below");
        return null;
    }


}
