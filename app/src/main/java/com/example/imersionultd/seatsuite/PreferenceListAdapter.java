package com.example.imersionultd.seatsuite;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by YonahKarp on 3/14/17.
 */

public class PreferenceListAdapter extends BaseAdapter {
    GuestList list;
    Context context;
    Guest currGuest;
    Typeface custom_font;



    private static LayoutInflater inflater = null;

    public PreferenceListAdapter(Context context, GuestList list, Guest currGuest){
        this.context = context;
        this.list = list;
        this.currGuest = currGuest;

        custom_font = Typeface.createFromAsset(context.getApplicationContext().getAssets(), "fonts/AppleEmoji.ttf");


        inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public int getCount() {
        return list.size();
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
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.preference_list_item, null);

        holder.nameTxt=(TextView) rowView.findViewById(R.id.nameTxt);
        holder.progressTxt = (TextView) rowView.findViewById(R.id.progressTxt);
        holder.seekBar=(SeekBar) rowView.findViewById(R.id.setPreferenceSeekbar);


        holder.nameTxt.setText(list.get(i).getName());

        int tmp = currGuest.getPreference(list.get(i));
        holder.seekBar.setProgress(tmp);
        holder.progressTxt.setText(getEmote(tmp));

        holder.progressTxt.setTypeface(custom_font);

        holder.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            //int progress = tmp;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                holder.progressTxt.setText(getEmote(progressValue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                currGuest.setPreference(list.get(i),seekBar.getProgress());
            }
        });


        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "You Clicked "+list.get(i), Toast.LENGTH_SHORT).show();
//                String txt = (String) holder.progressTxt.getText();
//
//                //must sit
//                try {
//                    Integer.parseInt(txt);
//                    holder.seekBar.setProgress(10);
//                    holder.progressTxt.setText("\uD83D\uDD17");//link symbol
//                    return;
//                }catch (Exception e){}
//
//                //can't sit
//                if (txt == "\uD83D\uDD17"){
//                    holder.seekBar.setProgress(0);
//                    holder.progressTxt.setText("\uD83D\uDEAB");//no symbol 0
//                }
//                else {
//                    holder.seekBar.setProgress(5);
//                    holder.progressTxt.setText("5");//no symbol 0
//                }


            }
        });

        return rowView;
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
    }

}



