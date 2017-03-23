package com.example.imersionultd.seatsuite;

import android.content.Intent;

import java.util.TreeMap;

/**
 * Created by YonahKarp on 3/22/17.
 */

public class PreferenceList extends TreeMap<Guest, Integer> {
    public PreferenceList(){
        super();
    }

    public void add(Guest key) {
        put(key, 5);
    }

}
