package com.example.clothessearchapp.materialux;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.res.ResourcesCompat;

import com.example.clothessearchapp.R;



/**
 * Created by Mohammmed Alsudani on 26-Jan-19.
 * for more visit http://materialuiux.com
 */
public class cairoButton  extends AppCompatButton
{

    public cairoButton(Context context)
    {
        super(context);
        init();
    }

    public cairoButton(Context context, AttributeSet attrs)
    {
        this(context, attrs, R.attr.borderlessButtonStyle);
        init();
    }

    public cairoButton(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init()
    {
        if (!isInEditMode())
        {
            setTextSize(18);
            Typeface tf = ResourcesCompat.getFont(getContext(), R.font.cairo_regular);
            setTypeface(tf);
        }
    }
}
