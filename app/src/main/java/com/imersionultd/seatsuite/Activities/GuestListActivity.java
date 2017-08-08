package com.imersionultd.seatsuite.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.imersionultd.seatsuite.Classes.Guest;
import com.imersionultd.seatsuite.Classes.GuestList;
import com.imersionultd.seatsuite.R;
import com.imersionultd.seatsuite.Services.NavDrawerHelper;

public class GuestListActivity extends AppCompatActivity {

    //todo add nice frame around list,
        //with number of guests on top


    public GuestList guestList = new GuestList();
    public ArrayAdapter<Guest> listAdapterGuest;

    public Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_guest_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Toolbar notepadBar = (Toolbar) findViewById(R.id.listToolBar);
        notepadBar.setTitle("My Guests:");
        notepadBar.inflateMenu (R.menu.guest_list);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        /*
         * Set up listView w/ delete functionality
         */
        if(!guestList.loadData(this,"guests") || guestList.size() == 0){

            addDefualtGuests();

        }



        listAdapterGuest = new ArrayAdapter<>(this, R.layout.list_item_guest, guestList);

        //SwipeMenuList view from API
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int height = displayMetrics.heightPixels;


        SwipeMenuListView listView = (SwipeMenuListView) findViewById(R.id.neighborsList);
        listView.setMenuCreator(createSwipeMenu());
        listView.setAdapter(listAdapterGuest);
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = (int)(height * .65);
        listView.setLayoutParams(params);


        //onClick of individual item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //Toast.makeText(getApplicationContext(), guestList.get(position).toString(),Toast.LENGTH_SHORT).show();

                Bundle bundle = new Bundle();
                bundle.putSerializable("edit", guestList.get(position));
                bundle.putInt("index", position);

                Intent intent = new Intent(GuestListActivity.this, AddGuestActivity.class);

                intent.putExtras(bundle);

                startActivity(intent);
                finish();
            }
        });

        //for onClick of delete button
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                guestList.remove(position);
                guestList.saveData(context, "guests"); //saves changes in memory
                listAdapterGuest.notifyDataSetChanged(); //updates list
                return true; //removes swipeView from screen
            }
        });
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
        //getMenuInflater().inflate(R.menu.guest_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    public void navigationHandler(MenuItem item){
        NavDrawerHelper navHelper = new NavDrawerHelper(this);
        String tag = item.getTitle().toString();
        navHelper.navigationItemSelected(tag);
    }

    public void addGuest(MenuItem item){
        Intent intent = new Intent(GuestListActivity.this, AddGuestActivity.class);
        startActivity(intent);
    }

    public void deleteAll(MenuItem item){
        new AlertDialog.Builder(this)
                .setTitle("Warning")
                .setMessage("Do you really want to delete all guests?")
                .setIcon(R.drawable.ic_warning)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        guestList.clear();
                        guestList.saveData(context, "guests"); //saves changes in memory
                        listAdapterGuest.notifyDataSetChanged(); //updates list
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }

    //from API
    public SwipeMenuCreator createSwipeMenu(){
        return new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(Color.RED));
                deleteItem.setWidth(180);
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
    }

    private void addDefualtGuests(){

        Guest george = new Guest("George", "Washington");
        Guest lincoln = new Guest("Abraham", "Lincoln");
        Guest bill = new Guest("Bill","Clinton");
        Guest hillary= new Guest("Hillary", "Clinton");
        Guest gBush=new Guest("George", "Bush");
        Guest lBush=new Guest("Laura", "Bush");
        Guest trump= new Guest("Donald", "Trump");
        Guest melania= new Guest("Melania", "Trump");
        Guest obama= new Guest("Barak", "Obama");
        Guest mrsbama= new Guest("Michelle", "Obama");

        guestList.add(george);
        guestList.add(lincoln);
        guestList.add(bill);
        guestList.add(hillary);
        guestList.add(gBush);
        guestList.add(lBush);
        guestList.add(trump);
        guestList.add(melania);
        guestList.add(obama);
        guestList.add(mrsbama);

        george.setPreference(lincoln, 8.0);
        george.setPreference(bill, 6.0);
        george.setPreference(hillary, 6.0);
        george.setPreference(gBush, 7.0);
        george.setPreference(lBush, 7.0);
        george.setPreference(trump, 6.0);
        george.setPreference(melania, 6.0);
        george.setPreference(obama, 7.0);
        george.setPreference(mrsbama, 7.0);

        lincoln.setPreference(bill, 7.0);
        lincoln.setPreference(hillary, 7.0);
        lincoln.setPreference(gBush, 6.0);
        lincoln.setPreference(lBush, 6.0);
        lincoln.setPreference(trump, 7.0);
        lincoln.setPreference(melania, 7.0);
        lincoln.setPreference(obama, 6.0);
        lincoln.setPreference(mrsbama, 6.0);

        bill.setPreference(hillary, 10.0);
        bill.setPreference(gBush, 6.0);
        bill.setPreference(lBush, 6.0);
        bill.setPreference(trump, 2.0);
        bill.setPreference(melania, 2.0);
        bill.setPreference(obama, 9.0);
        bill.setPreference(mrsbama, 9.0);

        hillary.setPreference(gBush, 5.0);
        hillary.setPreference(lBush, 6.0);
        hillary.setPreference(trump, 0.0);
        hillary.setPreference(melania, 1.0);
        hillary.setPreference(obama, 9.0);
        hillary.setPreference(mrsbama, 10.0);

        gBush.setPreference(lBush, 10.0);
        gBush.setPreference(trump, 8.0);
        gBush.setPreference(melania, 8.0);
        gBush.setPreference(obama, 5.0);
        gBush.setPreference(mrsbama, 5.0);

        lBush.setPreference(trump, 7.0);
        lBush.setPreference(melania, 8.0);
        lBush.setPreference(obama, 5.0);
        lBush.setPreference(mrsbama, 6.0);

        trump.setPreference(melania, 10.0);
        trump.setPreference(obama, 0.0);
        trump.setPreference(mrsbama, 0.0);

        melania.setPreference(obama, 1.0);
        melania.setPreference(mrsbama, 2.0);

        obama.setPreference(mrsbama, 10.0);


        guestList.saveData(context, "guests");
    }
}