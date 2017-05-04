package com.example.imersionultd.seatsuite.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.imersionultd.seatsuite.Classes.Guest;
import com.example.imersionultd.seatsuite.Classes.GuestList;
import com.example.imersionultd.seatsuite.Classes.TableArray;
import com.example.imersionultd.seatsuite.R;
import com.example.imersionultd.seatsuite.Services.ResourceDrawer;

public class SeatGuestsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private GuestList guestList = new GuestList();
    private TextView[] seats;
    private boolean noGuests = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_guests);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        if(!guestList.loadData(this, "guests")|| guestList.isEmpty())
            findViewById(R.id.goBtn).setEnabled(false);


        seats = new TextView[guestList.size()];
        drawSeats();
        //todo setTapListeners(seats);



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.seat_guests, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_guestList) {
            startActivity(new Intent(SeatGuestsActivity.this, GuestListActivity.class));
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void drawSeats(){
        ConstraintLayout parentLayout = (ConstraintLayout) findViewById(R.id.seatGuestLayout);

         //fixme divide by 0 issues

        int seatWidth;
        if (seats.length >= 3)
            seatWidth = ((ImageView)findViewById(R.id.table)).getDrawable().getIntrinsicWidth() /
                    (int) Math.ceil((seats.length - 2)/2.0);
        else
            seatWidth = ((ImageView)findViewById(R.id.table)).getDrawable().getIntrinsicWidth();

        double mid = seats.length / 2.0;
        for (int i = 0; i < seats.length; i++){
            seats[i] = ResourceDrawer.getCircle(this);

            seats[i].setId(100 + i);

            parentLayout.addView(seats[i]);

            ConstraintSet constraints = new ConstraintSet();
            constraints.clone(parentLayout);

            int adjust = (int)Math.ceil(seats.length / 2.0);

            if(i == 0){
                constraints.connect(seats[i].getId(),ConstraintSet.END, R.id.table, ConstraintSet.START, 15);
                constraints.connect(seats[i].getId(), ConstraintSet.TOP, R.id.table, ConstraintSet.TOP, 8);
                constraints.connect(seats[i].getId(), ConstraintSet.BOTTOM, R.id.table, ConstraintSet.BOTTOM, 8);
            }else if(i < mid){ // lower half of table
                constraints.connect(seats[i].getId(),ConstraintSet.START, R.id.table, ConstraintSet.START, (i-1) * seatWidth + (seatWidth / adjust));
                constraints.connect(seats[i].getId(), ConstraintSet.TOP, R.id.table, ConstraintSet.BOTTOM, 8);
            }else if(i > mid){ // upper half of table
                constraints.connect(seats[i].getId(),ConstraintSet.START, R.id.table, ConstraintSet.START, (((int)mid - (int)(i - mid) -1) * seatWidth + (seatWidth / adjust)));
                constraints.connect(seats[i].getId(), ConstraintSet.BOTTOM, R.id.table, ConstraintSet.TOP, 8);
            }else if(i == mid){ //always false when length is odd

                constraints.connect(seats[i].getId(),ConstraintSet.START, R.id.table, ConstraintSet.END, 15);
                constraints.connect(seats[i].getId(), ConstraintSet.TOP, R.id.table, ConstraintSet.TOP, 8);
                constraints.connect(seats[i].getId(), ConstraintSet.BOTTOM, R.id.table, ConstraintSet.BOTTOM, 8);
            }
            constraints.applyTo(parentLayout);
        }
    }

    public void seatGuests(View view) {
        //// TODO: add algorithm
        Spinner algorithmSpinner = (Spinner) findViewById(R.id.chooseAlgoritmSpinner);
        String algorithmName = String.valueOf(algorithmSpinner.getSelectedItem());

        TableArray tableSeats = guestList.advancedSort();

        for (int i = 0; i < guestList.size(); i++) {
            seats[i].setText(tableSeats.get(i).getName());
        }


    }


}
