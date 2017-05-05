package com.example.imersionultd.seatsuite.Classes;


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

    private String name; //must be unique
    private int age;
    private boolean male; //gender
    boolean isSeated = false;

    int connections = 0;

    public PreferenceList preferenceList = new PreferenceList();

    //private Set<Guest> blacklist = new HashSet<>();
    //private Set<Guest> greylist = new HashSet<>(); //prefers to sit next to
    //private Set<Guest> whitelist = new HashSet<>(); //must sit next to

    public Guest(String name, int age, boolean male){
        this.name = name;
        this.age = age;
        this.male = male;

    }

    /**
     * Getters and setters
     */
    public String getName() {return name;}
    public void setName(String name) { this.name = name;}

    public int getAge() {return age;}
    public void setAge(int age) {this.age = age;}

    public boolean isMale() {return male;}
    public void setMale(boolean male) { this.male = male;}

    public void setId(int id) { this.id = id;}

    public int getId() {return id;}

    /**
     * Preference List methods
     */

    public void addToPreferenceList(Guest guest){
        preferenceList.put(guest, autoGenPreference(guest));
        //System.out.println(guest.name);
    }

    public void removeFromPreferenceList(Guest guest){
        preferenceList.remove(guest);
    }


    public double getPreference(Guest guest){
        if(preferenceList.containsKey(guest))
            return preferenceList.get(guest);
        else
            return 0;
    }

    public void setPreference(Guest guest, Double val){
        preferenceList.put(guest, val);
        guest.preferenceList.put(this, val);
    }

    public double autoGenPreference(Guest guest){
        if(name.equals(""))
            return 5;

        double preference = 5;

        preference += (male == guest.isMale())? 2 : -2;

        preference += 3 - Math.abs(Math.round((age - guest.age)/2.5));

        if(preference >= 10)
            preference = 9 + .01 * preference;
        if (preference <= 0)
            preference = 1 + .001 * preference; //sets apart not-like from really-not-like with auto-gen

        return preference;
    }

    public void recalculateChanges(){
        for (Guest guest: preferenceList.keySet()) {
            setPreference(guest, autoGenPreference(guest));
        }
    }

    public void sortPreferencesByValue() { //O(n*log(n)))
        List<Map.Entry<Guest, Double>> list = new LinkedList<>(preferenceList.entrySet());

        Collections.sort( list, new Comparator<Map.Entry<Guest, Double>>() {
            public int compare( Map.Entry<Guest, Double> o1, Map.Entry<Guest, Double> o2 ) {
                return (o2.getValue()).compareTo( o1.getValue() );
            }
        });

        preferenceList.clear();
        for (Map.Entry<Guest, Double> entry : list) {
            preferenceList.put( entry.getKey(), entry.getValue() );
        }
    }

    int pickyScore(){
        int score = 0;

        for (Double val: preferenceList.values()) {
            if(val == 10)
                score+=2;
            else if(val == 0)
                score++;
        }
        return score;
    }


    Guest getBestAvailableCandidate(){
        Guest bestCandidate = null;
        double bestScore = 0;

        for (Map.Entry<Guest, Double> entry : this.preferenceList.entrySet()) {
            Guest candidate = entry.getKey();

            if(candidate.isSeated)
                continue;

            double score = candidate.getPreference(this) + this.getPreference(candidate);

            if(score > bestScore) {
                bestScore = score;
                bestCandidate = candidate;
            }
        }
        return  bestCandidate;
    }

    public double bid(Guest ... neighbors){
        return bidBasedOnNeighbors(neighbors) + bidBasedOnRemaining(neighbors);
    }

    private double bidBasedOnNeighbors(Guest ... neighbors) {
        double bid = 0;

        for (Guest neighbor : neighbors) {
            if (neighbor == null)
                continue;

            if (getPreference(neighbor) == 10)
                bid += 1000; //sets preference 10 above all else (must sit next to), but within 10's there can be competition
            else if (getPreference(neighbor) == 0)
                return Integer.MIN_VALUE; //nope, don't want to sit here

            bid += neighbor.getPreference(this)
                    + getPreference(neighbor);
        }
        return bid;
    }


    private double bidBasedOnRemaining(Guest ... neighbors){
        double bid = 0;
        double personalPrefs = 0;

        int nonNullNeighbors = 0;
        for (Guest neighbor : neighbors)
            if(neighbor != null) {
                personalPrefs += getPreference(neighbor);
                nonNullNeighbors += 1;
            }

        personalPrefs /= nonNullNeighbors;          //get average of how much neighbors are liked

        for (Map.Entry<Guest, Double> entry : preferenceList.entrySet()) {

            Guest key = entry.getKey();
            double value = entry.getValue();

            if (foundInArray(neighbors))            //skip neighbors (because you're already sitting next to them)
                continue;

            if(value == 10 && key.connections == 2) //if there's only one stop left & there's a 10 we're not neighbors with
                return -500;                        // then we are very uninterested in sitting next to anyone else

                                                    //if guest prefers others more, bid will be lowered
            if (key.connections < 3 && entry.getValue() > personalPrefs)
                bid -= (entry.getValue() - personalPrefs) / 2;
            else                                    //but if guest has (many) others preferred less, bid will be upped
                bid += (personalPrefs - entry.getValue()) / 4;
        }
        return bid;
    }

    private boolean foundInArray(Guest ... neighbors){
        for(Guest neighbor : neighbors)
            if (neighbor != null && neighbor.equals(this))
                return true;
        return false;
    }


    @Override
    public String toString(){
        return name + " (" + age +") id: " + id;
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
