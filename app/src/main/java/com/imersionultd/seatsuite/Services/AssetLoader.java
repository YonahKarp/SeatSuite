package com.imersionultd.seatsuite.Services;

import android.content.Context;
import android.graphics.Typeface;

import com.imersionultd.seatsuite.Classes.GuestList;

/**
 * Created by YonahKarp on 5/4/17.
 */

public class AssetLoader {

    public static GuestList guestList = null;

    private  static AssetLoader instance;
    private static Typeface custom_font = null;

    private AssetLoader() {
    }

    public static AssetLoader getInstance() {
        if (instance == null){
            instance = new AssetLoader();
        }
        return instance;
    }

    public Typeface getFont(Context context) {
        if (custom_font == null) {
            custom_font = Typeface.createFromAsset(context.getApplicationContext().getAssets(), "fonts/AppleEmoji.ttf");;
        }
        return custom_font;
    }


}
