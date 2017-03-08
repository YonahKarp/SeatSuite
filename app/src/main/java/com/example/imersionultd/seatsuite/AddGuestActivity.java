package com.example.imersionultd.seatsuite;

import android.content.Context;
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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class AddGuestActivity extends AppCompatActivity {

    private GuestList guestList = new GuestList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_guest);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        guestList.loadData(this,"guests");

        ArrayAdapter<Guest> listAdapterGuest = new ArrayAdapter<>(this, R.layout.list_item_guest, guestList);

        ListView listView = (ListView) findViewById(R.id.guestList);
        listView.setAdapter(listAdapterGuest);

    }

    public void goToGuestList(View v){

        String  name = ((EditText) findViewById(R.id.nameTxt)).getText().toString();
        int age = Integer.parseInt(((EditText) findViewById(R.id.ageTxt)).getText().toString());
        boolean gender = ((Switch) findViewById(R.id.genderSwitch)).isChecked();

        guestList.add(new Guest(name,age,gender));

        guestList.saveData(this, "guests");

        Intent intent = new Intent(AddGuestActivity.this, GuestListActivity.class);

        startActivity(intent);
    }

}
