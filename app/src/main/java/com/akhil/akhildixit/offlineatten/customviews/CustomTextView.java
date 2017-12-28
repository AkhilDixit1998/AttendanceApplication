package com.akhil.akhildixit.offlineatten.customviews;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Akhil Dixit on 9/2/2017.
 */

public class CustomTextView extends android.support.v7.widget.AppCompatTextView {
    public CustomTextView(Context context) {
        super(context);
        init(null);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public void init(AttributeSet attrs)
    {

        if (attrs!=null)
        {
            Typeface fontAwesomeFont = Typeface.createFromAsset(getContext().getAssets(), "walkway.ttf");
            setTypeface(fontAwesomeFont);

        }
    }
}
