package com.example.imersionultd.seatsuite;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

public class AddGuestActivity extends AppCompatActivity {

    private GuestList guestList = new GuestList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_guest);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        Bundle guestsBundle = getIntent().getBundleExtra("guests");
        GuestList guests = null;
        if(guestsBundle != null)
            guests = ((GuestListWrapper) guestsBundle.getSerializable("guests")).list;

        if(guests != null)
            guestList = guests;

        ArrayAdapter<Guest> listAdapterGuest = new ArrayAdapter<>(this, R.layout.list_item_guest, guestList);

        //SwipeMenuList view from API
        ListView listView = (ListView) findViewById(R.id.guestList);
        listView.setAdapter(listAdapterGuest);



        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    public void goToGuestList(View v){

        Bundle guestsBundle = getIntent().getBundleExtra("guests");
        GuestList guests = ((GuestListWrapper) guestsBundle.getSerializable("guests")).list;

        String  name = ((EditText) findViewById(R.id.nameTxt)).getText().toString();
        int age = Integer.parseInt(((EditText) findViewById(R.id.ageTxt)).getText().toString());
        boolean gender = ((Switch) findViewById(R.id.genderSwitch)).isChecked();

        guests.add(new Guest(name,age,gender));




        Intent intent = new Intent(AddGuestActivity.this, GuestListActivity.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("guests", new GuestListWrapper(guests));

        intent.putExtra("guests",bundle);

        //intent.putExtra("newValue", ((EditText) findViewById(R.id.nameTxt)).getText().toString());

        startActivity(intent);
    }

}
