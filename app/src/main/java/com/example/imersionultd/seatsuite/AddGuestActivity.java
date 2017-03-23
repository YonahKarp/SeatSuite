package com.example.imersionultd.seatsuite;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    private boolean isEdit = false;

    private Guest currGuest = new Guest("",0, true);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_guest);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        EditText name = ((EditText) findViewById(R.id.nameTxt));
        EditText age = ((EditText) findViewById(R.id.ageTxt));

        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus)
                    hideKeyboard(view);
            }
        });

        age.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus)
                    hideKeyboard(view);
            }
        });

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            isEdit = true;

            currGuest = (Guest) bundle.getSerializable("edit");


            Switch gender = ((Switch) findViewById(R.id.genderSwitch));

            name.setText(currGuest.getName());
            age.setText(currGuest.getAge() + "");
            gender.setChecked(!currGuest.isMale());

            Button doneButton = ((Button) findViewById(R.id.doneButton));
            doneButton.setText("Done");

        }

        this.setTitle("Edit Guest");
        //getSupportActionBar().setTitle("Edit Guest");  //provide compatibility to all the versions


        guestList.loadData(this,"guests");

        ArrayAdapter<Guest> listAdapterGuest = new ArrayAdapter<>(this, R.layout.list_item_guest, guestList);

        ListView listView = (ListView) findViewById(R.id.guestList);
        //listView.setAdapter(listAdapterGuest);
        listView.setAdapter(new PreferenceListAdapter(this, guestList, currGuest));


    }

    public void goToGuestList(View v){

        EditText  name = ((EditText) findViewById(R.id.nameTxt));
        EditText age = ((EditText) findViewById(R.id.ageTxt));
        boolean gender = ((Switch) findViewById(R.id.genderSwitch)).isChecked();


        boolean shouldReturn = false;
        if( name.getText().toString().length() == 0 ) {
            name.setError("Please input a name for this guest");
            shouldReturn = true;
        }
        try{
            Integer.parseInt(age.getText().toString());
        }catch (Exception e){
            shouldReturn = true;
            age.setError("please input an age");
        }

        if(shouldReturn)
            return;



        if (isEdit){
            Bundle bundle = getIntent().getExtras();
            int index = bundle.getInt("index");

            currGuest.setName(name.getText().toString());
            currGuest.setAge(Integer.parseInt(age.getText().toString()));
            currGuest.setMale(!gender);

            guestList.set(index, currGuest);

        }else {
            Guest newGuest = new Guest( //todo don't make new guest
                    name.getText().toString(),
                    Integer.parseInt(age.getText().toString()),
                    !gender);
            guestList.add(newGuest);
        }



        guestList.saveData(this, "guests");

        Intent intent = new Intent(AddGuestActivity.this, GuestListActivity.class);

        startActivity(intent);
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
