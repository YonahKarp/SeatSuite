package com.example.imersionultd.seatsuite;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by YonahKarp on 3/2/17
 */

public class GuestList extends TreeMap<String, Guest> {
    //Map<String, Guest> guests = new TreeMap<>();

    public GuestList(){
        super();
    }

    public void add(String name, int age, boolean male){
        put(name, new Guest(name, age, male));
    }

    public void add(Guest guest){
        put(guest.getName(), guest);
    }

    public Guest remove(String key){

        if(!containsKey(key))
            return null;

        Guest removed = super.remove(key);

        for (Guest guest : values()) {
            //if (guest.whitelistContains(removed)) //just a waste, remove does checking by itself either way
            guest.removeFromWhitelist(removed);
            guest.removeFromBlacklist(removed);
            guest.removeFromGreylist(removed);
        }

        return removed;
    }

    //copies keys to a new collection so guestues can be manipulated without side effects happening on graph
    public Collection<String> keyCopies(){
        ArrayList<String> copiedKeys = new ArrayList<>();
        for (String copy: keySet()) {
            copiedKeys.add(copy);
        }
        return copiedKeys;
    }
}
