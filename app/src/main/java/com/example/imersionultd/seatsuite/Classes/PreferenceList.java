package com.example.imersionultd.seatsuite.Classes;

import com.example.imersionultd.seatsuite.Classes.Guest;

import java.util.LinkedHashMap;

/**
 * Created by YonahKarp on 3/22/17.
 */

public class PreferenceList extends LinkedHashMap<Guest, Double> {
    public PreferenceList(){
        super();
    }

    public void add(Guest key) {
        put(key, 5.0);
    }

}
