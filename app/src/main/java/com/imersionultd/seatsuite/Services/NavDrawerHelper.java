package com.imersionultd.seatsuite.Services;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;

import com.imersionultd.seatsuite.Activities.About;
import com.imersionultd.seatsuite.Activities.GuestListActivity;
import com.imersionultd.seatsuite.Activities.HelpActivity;
import com.imersionultd.seatsuite.Activities.SeatGuestsActivity;
import com.imersionultd.seatsuite.R;

public class NavDrawerHelper {

    private AppCompatActivity activity;

    public NavDrawerHelper(AppCompatActivity activity){
        this.activity = activity;
    }

    public boolean navigationItemSelected(String itemName) {
        // Handle navigation view item clicks here.

        switch (itemName) {
            case "Seat Guests":
                if (activity instanceof  SeatGuestsActivity)
                    break;
                activity.startActivity(new Intent(activity, SeatGuestsActivity.class));
                activity.finish();
                break;
            case "Guest List":
                if (activity instanceof  GuestListActivity)
                    break;
                activity.startActivity(new Intent(activity, GuestListActivity.class));
                activity.finish();
                break;
            case "About":
                if (activity instanceof  About)
                    break;
                activity.startActivity(new Intent(activity, About.class));
                activity.finish();
                break;
            case "Help":
                if (activity instanceof HelpActivity)
                    break;
                activity.startActivity(new Intent(activity, HelpActivity.class));
                activity.finish();
                break;
            case "Share":
                String shareBody = "Get the Seat Suite app: https://play.google.com/store/apps/details?id=com.imersionultd.seatsuite";

                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Seat Suite (Open in Google Play Store to Download the Application)");

                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                activity.startActivity(Intent.createChooser(sharingIntent, "Share the Seat Suite app via:"));
                break;
            default:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
