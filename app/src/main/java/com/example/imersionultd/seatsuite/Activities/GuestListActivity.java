package com.example.imersionultd.seatsuite.Activities;

import android.content.Context;
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

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.imersionultd.seatsuite.Classes.Guest;
import com.example.imersionultd.seatsuite.Classes.GuestList;
import com.example.imersionultd.seatsuite.R;

public class GuestListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //todo add nice frame around list,
        //with number of guests on top


    public GuestList guestList = new GuestList();
    public ArrayAdapter<Guest> listAdapterGuest;

    public Context context = this;

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



        /*
         * Set up listView w/ delete functionality
         */
        if(!guestList.loadData(this,"guests") || guestList.size() == 0){
            guestList.add(new Guest("Reuven", 20, true));
            guestList.add(new Guest("Shimon", 25, true));
            guestList.add(new Guest("Levi", 31, true));
            guestList.add(new Guest("Sara", 23, false));
            guestList.add(new Guest("Rivka", 28, false));
            guestList.add(new Guest("Rachel", 37, false));

            guestList.saveData(context, "guests");
        }



        listAdapterGuest = new ArrayAdapter<>(this, R.layout.list_item_guest, guestList);

        //SwipeMenuList view from API
        SwipeMenuListView listView = (SwipeMenuListView) findViewById(R.id.neighborsList);
        listView.setMenuCreator(createSwipeMenu());
        listView.setAdapter(listAdapterGuest);


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
        getMenuInflater().inflate(R.menu.guest_list, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_seat) {
            startActivity(new Intent(GuestListActivity.this, SeatGuestsActivity.class));
            finish(); //
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void addGuest(MenuItem item){

        Intent intent = new Intent(GuestListActivity.this, AddGuestActivity.class);

        startActivity(intent);
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
}