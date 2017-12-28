/*
package com.akhil.akhildixit.offlineatten.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.Toast;

import com.akhil.akhildixit.offlineatten.settergetter.setaddBatchData;

import java.io.File;
import java.util.ArrayList;

*/
/**
 * Created by Akhil Dixit on 8/23/2017.
 *//*


public class Connection extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="offlineAtten2.db";
    public static final int DATABASE_VERSION=1;

    //String[] coloumn1=new String[]{"SERIAL","BRANCH","BATCH","COURSE","TYPE","YEAR","TOTAL_ATTENDANCE"};
    String[] coloumn=new String[]{"BRANCH","BATCH","COURSE","TYPE","YEAR","TOTAL_ATTENDANCE"};
    String tableName="MASTER_TABLE";
    String query_branch_table="CREATE TABLE IF NOT EXISTS MASTER_TABLE(BRANCH TEXT,BATCH TEXT,COURSE TEXT,TYPE TEXT,YEAR TEXT,TOTAL_ATTENDANCE TEXT)";

    public Connection(Context context) {
        super(context, Environment.getExternalStorageDirectory()+ File.separator+"New Attendance"+File.separator+ DATABASE_NAME, null, DATABASE_VERSION);
    }

    public Connection(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(query_branch_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Boolean addDataToMasterTable(setaddBatchData set)
    {
       SQLiteDatabase database=this.getWritableDatabase();


        if (checkIfRecordPresent(set).equals(Boolean.FALSE))
        {
        ContentValues contentValues=new ContentValues();
        contentValues.put(coloumn[0],set.branch);
        contentValues.put(coloumn[1],set.batch);
        contentValues.put(coloumn[2],set.course);
        contentValues.put(coloumn[3],set.type);
        contentValues.put(coloumn[4],set.year);
        contentValues.put(coloumn[5],"");

        long id=database.insert(tableName,null,contentValues);

        if (id>0)
        {
            return true;

        }

    }
    else {
            return false;
        }
        return null;
    }

    public Boolean checkIfRecordPresent(setaddBatchData set)
    {
    SQLiteDatabase database=this.getWritableDatabase();

        Cursor cursor=database.query(tableName,coloumn,coloumn[0]+"=? AND "+coloumn[1]+"=? AND "+coloumn[2]+"=? AND "+coloumn[3]+"=? AND "+coloumn[4]+"=?",
                new String []{set.branch,set.batch,set.course,set.type,set.year},null,null,null);

        if (cursor.getCount()>0)
        {
            return true;
        }
        else return false;
    }

    public ArrayList<setaddBatchData> getRecordFromMasterTable()
    {
        SQLiteDatabase database=this.getWritableDatabase();

        ArrayList<setaddBatchData> arrayList=new ArrayList<>();


        Cursor cursor=database.query(tableName,coloumn,null,null,null,null,null);

        if (cursor.getCount()>0)
        {
            while (cursor.moveToNext())
            {
                setaddBatchData set=new setaddBatchData();

              set.branch= cursor.getString(0);
              set.batch=  cursor.getString(1);
               set.course= cursor.getString(2);
              set.type=  cursor.getString(3);
               set.year= cursor.getString(4);

                Log.e("The year is as foll:","works "+cursor.getString(4)+"type:"+cursor.getString(3));
                arrayList.add(set);

            }
        }

        if (arrayList.size()>0)
        {
            return arrayList;
        }
        else return null;

    }

    public Boolean updateRecord(setaddBatchData newData,setaddBatchData oldData,String newTable,String oldTable)
    {
        SQLiteDatabase database=this.getWritableDatabase();
       // String query="ALTER TABLE "+oldTable+" RENAME TO "+newTable;

        */
/**//*

        String attendanceTable="ALTER TABLE "+oldTable+"_ATTENDANCE"+" RENAME TO "+newTable+"_ATTENDANCE";
        String dates="ALTER TABLE "+oldTable+"_ATTENDANCE_DATES"+" RENAME TO "+newTable+"_ATTENDANCE_DATES";
        String student="ALTER TABLE "+oldTable+"_STUDENT"+" RENAME TO "+newTable+"_STUDENT";
        String studentImages="ALTER TABLE "+oldTable+"_STUDENT_IMAGES"+" RENAME TO "+newTable+"_STUDENT_IMAGES";
        String schedule="ALTER TABLE "+oldTable+"_SCHEDULE"+" RENAME TO "+newTable+"_SCHEDULE";


       */
/* try
        {
            Cursor cursor=database.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name=?",new String[]{oldTable+"_ATTENDANCE"});
            if (cursor.getCount()>0)
            {
               database.execSQL(attendanceTable);
            }

            *//*
*/
/*Student*//*
*/
/*
            Cursor stu=database.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name=?",new String[]{oldTable+"_STUDENT"});
            if (stu.getCount()>0)
            {
                database.execSQL(student);
            }

            Cursor stuI=database.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name=?",new String[]{oldTable+"_STUDENT_IMAGES"});
            if (stuI.getCount()>0)
            {
                database.execSQL(studentImages);
            }
            *//*
*/
/**//*
*/
/*
            *//*
*/
/*Schedule*//*
*/
/*
            Cursor sche=database.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name=?",new String[]{oldTable+"_SCHEDULE"});
            if (sche.getCount()>0)
            {
                database.execSQL(schedule);
            }
            *//*
*/
/**//*

       */
/* }
        catch (Exception e)
        {

          //  Toast.makeText(context,"Looks like there is some problem with your database...",Toast.LENGTH_LONG).show();
            Log.e("Exceptionincheckifexist","Not wwork"+e);
        }
        try
        {
            Cursor cursor=database.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name=?",new String[]{oldTable+"_ATTENDANCE_DATES"});
            if (cursor.getCount()>0)
            {
                database.execSQL(dates);
            }
        }
        catch (Exception e)
        {

            //  Toast.makeText(context,"Looks like there is some problem with your database...",Toast.LENGTH_LONG).show();
            Log.e("Exceptionincheckifexist","Not wwork"+e);
        }
*//*

        */
/**//*

        //database.execSQL(query);



        ContentValues contentValues=new ContentValues();

        contentValues.put(coloumn[0],newData.branch);
        contentValues.put(coloumn[1],newData.batch);
        contentValues.put(coloumn[2],newData.course);
        contentValues.put(coloumn[3],newData.type);
        contentValues.put(coloumn[4],newData.year);
        contentValues.put(coloumn[5],"");

       long id= database.update(tableName,contentValues,coloumn[0]+"=? AND "+coloumn[1]+"=? AND "+coloumn[2]+"=? AND "+coloumn[3]+"=? AND "+coloumn[4]+"=? ",
                new String []{oldData.branch,oldData.batch,oldData.course,oldData.type,oldData.year});





        if (id>0)
        {
            Log.e("Succes update","master table");
            return true;


        }
        else return false;
    }

    public void deleteRecord(setaddBatchData oldData)
    {
        SQLiteDatabase database=this.getWritableDatabase();

        long id =database.delete(tableName,coloumn[0]+"=? AND "+coloumn[1]+"=? AND "+coloumn[2]+"=? AND "+coloumn[3]+"=? AND "+coloumn[4]+"=? ",
                new String []{oldData.branch,oldData.batch,oldData.course,oldData.type,oldData.year});

        if (id>0)
        {
            Log.e("Success delete,,","works");
        }

    }
}
*/



/*New data*/


package com.akhil.akhildixit.offlineatten.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.Toast;

import com.akhil.akhildixit.offlineatten.settergetter.setaddBatchData;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Akhil Dixit on 8/23/2017.
 */

public class Connection extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="markup.db";
    public static final int DATABASE_VERSION=1;

    //String[] coloumn1=new String[]{"SERIAL","BRANCH","BATCH","COURSE","TYPE","YEAR","TOTAL_ATTENDANCE"};
    String[] coloumn=new String[]{"BRANCH","BATCH","COURSE","TYPE","YEAR","TOTAL_ATTENDANCE"};
    String tableName="MASTER_TABLE";
    String query_branch_table="CREATE TABLE IF NOT EXISTS MASTER_TABLE(BRANCH TEXT,BATCH TEXT,COURSE TEXT,TYPE TEXT,YEAR TEXT,TOTAL_ATTENDANCE TEXT)";

    public Connection(Context context) {
        super(context, Environment.getExternalStorageDirectory()+ File.separator+"Mark-Up"+File.separator+ DATABASE_NAME, null, DATABASE_VERSION);
    }

    public Connection(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(query_branch_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Boolean addDataToMasterTable(setaddBatchData set)
    {
        SQLiteDatabase database=this.getWritableDatabase();


        if (checkIfRecordPresent(set).equals(Boolean.FALSE))
        {
            ContentValues contentValues=new ContentValues();
            contentValues.put(coloumn[0],set.branch);
            contentValues.put(coloumn[1],set.batch);
            contentValues.put(coloumn[2],set.course);
            contentValues.put(coloumn[3],set.type);
            contentValues.put(coloumn[4],set.year);
            contentValues.put(coloumn[5],"");

            long id=database.insert(tableName,null,contentValues);

            if (id>0)
            {
                return true;

            }

        }
        else {
            return false;
        }
        return null;
    }

    public Boolean checkIfRecordPresent(setaddBatchData set)
    {
        SQLiteDatabase database=this.getWritableDatabase();

        Cursor cursor=database.query(tableName,coloumn,coloumn[0]+"=? AND "+coloumn[1]+"=? AND "+coloumn[2]+"=? AND "+coloumn[3]+"=? AND "+coloumn[4]+"=?",
                new String []{set.branch,set.batch,set.course,set.type,set.year},null,null,null);

        if (cursor.getCount()>0)
        {
            return true;
        }
        else return false;
    }

    public ArrayList<setaddBatchData> getRecordFromMasterTable()
    {
        SQLiteDatabase database=this.getWritableDatabase();

        ArrayList<setaddBatchData> arrayList=new ArrayList<>();


        Cursor cursor=database.query(tableName,coloumn,null,null,null,null,null);

        if (cursor.getCount()>0)
        {
            while (cursor.moveToNext())
            {
                setaddBatchData set=new setaddBatchData();

                set.branch= cursor.getString(0);
                set.batch=  cursor.getString(1);
                set.course= cursor.getString(2);
                set.type=  cursor.getString(3);
                set.year= cursor.getString(4);

                Log.e("The year is as foll:","works "+cursor.getString(4)+"type:"+cursor.getString(3));
                arrayList.add(set);

            }
        }

        if (arrayList.size()>0)
        {
            return arrayList;
        }
        else return null;

    }



    public void changeAllTableNames(String oldTable,String newTable)
    {

        Log.e("In change names","Up");
        SQLiteDatabase database=this.getWritableDatabase();
        String StudentTable="ALTER TABLE "+oldTable+"_STUDENT RENAME TO "+newTable+"_STUDENT";
        String Attendancetable="ALTER TABLE "+oldTable+"_STUDENT_ATTENDANCE RENAME TO "+newTable+"_STUDENT_ATTENDANCE";
        String AttendanceDatesTable="ALTER TABLE "+oldTable+"_STUDENT_ATTENDANCE_DATES RENAME TO "+newTable+"_STUDENT_ATTENDANCE_DATES";
        String StudentImages="ALTER TABLE "+oldTable+"_STUDENT_IMAGES RENAME TO "+newTable+"_STUDENT_IMAGES";


        /*checking if table exist*/
/*

        Cursor student=database.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name=?",new String[]{oldTable+"_STUDENT"});
        Cursor attendance=database.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name=?",new String[]{oldTable+"_STUDENT_ATTENDANCE"});
        Cursor attendancedates=database.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name=?",new String[]{oldTable+"_STUDENT_ATTENDANCE_DATES"});
        Cursor studentImage=database.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name=?",new String[]{oldTable+"_STUDENT_IMAGES"});

*/

        try{
            Cursor stu=database.query(oldTable+"_STUDENT",new String[]{"ROLLNUMBER"},null,null,null,null,null);
            Cursor att=database.query(oldTable+"_STUDENT_ATTENDANCE",new String[]{"ROLLNUMBER"},null,null,null,null,null);
            Cursor dat=database.query(oldTable+"_STUDENT_ATTENDANCE_DATES",new String[]{"DATE"},null,null,null,null,null);
            Cursor imag=database.query(oldTable+"_STUDENT_IMAGES",new String[]{"ROLLNUMBER"},null,null,null,null,null);

            if (stu.getCount()>0){
                database.execSQL(StudentTable);
                Log.e("In change names","student");
            }
            if (att.getCount()>0){
                database.execSQL(Attendancetable);
            }
            if (dat.getCount()>0){
                database.execSQL(AttendanceDatesTable);
            }
            if (imag.getCount()>0){
                database.execSQL(StudentImages);
            }

        }
        catch (RuntimeException e)
        {
            Log.e("Some error","Not works");
        }
        catch (Exception e)
        {
            Log.e("Error","no");
        }



        /**/

    }



    public Boolean updateRecord(setaddBatchData newData,setaddBatchData oldData,String newTable,String oldTable)
    {
        SQLiteDatabase database=this.getWritableDatabase();
        // String query="ALTER TABLE "+oldTable+" RENAME TO "+newTable;

        /**/
        String attendanceTable="ALTER TABLE "+oldTable+"_ATTENDANCE"+" RENAME TO "+newTable+"_ATTENDANCE";
        String dates="ALTER TABLE "+oldTable+"_ATTENDANCE_DATES"+" RENAME TO "+newTable+"_ATTENDANCE_DATES";
        String student="ALTER TABLE "+oldTable+"_STUDENT"+" RENAME TO "+newTable+"_STUDENT";
        String studentImages="ALTER TABLE "+oldTable+"_STUDENT_IMAGES"+" RENAME TO "+newTable+"_STUDENT_IMAGES";
        String schedule="ALTER TABLE "+oldTable+"_SCHEDULE"+" RENAME TO "+newTable+"_SCHEDULE";


       /* try
        {
            Cursor cursor=database.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name=?",new String[]{oldTable+"_ATTENDANCE"});
            if (cursor.getCount()>0)
            {
               database.execSQL(attendanceTable);
            }

            *//*Student*//*
            Cursor stu=database.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name=?",new String[]{oldTable+"_STUDENT"});
            if (stu.getCount()>0)
            {
                database.execSQL(student);
            }

            Cursor stuI=database.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name=?",new String[]{oldTable+"_STUDENT_IMAGES"});
            if (stuI.getCount()>0)
            {
                database.execSQL(studentImages);
            }
            *//**//*
            *//*Schedule*//*
            Cursor sche=database.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name=?",new String[]{oldTable+"_SCHEDULE"});
            if (sche.getCount()>0)
            {
                database.execSQL(schedule);
            }
            *//**/
       /* }
        catch (Exception e)
        {

          //  Toast.makeText(context,"Looks like there is some problem with your database...",Toast.LENGTH_LONG).show();
            Log.e("Exceptionincheckifexist","Not wwork"+e);
        }
        try
        {
            Cursor cursor=database.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name=?",new String[]{oldTable+"_ATTENDANCE_DATES"});
            if (cursor.getCount()>0)
            {
                database.execSQL(dates);
            }
        }
        catch (Exception e)
        {

            //  Toast.makeText(context,"Looks like there is some problem with your database...",Toast.LENGTH_LONG).show();
            Log.e("Exceptionincheckifexist","Not wwork"+e);
        }
*/
        /**/
        //database.execSQL(query);



        ContentValues contentValues=new ContentValues();

        contentValues.put(coloumn[0],newData.branch);
        contentValues.put(coloumn[1],newData.batch);
        contentValues.put(coloumn[2],newData.course);
        contentValues.put(coloumn[3],newData.type);
        contentValues.put(coloumn[4],newData.year);
        contentValues.put(coloumn[5],"");

        long id= database.update(tableName,contentValues,coloumn[0]+"=? AND "+coloumn[1]+"=? AND "+coloumn[2]+"=? AND "+coloumn[3]+"=? AND "+coloumn[4]+"=? ",
                new String []{oldData.branch,oldData.batch,oldData.course,oldData.type,oldData.year});





        if (id>0)
        {
            Log.e("Succes update","master table");
            return true;


        }
        else return false;
    }

    public void deleteRecord(setaddBatchData oldData)
    {
        SQLiteDatabase database=this.getWritableDatabase();

        long id =database.delete(tableName,coloumn[0]+"=? AND "+coloumn[1]+"=? AND "+coloumn[2]+"=? AND "+coloumn[3]+"=? AND "+coloumn[4]+"=? ",
                new String []{oldData.branch,oldData.batch,oldData.course,oldData.type,oldData.year});

        if (id>0)
        {
            Log.e("Success delete,,","works");
        }

    }
}
