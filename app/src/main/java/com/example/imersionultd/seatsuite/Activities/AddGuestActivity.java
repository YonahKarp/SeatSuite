package com.example.imersionultd.seatsuite.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;

import com.example.imersionultd.seatsuite.Classes.Guest;
import com.example.imersionultd.seatsuite.Classes.GuestList;
import com.example.imersionultd.seatsuite.Adapters.PreferenceListAdapter;
import com.example.imersionultd.seatsuite.R;

public class AddGuestActivity extends AppCompatActivity {

    private GuestList guestList = new GuestList();
    private boolean isEdit = false;

    private Guest currGuest = new Guest("",30, true);

    private PreferenceListAdapter preferenceListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_guest);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.setTitle("Edit Guest");

        final EditText name = ((EditText) findViewById(R.id.nameTxt));
        final EditText age = ((EditText) findViewById(R.id.ageTxt));
        Switch gender = ((Switch) findViewById(R.id.genderSwitch));

        guestList.loadData(this,"guests");

        Bundle bundle = getIntent().getExtras();

        if(bundle != null){
            isEdit = true;
            int index = bundle.getInt("index");

            currGuest = (Guest) bundle.getSerializable("edit");

            name.setText(currGuest.getName());
            age.setText(currGuest.getAge() + "");
            gender.setChecked(!currGuest.isMale());

            Button doneButton = ((Button) findViewById(R.id.doneButton));
            doneButton.setText("Done");
        }

        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus){
                    hideKeyboard(view);
                    if(validateName())
                        currGuest.setName(name.getText().toString());
                }
            }
        });

        age.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(view);
                    if(validateAge())
                        currGuest.setAge(Integer.parseInt(age.getText().toString()));
                }
            }
        });

        gender.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                currGuest.setMale(!isChecked);
            }
        });

        //getSupportActionBar().setTitle("Edit Guest");  //provide compatibility to all the versions




        if(!isEdit){
            guestList.add(currGuest);
        }


        ListView listView = (ListView) findViewById(R.id.guestList);

        GuestList filteredList = guestList.filtered(currGuest);

        preferenceListAdapter = new PreferenceListAdapter(this, filteredList, currGuest);

        listView.setAdapter(preferenceListAdapter);
    }

    public void goToGuestList(View v){

        EditText  name = ((EditText) findViewById(R.id.nameTxt));
        EditText age = ((EditText) findViewById(R.id.ageTxt));
        boolean gender = ((Switch) findViewById(R.id.genderSwitch)).isChecked();


        //validation
        if(!validateName() | !validateAge())
            return;

        if (isEdit){
            Bundle bundle = getIntent().getExtras();
            int index = bundle.getInt("index");

            currGuest.setName(name.getText().toString());
            currGuest.setAge(Integer.parseInt(age.getText().toString()));
            currGuest.setMale(!gender);

            guestList.set(index, currGuest);

        }else {
            guestList.set(guestList.size() - 1, currGuest);
        }

        guestList.saveData(this, "guests");

        Intent intent = new Intent(AddGuestActivity.this, GuestListActivity.class);

        startActivity(intent);
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void recalculatePreferences(View view){
        currGuest.recalculateChanges();
        preferenceListAdapter.notifyDataSetChanged();
    }

    public boolean validateName(){
        EditText  name = ((EditText) findViewById(R.id.nameTxt));

        if( name.getText().toString().length() == 0 ) {
            name.setError("Please input a name for this guest");
            return false;
        }
        return true;
    }

    public boolean validateAge(){
        EditText  age = ((EditText) findViewById(R.id.ageTxt));

        try {
            int tempAge = Integer.parseInt(age.getText().toString());
            if(tempAge > 120 || tempAge < 0) {
                age.setError("invalid age");
                return false;
            }

            return true;

        } catch (Exception e) {
            age.setError("please input an age");
            return false;
        }
    }

}
