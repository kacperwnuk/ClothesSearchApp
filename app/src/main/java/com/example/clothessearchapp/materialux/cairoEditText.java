package com.example.clothessearchapp.materialux;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.res.ResourcesCompat;

import com.example.clothessearchapp.R;


/**
 * Created by Mohammmed Alsudani on 26-Jan-19.
 * for more visit http://materialuiux.com
 */
public class cairoEditText extends AppCompatEditText
{


    public cairoEditText(Context context)
    {
        super(context);
        init();
    }

    public cairoEditText(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public cairoEditText(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init()
    {
        if (!isInEditMode())
        {
//            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "res/font/cairo_regular.ttf");
            Typeface tf = ResourcesCompat.getFont(getContext(), R.font.cairo_regular);
            setTypeface(tf);
        }
    }

}
