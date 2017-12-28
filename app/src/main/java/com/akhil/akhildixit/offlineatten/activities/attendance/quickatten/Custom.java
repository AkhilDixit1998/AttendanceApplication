package com.akhil.akhildixit.offlineatten.activities.attendance.quickatten;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.akhil.akhildixit.offlineatten.R;

/**
 * Created by Akhil Dixit on 8/29/2017.
 */

public class Custom extends BaseAdapter {

    Context context;

    public Custom(Context context)
    {
        this.context=context;
    }
    @Override
    public int getCount() {
        return 9;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView=new ImageView(context);

        if (convertView == null) {
            imageView.setLayoutParams(new GridView.LayoutParams(50, 50));/*This is to increase the height and width of each use
                this to scale images etc*/
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(R.drawable.clock);/*to change the starting icon*/
        return imageView;

    }
}
