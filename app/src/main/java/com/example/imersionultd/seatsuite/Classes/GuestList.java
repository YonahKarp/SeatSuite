package com.example.imersionultd.seatsuite.Classes;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;

/**
 * Created by YonahKarp on 3/2/17
 */

public class GuestList extends ArrayList<Guest> implements Serializable {

    private Integer maxId; //auto incrementing id

    public GuestList(){
        super();
    }


    public void add(String name, int age, boolean male){
        Guest newGuest = new Guest(name, age, male);
        newGuest.setId(getMaxId());

        for (Guest guest: this) {
            guest.addToPreferenceList(newGuest); //add new guest to all other lists
            newGuest.addToPreferenceList(guest); //add all others to newGuest's list
        }

        super.add(newGuest);
    }

    @Override
    public boolean add(Guest newGuest) {
        newGuest.setId(getMaxId());

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
            guest.removeFromPreferenceList(removed);
        }

        return removed;
    }

    public GuestList filtered(Guest guestToFilter){
        GuestList filtered = new GuestList();
        for(Guest guest : this){
            if (!guestToFilter.equals(guest))
                filtered.add(guest);
        }

        return filtered;
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

    public GuestList basicSeatingSort(){
        //choose least picky
        Guest currGuest = getLeastPickyGuest();

        //now we seat
        GuestList seating = new GuestList();
        seating.add(currGuest);
        currGuest.isSeated = true;

        //initial pass
        while (seating.size() < size()){

            Guest bestCandidate = currGuest.getBestAvailableCandidate();

            seating.add(bestCandidate);
            bestCandidate.isSeated = true;

            currGuest = bestCandidate;
        }
        return seating;
    }

    private Guest getLeastPickyGuest(){
        int lowestScore = Integer.MAX_VALUE;
        Guest leastPickyGuest = null;

        for (Guest guest: this) {
            guest.isSeated = false; //set at beginning of sort algorithms
            int score = guest.pickyScore();

            if (score < lowestScore) {
                lowestScore = score;
                leastPickyGuest = guest;
            }
        }
        return leastPickyGuest;
    }

    public TableArray advancedSort(){
        TableArray table = new TableArray(size());
        Guest currGuest = getLeastPickyGuest();

        table.set(0, currGuest);
        currGuest.isSeated = true;

        Stack<Integer> seats = new Stack<>();
        seats.push(1);
        seats.push(table.getIndex(-1));


        while (!seats.isEmpty()){
            int seatIndex = seats.pop();

            Guest highestBidder = highestBidder(table.get(seatIndex -1), table.get(table.acrossIndex(seatIndex)), table.get(seatIndex + 1));

            table.set(seatIndex, highestBidder);

            int[] neighbors = {seatIndex + 1, seatIndex -1, table.acrossIndex(seatIndex)};
            for (int index: neighbors) {
                if (table.get(index) == null && !seats.contains(index))
                    seats.push(index);
            }
        }

        return table;

    }

    private Guest highestBidder(Guest ... neighbors){

        double highestBid = Integer.MIN_VALUE;
        Guest highestBidder = null;

        for (Guest guest : this){
            if(guest.isSeated)
                continue;

            double bid = guest.bid(neighbors);

            if (bid > highestBid) {
                highestBid = bid;
                highestBidder = guest;
            }
        }
        return highestBidder; 
    }

    private boolean valueFoundInArray(Guest val, Guest ... neighbors){
        for(Guest neighbor : neighbors)
            if (neighbor.equals(val))
                return true;
        return false;
    }

    private int getMaxId(){
        if (maxId == null){
            maxId = -1;
            for (Guest guest: this)
                if (guest.getId() > maxId)
                    maxId = guest.getId();
        }

        //note it also increments the max
        return ++maxId;
    }

}
