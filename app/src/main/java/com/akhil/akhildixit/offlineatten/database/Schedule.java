/*
package com.akhil.akhildixit.offlineatten.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.akhil.akhildixit.offlineatten.settergetter.scheduleDateAndTime;

import java.util.ArrayList;

*/
/**
 * Created by Akhil Dixit on 8/23/2017.
 *//*


public class Schedule {

    SQLiteDatabase database;
    SQLiteOpenHelper openHelper;
    String tableName;
    String[] coloumn=new String[]{"DAY","TIME"};

    public Schedule(Context context,String table)
    {
        openHelper=new Connection(context);
        this.tableName=table;

        database=openHelper.getWritableDatabase();

        createTable();
    }

    public void createTable()
    {
        String sql="CREATE TABLE IF NOT EXISTS "+this.tableName+"(DAY TEXT,TIME TEXT)";
        database.execSQL(sql);
    }

    public void addSchedule(ArrayList<scheduleDateAndTime> arrayList)
    {

        for (int i=0;i<arrayList.size();i++)
        {
            ContentValues contentValues=new ContentValues();
            contentValues.put(coloumn[0],arrayList.get(i).day);
            contentValues.put(coloumn[1],arrayList.get(i).time);

            long id= database.insert(this.tableName,null,contentValues);
            if (id>0)
            {
                Log.e("Schedule submit","works");
            }
        }
    }


    public ArrayList<scheduleDateAndTime> getSchedule()
    {
        Cursor cursor=database.query(tableName,coloumn,null,null,null,null,null);
        if (cursor.getCount()>0)
        {
            ArrayList<scheduleDateAndTime> arrayList=new ArrayList<>();
            while (cursor.moveToNext())
            {
                scheduleDateAndTime st=new scheduleDateAndTime();
                st.day=cursor.getString(0);
                st.time=cursor.getString(1);

                arrayList.add(st);

            }
            return arrayList;
        }
        else
            return null;
    }
}
*/


package com.akhil.akhildixit.offlineatten.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.akhil.akhildixit.offlineatten.settergetter.scheduleDateAndTime;

import java.util.ArrayList;

/**
 * Created by Akhil Dixit on 8/23/2017.
 */

public class Schedule {

    SQLiteDatabase database;
    SQLiteOpenHelper openHelper;
    String tableName;
    String[] coloumn=new String[]{"DAY","TIME"};

    public Schedule(Context context,String table)
    {
        openHelper=new Connection(context);
        this.tableName=table;

        database=openHelper.getWritableDatabase();

        createTable();
    }

    public void createTable()
    {
        String sql="CREATE TABLE IF NOT EXISTS "+this.tableName+"(DAY TEXT,TIME TEXT)";
        database.execSQL(sql);
    }

    public void addSchedule(ArrayList<scheduleDateAndTime> arrayList)
    {

        for (int i=0;i<arrayList.size();i++)
        {
            ContentValues contentValues=new ContentValues();
            contentValues.put(coloumn[0],arrayList.get(i).day);
            contentValues.put(coloumn[1],arrayList.get(i).time);

            long id= database.insert(this.tableName,null,contentValues);
            if (id>0)
            {
                Log.e("Schedule submit","works");
            }
        }
    }

   public void updateSchedule(ArrayList<scheduleDateAndTime> arrayList)
   {
       String query="DROP TABLE IF EXISTS "+tableName;
       database.execSQL(query);
       String makeNewTable="CREATE TABLE IF NOT EXISTS "+this.tableName+"(DAY TEXT,TIME TEXT)";
       database.execSQL(makeNewTable);

       addSchedule(arrayList);
   }

    public ArrayList<scheduleDateAndTime> getSchedule()
    {
        Cursor cursor=database.query(tableName,coloumn,null,null,null,null,null);
        if (cursor.getCount()>0)
        {
            ArrayList<scheduleDateAndTime> arrayList=new ArrayList<>();
            while (cursor.moveToNext())
            {
                scheduleDateAndTime st=new scheduleDateAndTime();
                st.day=cursor.getString(0);
                st.time=cursor.getString(1);

                arrayList.add(st);

            }
            return arrayList;
        }
        else
            return null;
    }
}
