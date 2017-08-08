package com.imersionultd.seatsuite.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;

import com.imersionultd.seatsuite.Classes.Guest;
import com.imersionultd.seatsuite.Classes.GuestList;
import com.imersionultd.seatsuite.Adapters.PreferenceListAdapter;
import com.imersionultd.seatsuite.R;

public class AddGuestActivity extends AppCompatActivity {

    private GuestList guestList = new GuestList();
    private boolean isEdit = false;

    private Guest currGuest;
    //private Switch gender;

    private PreferenceListAdapter preferenceListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_add_guest);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Toolbar notepadBar = (Toolbar) findViewById(R.id.prefToolbar);
        notepadBar.setTitle("Preferences");
        notepadBar.inflateMenu (R.menu.menu_preflist);

        setTitle("Add Guest");

        final EditText name = ((EditText) findViewById(R.id.nameTxt));
        final EditText group = ((EditText) findViewById(R.id.groupTxt));



        guestList.loadData(this,"guests");
        currGuest = new Guest("","");

        Bundle bundle = getIntent().getExtras();

        int index = guestList.size();
        if(bundle != null){
            isEdit = true;
            setTitle("Edit Guest");

            index = bundle.getInt("index");

            currGuest = (Guest) bundle.getSerializable("edit");

            name.setText(currGuest.getName());
            group.setText(currGuest.getGroup());
            //gender.setChecked(!currGuest.isMale());
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

        group.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus){
                    hideKeyboard(view);
                    if(validateName())
                        currGuest.setGroup(group.getText().toString());
                }
            }
        });


        if(!isEdit){
            guestList.add(currGuest);
        }


        ListView listView = (ListView) findViewById(R.id.neighborsList);

        preferenceListAdapter = new PreferenceListAdapter(this, guestList, currGuest, index);

        listView.setAdapter(preferenceListAdapter);
    }

    public void goToGuestList(View v){

        EditText  name = ((EditText) findViewById(R.id.nameTxt));
        EditText group = ((EditText) findViewById(R.id.groupTxt));
//        boolean gender = ((Switch) findViewById(R.id.genderSwitch)).isChecked();


        //validation
        //if(!validateName() | !validateAge())
        if(!validateName())
            return;

        currGuest.setName(name.getText().toString());
        currGuest.setGroup(group.getText().toString());

        if (isEdit){
            Bundle bundle = getIntent().getExtras();
            int index = bundle.getInt("index");

//            currGuest.setAge(Integer.parseInt(age.getText().toString()));
//            currGuest.setMale(!gender);

            guestList.set(index, currGuest);

        }else {
            guestList.set(guestList.size() - 1, currGuest);
        }

        guestList.saveData(this, "guests");



        Intent intent = new Intent(AddGuestActivity.this, GuestListActivity.class);

        startActivity(intent);
        finish();
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void recalculatePreferences(MenuItem item){
        currGuest.recalculateChanges(guestList);
        preferenceListAdapter.notifyDataSetChanged();
    }

//    public void setMaleTrue(View v) {
//        currGuest.setMale(true);
//        gender.setChecked(false);
//    }
//
//    public void setMaleFalse(View v) {
//        currGuest.setMale(false);
//        gender.setChecked(true);
//    }

    public boolean validateName(){
        EditText  name = ((EditText) findViewById(R.id.nameTxt));

        if( name.getText().toString().length() == 0 ) {
            name.setError("Please input a name for this guest");
            return false;
        }
        return true;
    }

//    public boolean validateAge(){
//        EditText  age = ((EditText) findViewById(R.id.ageTxt));
//
//        try {
//            int tempAge = Integer.parseInt(age.getText().toString());
//            if(tempAge > 120 || tempAge < 0) {
//                age.setError("invalid age");
//                return false;
//            }
//
//            return true;
//
//        } catch (Exception e) {
//            age.setError("please input an age");
//            return false;
//        }
//    }

}
