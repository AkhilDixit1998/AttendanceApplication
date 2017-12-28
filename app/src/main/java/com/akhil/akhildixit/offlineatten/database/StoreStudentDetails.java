package com.akhil.akhildixit.offlineatten.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import com.akhil.akhildixit.offlineatten.settergetter.StudentEntry;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by Akhil Dixit on 8/23/2017.
 */

public class StoreStudentDetails {

    SQLiteDatabase database;
    SQLiteOpenHelper openHelper;
    String table;
    String tableImage;
Context context;
    String[] coloumn=new String[]{"ROLLNUMBER","NAME"};

    public StoreStudentDetails(Context context, String tableName) {
        openHelper = new Connection(context);
        database = openHelper.getWritableDatabase();
        this.context=context;
        this.table=tableName;
        this.tableImage=tableName+"_IMAGES";

        createStudentInfoTable();
        createImageStorageTable();
    }

    public void StoreImage(StudentEntry st, Bitmap bitmap)
    {

        byte array[]=getBitmapAsByteArray(bitmap);
        ContentValues contentValues=new ContentValues();
        contentValues.put("IMAGE",array);
        contentValues.put(coloumn[0],st.rollnumber);
        contentValues.put(coloumn[1],st.name);
        long id=database.insert(tableImage,null,contentValues);
       // long id=database.insert(tableImage,contentValues,coloumn[0]+"=? AND "+coloumn[1]+"=?",new String[]{st.rollnumber,st.name});
        if (id>0)
        {
            Log.e("Success update","works");

        }
        else {
            Log.e("Unsuccess","works");

        }
    }

    public Boolean deleteRecord(String rollNumber,String name)
    {
        long id=database.delete(table,coloumn[0]+"=? AND "+coloumn[1]+"=? ",new String[]{rollNumber,name});
        if (id>0)
        {
            return true;
        }
        else return false;
    }
    public Boolean updateImage(String rollnumber,Bitmap bitmap,String name)
    {

        byte array[]=getBitmapAsByteArray(bitmap);
        ContentValues contentValues=new ContentValues();
        contentValues.put("IMAGE",array);
        long id=database.update(tableImage,contentValues,coloumn[0]+"=? AND "+coloumn[1]+"=?",new String[]{rollnumber,name});
        if (id>0)
        {
            Log.e("Success update","works");
            return true;

        }
        else {
            Log.e("Unsuccess","works");
            return false;

        }
    }
    public Bitmap getImage(String rollnumber,String name)
    {

        Log.e("In table",tableImage);

        Cursor cursor=database.query(tableImage,new String[]{"IMAGE"},"ROLLNUMBER=? AND NAME=? ",new String[]{rollnumber,name},null,null,null);
        if (cursor!=null)
        {

            Log.e("Before first","works");
            if (cursor.moveToFirst())
            {
                byte[] arr=cursor.getBlob(cursor.getColumnIndex("IMAGE"));
                cursor.close();
                return BitmapFactory.decodeByteArray(arr,0,arr.length);


            }
        }
        else {
            return null;
        }
        return null;
    }

    public byte[] getBitmapAsByteArray(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,0,byteArrayOutputStream);

        return byteArrayOutputStream.toByteArray();
    }
    public Boolean updateRecord(String rollnew,String namenew,String rollold,String nameold)
    {
     ContentValues contentValues=new ContentValues();
        contentValues.put(coloumn[0],rollnew);
        contentValues.put(coloumn[1],namenew);
        long id=database.update(table,contentValues,coloumn[0]+"=? AND "+coloumn[1]+"=?",new String[]{rollold,nameold});
       long idImages= database.update(tableImage,contentValues,coloumn[0]+"=? AND "+coloumn[1]+"=?",new String[]{rollold,nameold});
        if ((id>0 && idImages>0))
        {
            Log.d("id is ",""+id);
            Log.d("idimages is ",""+idImages);
            Log.e("Success update","works");
            return true;
        }
        else {
            Log.e("Unsuccess","works");
            return false;
        }
    }
    public void createImageStorageTable()
    {
        String sql="CREATE TABLE IF NOT EXISTS "+this.tableImage+"(ROLLNUMBER TEXT,NAME TEXT,IMAGE BLOB)";
        database.execSQL(sql);
    }
    public void createStudentInfoTable()
    {
        String sql="CREATE TABLE IF NOT EXISTS "+this.table+"(ROLLNUMBER TEXT,NAME TEXT,ATTENDANCE TEXT)";
        database.execSQL(sql);
    }

    public Boolean checkIftableExist()
    {
        Cursor cursor=database.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name=?",new String[]{table});
        if (cursor.getCount()>0)
        {
            return true;
        }
        else return false;
    }


    public Boolean insertData(StudentEntry st)
    {

        ContentValues contentValues=new ContentValues();
        contentValues.put("ROLLNUMBER",st.rollnumber);
        contentValues.put("NAME",st.name);
        Log.e("In bool insert","works"+st.name);
        long id= database.insert(table,null,contentValues);
        if (id==-1)
        {

            Toast.makeText(context,"Sorry student could not be added..",Toast.LENGTH_LONG).show();
            return false;
        }
        else if(id>0)
        {
            Toast.makeText(context,"Successfully added student : "+st.name+"...",Toast.LENGTH_LONG).show();
            return true;
        }
        return null;
    }

    public ArrayList<StudentEntry> getData()
    {
        Cursor cursor=database.query(table,coloumn,null,null,null,null,null);
        ArrayList<StudentEntry> student=new ArrayList<>();
        if (cursor.getCount()>0)
        {

            while (cursor.moveToNext())
            {
                StudentEntry st=new StudentEntry();
                st.rollnumber=cursor.getString(0);
                st.name=cursor.getString(1);
                Log.e("The 0 string roll","works "+cursor.getString(1));
                student.add(st);
            }
            return student;
        }
        else return null;
    }



/*New */

    public ArrayList<String> getRollNumbers()
    {
        ArrayList<String> arrayList=new ArrayList<>();
        String data="";
        Cursor cursor=database.query(table,new String[]{"ROLLNUMBER","NAME"},null,null,null,null,null);
        if(cursor.getCount()>0)
        {
            while(cursor.moveToNext())
            {
                data=cursor.getString(0);

                arrayList.add(data);
                Log.e("The getAllStudents","works "+data+" in cursor is "+cursor.getString(0)+" "+cursor.getString(1));
            }

        }


        return arrayList;
    }
    public ArrayList<Bitmap> getAllImages()
    {

        Log.e("In table",tableImage);

        ArrayList<Bitmap> arrayList=new ArrayList<>();

        Cursor cursor=database.query(tableImage,new String[]{"IMAGE"},null,null,null,null,null);
        if (cursor!=null)
        {

            Log.e("Before first","works"+cursor.getCount());
            while (cursor.moveToNext())
            {
                byte[] arr=cursor.getBlob(cursor.getColumnIndex("IMAGE"));
                arrayList.add( BitmapFactory.decodeByteArray(arr,0,arr.length));


            }
            Log.e("In store st all image","Size is:: "+arrayList.size());
            return arrayList;
        }
        else {
            return null;
        }

    }

    public ArrayList<String> getNames()
    {
        ArrayList<String> arrayList=new ArrayList<>();
        String data="";
        Cursor cursor=database.query(table,new String[]{"ROLLNUMBER","NAME"},null,null,null,null,null);
        if(cursor.getCount()>0)
        {
            while(cursor.moveToNext())
            {
                data=cursor.getString(1);

                arrayList.add(data);
                Log.e("The getAllStudents","works "+data+" in cursor is "+cursor.getString(0)+" "+cursor.getString(1));
            }

        }


        return arrayList;
    }


    public Boolean isAnyStudentAdded()
    {
       Cursor cursor=database.query(table,new String[]{"ROLLNUMBER"},null,null,null,null,null);

        if (cursor.getCount()>0)
        {
            return true;
        }
        else return false;

    }

}

