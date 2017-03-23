package com.example.imersionultd.seatsuite;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by YonahKarp on 3/2/17
 */

public class GuestList extends ArrayList<Guest> implements Serializable {

    public GuestList(){
        super();
    }

    public void add(String name, int age, boolean male){
        Guest newGuest = new Guest(name, age, male);

        System.out.println("adding to guestList");

        for (Guest guest: this) {
            guest.addToPreferenceList(newGuest); //add new guest to all other lists
            newGuest.addToPreferenceList(guest); //add all others to newGuest's list
        }

        super.add(newGuest);
    }

    @Override
    public boolean add(Guest newGuest) {

        for (Guest guest: this) {
            guest.addToPreferenceList(newGuest); //add new guest to all other lists
            newGuest.addToPreferenceList(guest); //add all others to newGuest's list
        }
        return super.add(newGuest);
    }

    //inefficient O(n)
    public Guest get(String key){
        for (Guest guest: this) {
            if(guest.getName().equals(key))
                return guest;
        }
        return null;
    }


    public Guest remove(int i){

        Guest removed = super.remove(i);

        for (Guest guest : this) {
            guest.removeFromWhitelist(removed);
            guest.removeFromBlacklist(removed);
            guest.removeFromGreylist(removed);
        }

        return removed;
    }

    public boolean saveData(Context context, String fileName){
        try {
            FileOutputStream fileOut = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(this);

            out.close();
            fileOut.close();
            return true; //success
        } catch (Exception e) {e.printStackTrace();}
        return false; //failure
    }

    public boolean loadData(Context context, String fileName){
        GuestList list = null;
        try {
            FileInputStream fileIn = context.openFileInput(fileName);
            ObjectInputStream in = new ObjectInputStream(fileIn);

            list = (GuestList) in.readObject();

            in.close();
            fileIn.close();

        } catch (Exception e) {e.printStackTrace();}

        if (list != null) {
            this.clear();
            this.addAll(list);
            return true; //success
        }

        return false; //failure
    }

}
