package com.example.imersionultd.seatsuite.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.location.Location;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imersionultd.seatsuite.Classes.Guest;
import com.example.imersionultd.seatsuite.Classes.GuestList;
import com.example.imersionultd.seatsuite.Classes.TableArray;
import com.example.imersionultd.seatsuite.R;
import com.example.imersionultd.seatsuite.Services.ResourceDrawer;

import java.util.ArrayList;
import java.util.Collection;

public class SeatGuestsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TableArray tableSeats;
    private GuestList guestList = new GuestList();
    private TextView[] seats;
    private boolean noGuests = false;
    private boolean guestsSeated = false;



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


        tableSeats = new TableArray(guestList.size());
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
            finish();
        }  else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

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

            addOnClickForSeat(i);
        }
    }

    public void addOnClickForSeat(final int i){
        seats[i].setOnClickListener(new View.OnClickListener() {

            int[] location = new int[2];

            @Override
            public void onClick(View view) {
                if(!guestsSeated) {
                    seats[i].getLocationInWindow(location);


                    seats[i].setBackground(ResourceDrawer.getLtGrayCircle());
                    showPopup(SeatGuestsActivity.this, location, i);
                }
                else
                    Toast.makeText(SeatGuestsActivity.this, "score = " + tableSeats.getScore(i), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void seatGuests(View view) {
        Spinner algorithmSpinner = (Spinner) findViewById(R.id.chooseAlgoritmSpinner);
        String algorithmName = String.valueOf(algorithmSpinner.getSelectedItem());



        //if (algorithmName.equals("Basic Seating"))
            //tableSeats = guestList.basicSeatingSort();
        //else
        tableSeats = guestList.advancedSort(tableSeats);
        guestsSeated = true;

        int totalScore = 0;

        for (int i = 0; i < guestList.size(); i++) {
            seats[i].setText(tableSeats.get(i).getName());

            int score = tableSeats.getScore(i);
            totalScore += score;
            //System.out.println(score);

            ((ShapeDrawable)seats[i].getBackground()).getPaint().setColor(Color.rgb((int)(255 - 11.25*score), (int)11.25*score, 0));
        }

        TextView scoreTxt = (TextView) findViewById(R.id.totalScoreTxt);
        double avgHappiness = totalScore /(2.0*guestList.size());
        scoreTxt.setText("Score: " + avgHappiness);




    }


    public void showPopup(Context context, int[] location, final int index){
        int width = 450;
        int height = 300;

        // Inflate popup layout
        LinearLayout viewGroup = (LinearLayout) findViewById(R.id.popupView);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.neighbors_popup, viewGroup);

        ListView listView = (ListView) layout.findViewById(R.id.neighborsList);
        ListAdapter listAdapter = new ArrayAdapter<>(this, R.layout.list_item_guest, guestList);
        listView.setAdapter(listAdapter);


        layout.setBackgroundColor(Color.WHITE);

        // Creating the PopupWindow
        final PopupWindow popup = new PopupWindow(context);
        popup.setContentView(layout);
        popup.setWidth(width);
        popup.setHeight(height);
        popup.setFocusable(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Guest guest = guestList.get(position);

                //if guest is seated elsewhere, move him
                if (guest.isSeated()) {
                    int i = tableSeats.getIndexOf(guest);
                    seats[i].setText("-");
                    tableSeats.setNull(i);
                }

                //remove whatever guest was here before
                if (!seats[index].getText().equals("-"))
                    guestList.get(seats[index].getText()+"").setIsSeated(false);

                seats[index].setText(guest.getName());
                guest.setIsSeated(true);

                tableSeats.set(index, guest);
                popup.dismiss();

            }
        });

        popup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

                seats[index].setBackground(ResourceDrawer.getDkGrayCircle());
            }
        });



        // Some offset to align the popup a bit to the right, and a bit down, relative to button's position.
        int OFFSET_X = 30;
        int OFFSET_Y = 30;


        popup.showAtLocation(layout, Gravity.NO_GRAVITY, location[0] + OFFSET_X, location[1] + OFFSET_Y);

    }

}
