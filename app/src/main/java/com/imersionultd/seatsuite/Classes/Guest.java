package com.imersionultd.seatsuite.Classes;


import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by YonahKarp on 3/2/17.
 */

public class Guest implements Comparable<Guest>, Serializable {

    private Integer id; //id is used for identifying guest so preferences will remain even if name is changed

    private String name;
    private String group;
    private boolean seated = false;

    int connections = 0;

    public PreferenceList preferenceList = new PreferenceList();

    public Guest(String name, String group){
        this.name = name;
        this.group = group;
    }

//    public Guest(String name){
//        this.name = name;
//    }

    /**
     * Getters and setters
     */
    public String getName() {return name;}
    public void setName(String name) { this.name = name;}

    public String getGroup() {return group;}
    public void setGroup(String group) { this.group = group;}

    public void setId(int id) { this.id = id;}
    public int getId() {return id;}

    public void setIsSeated(boolean bool){
        seated = bool;
        if (!seated)
            connections = 0;
    }
    public boolean isSeated() {return seated;}

    /**
     * Preference List methods
     */

    public void addToPreferenceList(Guest guest){
        preferenceList.put(guest.id, 5.0);
    }

    public void removeFromPreferenceList(Guest guest){
        preferenceList.remove(guest.id);
    }


    public double getPreference(Guest guest){
        if(preferenceList.containsKey(guest.id))
            return preferenceList.get(guest.id);
        else
            return 0;
    }

    public void setPreference(Guest guest, Double val){
        preferenceList.put(guest.id, val);
        guest.preferenceList.put(id, val);
    }

    public double autoGenPreference(Guest guest){
        if(name.equals(""))
            return 5;

        double preference = 5;

        if (!group.equals("") && group.equals(guest.group))
            preference += 3;
        else
            preference -= 1;

        return preference;
    }

    public void recalculateChanges(GuestList list){
        for (Guest guest: list) {
            if (this.equals(guest))
                continue;

            setPreference(guest,5.0); //fixme doesn't reciprocate
        }
    }

    public void sortPreferencesByValue() { //O(n*log(n)))
        List<Map.Entry<Integer, Double>> list = new LinkedList<>(preferenceList.entrySet());

        Collections.sort( list, new Comparator<Map.Entry<Integer, Double>>() {
            public int compare( Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2 ) {
                return (o2.getValue()).compareTo( o1.getValue() );
            }
        });

        preferenceList.clear();
        for (Map.Entry<Integer, Double> entry : list) {
            preferenceList.put( entry.getKey(), entry.getValue() );
        }
    }

    int pickyScore(){
        int score = 0;

        for (Double val: preferenceList.values()) {
            if(val == 0)
                score+= 10;
            else
                score += 5 - val;
        }
        return score;
    }


    Guest getBestAvailableCandidate(GuestList list){
        Guest bestCandidate = null;
        double bestScore = 0;

        for (Guest candidate: list) {

            if(candidate.seated || candidate.equals(this))
                continue;

            double score = candidate.getPreference(this) + this.getPreference(candidate);

            if(score > bestScore) {
                bestScore = score;
                bestCandidate = candidate;
            }
        }
        return  bestCandidate;
    }

    public double bid(GuestList list, Guest ... neighbors){
        return bidBasedOnNeighbors(neighbors) + bidBasedOnRemaining(list, neighbors);
    }

    private double bidBasedOnNeighbors(Guest ... neighbors) {
        double bid = 0;

        for (Guest neighbor : neighbors) {
            if (neighbor == null)
                continue;

            if (getPreference(neighbor) == 10)
                bid += 1000; //sets preference 10 above all else (must sit next to), but within 10's there can be competition
            else if (getPreference(neighbor) == 0)
                return -1000; //nope, don't want to sit here

            bid += 2*getPreference(neighbor); //neighbor.getPreference(this)
        }
        return bid;
    }


    private double bidBasedOnRemaining(GuestList list, Guest ... neighbors){
        double bid = 0;
        double personalPrefs = 0;

        int nonNullNeighbors = 0;
        for (Guest neighbor : neighbors)
            if(neighbor != null) {
                personalPrefs += getPreference(neighbor);
                nonNullNeighbors += 1;
            }

        personalPrefs /= nonNullNeighbors;          //get average of how much neighbors are liked

        for(Guest guest : list){

            if (guest.equals(this) || foundInArray(guest, neighbors))  //skip neighbors (because you're already sitting next to them)
                continue;

            double value = preferenceList.get(guest.id);

            if(value == 10 && guest.connections == 1) //if there's only one seat left & there's a 10 we're not neighbors with
                return -500;                        // then we are very uninterested in sitting next to anyone else

                                                    //if guest prefers others more, bid will be lowered
            if (guest.connections < 2 && value > personalPrefs)
                bid -= (value - personalPrefs) / 2;
            else                                    //but if guest has (many) others preferred less, bid will be upped
                bid += (personalPrefs - value) / 2;
        }
        return bid;
    }

    private boolean foundInArray(Guest key, Guest ... neighbors){ //todo fix this method
        for(Guest neighbor : neighbors)
            if (neighbor != null && key.equals(neighbor))
                return true;
        return false;
    }


    @Override
    public String toString(){
        return name;
    }

    @Override
    public int compareTo(Guest guest) {
        return id.compareTo(guest.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Guest))
            return false;

        Guest guest = (Guest) obj;

        return id.equals(guest.id);

    }
}
