package com.example.imersionultd.seatsuite;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by YonahKarp on 3/2/17
 */

public class GuestList extends ArrayList<Guest> implements Serializable {
    //Map<String, Guest> guests = new TreeMap<>();

    public GuestList(){
        super();
    }

    public void add(String name, int age, boolean male){
        add(new Guest(name, age, male));
    }


    public Guest remove(int i){

        Guest removed = super.remove(i);

        for (Guest guest : this) {
            //if (guest.whitelistContains(removed)) //just a waste, remove does checking by itself either way
            guest.removeFromWhitelist(removed);
            guest.removeFromBlacklist(removed);
            guest.removeFromGreylist(removed);
        }

        return removed;
    }

}
