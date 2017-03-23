package com.example.imersionultd.seatsuite;

import android.support.annotation.NonNull;

import java.util.Objects;

/**
 * Created by YonahKarp on 3/22/17.
 */

public class WeightedConnection implements Comparable<WeightedConnection> {
    public int weight;
    public Guest guest;

    public WeightedConnection(Guest guest, int weight){
        this.guest = guest;
        this.weight = weight;
    }

    @Override
    public boolean equals(Object obj) {
        return guest.equals(((WeightedConnection)obj).guest);
    }

    @Override
    public int compareTo(@NonNull WeightedConnection weightedConnection) {
        return guest.compareTo(weightedConnection.guest);
    }
}
