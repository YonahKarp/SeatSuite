package com.example.imersionultd.seatsuite;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class GuestListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //public ArrayList<String> fruits = new ArrayList<>();
    //public ArrayAdapter<String> listAdapter;

    public GuestList guestList = new GuestList();
    public ArrayAdapter<Guest> listAdapterGuest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //getVal from previous view;
        //String newVal = getIntent().getStringExtra("newValue");

        Bundle guestsBundle = getIntent().getBundleExtra("guests");
        GuestList guests = null;
        if(guestsBundle != null)
            guests = ((GuestListWrapper) guestsBundle.getSerializable("guests")).list;




        /**
         * Set up listView w/ delete functionality
         */
        //if(fruits.isEmpty())
            //fruits.addAll(Arrays.asList("Apple", "Banana", "Cherry", "Kiwi", "Lemon", "Pear"));

        if(guests == null) {
            guestList.add(new Guest("Reuven", 20, true));
            guestList.add(new Guest("Shimon", 25, true));
            guestList.add(new Guest("Sara", 23, false));
            guestList.add(new Guest("Levi", 31, true));
            guestList.add(new Guest("Rivka", 28, false));
        }else {
            guestList = guests;
        }



        //if(newVal != null)
            //fruits.add(newVal);

        //listAdapter = new ArrayAdapter<String>(this, R.layout.list_item_guest,fruits);
        listAdapterGuest = new ArrayAdapter<>(this, R.layout.list_item_guest, guestList);

        //SwipeMenuList view from API
        SwipeMenuListView listView = (SwipeMenuListView) findViewById(R.id.guestList);
        listView.setMenuCreator(createSwipeMenu());
        listView.setAdapter(listAdapterGuest);


        //on click of individual item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getApplicationContext(), guestList.get(position).toString(),Toast.LENGTH_SHORT).show();
            }
        });

        //for onClick of delete button
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                guestList.remove(position);
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
        getMenuInflater().inflate(R.menu.guest_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        /*noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
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

    public void addGuest(MenuItem item){
        //Toast.makeText(this, "add Guest", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(GuestListActivity.this, AddGuestActivity.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("guests", new GuestListWrapper(guestList));

        intent.putExtra("guests",bundle);

        startActivity(intent);
    }

    //from API
    public SwipeMenuCreator createSwipeMenu(){
        return new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
                deleteItem.setWidth(180);
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
    }
}