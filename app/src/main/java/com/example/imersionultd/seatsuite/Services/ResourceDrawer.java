package com.example.imersionultd.seatsuite.Services;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by YonahKarp on 5/3/17.
 */

public class ResourceDrawer {


    public static TextView getCircle(Context context) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(100, 100);

        TextView textView = new TextView(context);
        //ImageView image = new ImageView(context);
        //image.setLayoutParams (layoutParams);
        ShapeDrawable badge = getDkGrayCircle();
        //image.setImageDrawable(badge);

        textView.setBackground(badge);
        textView.setText("-");
        textView.setTextColor(Color.WHITE);
        textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);


        return textView;
    }

    public static ShapeDrawable getDkGrayCircle() {
        ShapeDrawable dkGrayCircle = new ShapeDrawable(new OvalShape());
        dkGrayCircle.setIntrinsicWidth(100);
        dkGrayCircle.setIntrinsicHeight(100);
        dkGrayCircle.getPaint().setColor(Color.DKGRAY);


        return dkGrayCircle;
    }

    public static ShapeDrawable getLtGrayCircle() {
        ShapeDrawable ltGrayCircle = new ShapeDrawable(new OvalShape());
        ltGrayCircle.setIntrinsicWidth(100);
        ltGrayCircle.setIntrinsicHeight(100);
        ltGrayCircle.getPaint().setColor(Color.LTGRAY);

        return ltGrayCircle;
    }
}
