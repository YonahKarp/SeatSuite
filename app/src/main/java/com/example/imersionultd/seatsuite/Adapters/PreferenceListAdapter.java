package com.example.imersionultd.seatsuite.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.imersionultd.seatsuite.Classes.Guest;
import com.example.imersionultd.seatsuite.Classes.GuestList;
import com.example.imersionultd.seatsuite.R;
import com.example.imersionultd.seatsuite.Services.AssetLoader;

import java.util.Iterator;

/**
 * Created by YonahKarp on 3/14/17.
 */

public class PreferenceListAdapter extends BaseAdapter {
    private GuestList list;
    private Context context;
    private Guest currGuest;
    private Typeface custom_font;
    private int hiddenIndex;

    private static LayoutInflater inflater = null;

    public PreferenceListAdapter(Context context, GuestList list, Guest currGuest, int guestIndex){
        this.context = context;
        this.list = list;
        this.hiddenIndex = guestIndex;
        this.currGuest = currGuest;

        custom_font = AssetLoader.getInstance().getFont(context);


        inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public int getCount() {
        // -1 because we want to ignore current guest
        return list.size() - 1;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class Holder
    {
        TextView nameTxt;
        TextView progressTxt;
        SeekBar seekBar;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        //skip hidden guest
        if(i >= hiddenIndex)
            i += 1;

        final Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.preference_list_item, null);

        holder.nameTxt=(TextView) rowView.findViewById(R.id.nameTxt);
        holder.progressTxt = (TextView) rowView.findViewById(R.id.progressTxt);
        holder.seekBar=(SeekBar) rowView.findViewById(R.id.setPreferenceSeekbar);



        String[] fullName = list.get(i).getName().split(" ");
        String name = fullName[0];

        if (fullName.length > 1)
            name += " " + fullName[fullName.length-1].substring(0,1);

        if (name.length() > 9)
            name = name.substring(0, 5) + ".." + name.charAt(name.length() - 1);

        holder.nameTxt.setText(name + "");

        int tmp = (int)currGuest.getPreference(list.get(i));
        holder.seekBar.setProgress(tmp);
        holder.progressTxt.setText(getEmote(tmp));

        holder.progressTxt.setTypeface(custom_font);

        final int iTmp = i;

        holder.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            //int progress = tmp;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                holder.progressTxt.setText(getEmote(progressValue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                hideKeyboard(seekBar);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int curr10s = 0;
                int other10s = 0;
                Guest otherGuest = list.get(iTmp);

                if(seekBar.getProgress() == 10) //don't allow more than 2 10s
                    if(currGuest.getPreference(otherGuest) != 10) {
                        for (double d : currGuest.preferenceList.values())
                            if (d == 10.0)
                                curr10s++;

                        for (double d : otherGuest.preferenceList.values())
                            if (d == 10.0)
                                other10s++;
                    }

                if (curr10s >= 2) {
                    show10Alert(currGuest.getName());
                    seekBar.setProgress((int)currGuest.getPreference(otherGuest));
                }else if(other10s >= 2){
                    show10Alert(otherGuest.getName());
                    seekBar.setProgress((int)currGuest.getPreference(otherGuest));
                }
                else
                    currGuest.setPreference(list.get(iTmp),(double)seekBar.getProgress());
            }
        });

        return rowView;
    }

    private void show10Alert(String name){
        new AlertDialog.Builder(context)
                .setTitle("Could Not Set")
                .setMessage(name + " already has 2 other guests he/she must sit next to")
                .setIcon(R.drawable.ic_warning)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {}
                }).show();
    }

    private String getEmote(int val){
        switch (val){
            case 0:
                return "\uD83D\uDEAB"; //block
            case 1:
                return "\uD83D\uDE12"; //sad
            case 2:
                return "\uD83D\uDE1F";
            case 3:
                return "☹";
            case 4:
                return "\uD83D\uDE15";
            case 5:
                return "\uD83D\uDE10";
            case 6:
                return "\uD83D\uDE42";
            case 7:
                return "☺";
            case 8:
                return "\uD83D\uDE0A";
            case 9:
                return "\uD83D\uDE03"; //happy
            case 10:
                return "⭐️"; //invisible star char
            default:
                return "?";

        }
    }public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}



