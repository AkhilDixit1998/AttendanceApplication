package com.akhil.akhildixit.offlineatten.exceltosqlconvert;

/**
 * Created by Akhil Dixit on 8/14/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;


import com.akhil.akhildixit.offlineatten.database.Connection;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelToSQLiteMe {

    private static Handler handler = new Handler(Looper.getMainLooper());

    private Context mContext;
    private SQLiteDatabase database;
    private String mDbName;
    SQLiteOpenHelper openHelper;

    public ExcelToSQLiteMe(Context context, String dbName) {
        mContext = context;
        mDbName = dbName;
        openHelper= new Connection(context);
        try {
            database=openHelper.getWritableDatabase();
            //database = SQLiteDatabase.openOrCreateDatabase(mContext.getDatabasePath(mDbName).getAbsolutePath(), null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void importFromAsset(final String assetFileName, final ImportListener listener) {
        if (listener != null) {
            listener.onStart();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    working(mContext.getAssets().open(assetFileName));
                    if (listener != null) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                listener.onCompleted(mDbName);
                            }
                        });
                    }
                } catch (final Exception e) {
                    if (database != null && database.isOpen()) {
                        database.close();
                    }
                    if (listener != null) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                listener.onError(e);
                            }
                        });
                    }
                }
            }
        }).start();
    }

    public void importFromFile(String filePath, ImportListener listener) {


        Log.e("In import from file:","works");
        importFromFile(new File(filePath), listener);

    }

    private void importFromFile(final File file, final ImportListener listener) {
        if (listener != null) {
            listener.onStart();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.e("in run","works");
                    working(new FileInputStream(file));
                    if (listener != null) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Log.e("in new thread run ","work");
                                listener.onCompleted(mDbName);
                            }
                        });
                    }
                } catch (final Exception e) {
                    Log.e("exception is ","the excepion as : "+e);
                    if (database != null && database.isOpen()) {
                        database.close();
                    }
                    if (listener != null) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                listener.onError(e);
                            }
                        });
                    }
                }
            }
        }).start();

    }

    private void working(InputStream stream) throws Exception {

        Log.e("in sheet creation up : ","work");
       // HSSFWorkbook workbook = new HSSFWorkbook(stream);


        Workbook workbook= WorkbookFactory.create(stream);

        Log.e("in sheet creation : ","work");
        int sheetNumber = workbook.getNumberOfSheets();
        Log.e("Sheet number","Number is : "+sheetNumber);
        for (int i = 0; i < sheetNumber; i++) {
            Log.e("in sheet creation : ","work");
            createTable(workbook.getSheetAt(i));
        }


        database.close();
    }

    private void createTable(Sheet sheet) {
        StringBuilder createTableSql = new StringBuilder("CREATE TABLE IF NOT EXISTS ");
        createTableSql.append(sheet.getSheetName());
        createTableSql.append("(");
        Iterator<Row> rit = sheet.rowIterator();
        Log.e("in create table ","the iterator is : "+rit);
        Row rowHeader = rit.next();
        Log.e("in create table ","the row header is : "+rowHeader);
        List<String> columns = new ArrayList<>();
        for (int i = 0; i < rowHeader.getPhysicalNumberOfCells(); i++) {
            createTableSql.append(rowHeader.getCell(i).getStringCellValue());
            if (i == rowHeader.getPhysicalNumberOfCells() - 1) {
                createTableSql.append(" TEXT");
            } else {
                createTableSql.append(" TEXT,");
            }
            columns.add(rowHeader.getCell(i).getStringCellValue());
        }
        createTableSql.append(")");

        database.execSQL(createTableSql.toString());

        while (rit.hasNext()) {

            Log.e("in rit.next","works");
            Row row = rit.next();
            ContentValues values = new ContentValues();
            for (int n = 0; n < row.getPhysicalNumberOfCells()-1; n++) {
                if (row.getCell(n).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    values.put(columns.get(n), row.getCell(n).getNumericCellValue());
                } else {
                    values.put(columns.get(n), row.getCell(n).getStringCellValue());
                }
            }
            long result = database.insert(sheet.getSheetName(), null, values);
            if (result < 0) {
                throw new RuntimeException("insert value failed!");
            }
        }
    }

    public interface ImportListener {
        void onStart();

        void onCompleted(String dbName);

        void onError(Exception e);
    }

}
