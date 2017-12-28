package com.akhil.akhildixit.offlineatten.activities.attendance.quickatten;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import com.akhil.akhildixit.offlineatten.R;

/**
 * Created by Akhil Dixit on 8/29/2017.
 */

public class TakeQuickAttendance extends AppCompatActivity{

    GridView gridView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quickattendance);
        gridView=(GridView)findViewById(R.id.quickattengrid);

        gridView.setAdapter(new Custom(this));
    }

}
