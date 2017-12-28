package com.akhil.akhildixit.offlineatten.exceltosqlconvert;

/**
 * Created by Akhil Dixit on 8/14/2017.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.akhil.akhildixit.offlineatten.database.Connection;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyu on 2015-9-8
 */
public class SQLiteToExcelMe {

    private static Handler handler = new Handler(Looper.getMainLooper());

    private Context mContext;
    private SQLiteDatabase database;
    private String mDbName;
    private String mExportPath;
    private HSSFWorkbook workbook;
    SQLiteOpenHelper openHelper;

    public SQLiteToExcelMe(Context context, String dbName) {
        this(context, dbName, Environment.getExternalStorageDirectory().toString() + File.separator);
    }

    public SQLiteToExcelMe(Context context, String dbName, String exportPath) {

        openHelper=new Connection(context);
        mContext = context;
        mDbName = dbName;
        mExportPath = exportPath;
        Log.e("export path is ","export path :"+mExportPath);
        try {
            database=openHelper.getWritableDatabase();
            //database = SQLiteDatabase.openOrCreateDatabase(mContext.getDatabasePath(mDbName).getAbsolutePath(), null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<String> getAllTables() {
        ArrayList<String> tables = new ArrayList<>();
        Cursor cursor = database.rawQuery("select name from sqlite_master where type='table' order by name", null);
        while (cursor.moveToNext()) {
            tables.add(cursor.getString(0));
        }
        cursor.close();
        return tables;
    }

    private ArrayList<String> getColumns(String table) {
        ArrayList<String> columns = new ArrayList<>();
        Cursor cursor = database.rawQuery("PRAGMA table_info(" + table + ")", null);
        while (cursor.moveToNext()) {
            columns.add(cursor.getString(1));
        }
        cursor.close();
        return columns;
    }

    private void exportTables(List<String> tables, final String fileName) throws Exception {
        workbook = new HSSFWorkbook();
        for (int i = 0; i < tables.size(); i++) {
            if (!tables.get(i).equals("android_metadata")) {
                HSSFSheet sheet = workbook.createSheet(tables.get(i));
                createSheet(tables.get(i), sheet);

                Log.e("in export table","export : "+tables.get(i));
            }
        }
        File file = new File(mExportPath, fileName);
        if (!file.exists())
        {
            file.getParentFile().mkdirs();
            Log.e("in file creation","works");

        }
        FileOutputStream fos = new FileOutputStream(file);
        workbook.write(fos);
        fos.flush();
        fos.close();
        workbook.close();
        database.close();
    }

    public void exportSingleTable(final String table, final String fileName, ExportListener listener) {
        List<String> tables = new ArrayList<>();
        tables.add(table);
        startExportTables(tables, fileName, listener);
    }

    public void exportSpecificTables(final ArrayList<String> tables, String fileName, ExportListener listener) {
        startExportTables(tables, fileName, listener);
    }

    public void exportAllTables(final String fileName, ExportListener listener) {
        ArrayList<String> tables = getAllTables();
        startExportTables(tables, fileName, listener);
    }

    public void startExportTables(final List<String> tables, final String fileName, final ExportListener listener) {
        if (listener != null) {
            listener.onStart();
        }
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    exportTables(tables, fileName);
                    if (listener != null) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                listener.onCompleted(mExportPath + fileName);
                            }
                        });
                    }
                } catch (final Exception e) {
                    if (database != null && database.isOpen()) {



                        /*Changes start*/
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext,"Sorry file not found",Toast.LENGTH_LONG).show();
                                Log.e("in some exception ","exception is : "+e);
                            }
                        });
                        /*Changes end*/

                        database.close();
                    }
                    if (listener != null)
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                listener.onError(e);
                            }
                        });
                }
            }
        }).start();
    }

    private void createSheet(String table, HSSFSheet sheet) {
        HSSFRow rowA = sheet.createRow(0);
        ArrayList<String> columns = getColumns(table);
        for (int i = 0; i < columns.size(); i++) {
            HSSFCell cellA = rowA.createCell(i);
            cellA.setCellValue(new HSSFRichTextString("" + columns.get(i)));
        }
        insertItemToSheet(table, sheet, columns);
    }

    private void insertItemToSheet(String table, HSSFSheet sheet, ArrayList<String> columns) {
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        Cursor cursor = database.rawQuery("select * from " + table, null);
        cursor.moveToFirst();
        int n = 1;
        while (!cursor.isAfterLast()) {
            HSSFRow rowA = sheet.createRow(n);
            for (int j = 0; j < columns.size(); j++) {
                HSSFCell cellA = rowA.createCell(j);
                if (cursor.getType(j) == Cursor.FIELD_TYPE_BLOB) {
                    HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0, (short) j, n, (short) (j + 1), n + 1);
                    anchor.setAnchorType(3);
                    patriarch.createPicture(anchor, workbook.addPicture(cursor.getBlob(j), HSSFWorkbook.PICTURE_TYPE_JPEG));
                } else {
                    cellA.setCellValue(new HSSFRichTextString(cursor.getString(j)));
                }
            }
            n++;
            cursor.moveToNext();
        }
        cursor.close();
    }

    public interface ExportListener {
        void onStart();

        void onCompleted(String filePath);

        void onError(Exception e);
    }

}
