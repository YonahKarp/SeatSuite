package com.imersionultd.seatsuite.Classes;

import java.util.LinkedHashMap;

/**
 * Created by YonahKarp on 3/22/17.
 */

public class PreferenceList extends LinkedHashMap<Integer, Double> {
    public PreferenceList(){
        super();
    }

    public void add(Integer key) {
        put(key, 5.0);
    }

}
