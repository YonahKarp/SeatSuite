package com.example.imersionultd.seatsuite;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by YonahKarp on 3/2/17.
 */

public class Guest implements Comparable<Guest>, Serializable {
    private String name; //must be unique
    private int age;
    private boolean male; //gender

    public PreferenceList preferenceList = new PreferenceList();

    private Set<Guest> blacklist = new HashSet<>();
    private Set<Guest> greylist = new HashSet<>(); //prefers to sit next to
    private Set<Guest> whitelist = new HashSet<>(); //must sit next to

    public Guest(String name, int age, boolean male){
        this.name = name;
        this.age = age;
        this.male = male;
    }

    /**
     * Getters and setters
     */

    public String getName() {return name;}
    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {return age;}
    public void setAge(int age) {this.age = age;}

    public boolean isMale() {return male;}
    public void setMale(boolean male) {
        this.male = male;
    }

    /**
     *  blacklist add/remove
     */
    public String addGuestToBlacklist(Guest guest){

        if(greylistContains(guest) )
            //messages will be more user friendly on release // TODO: 3/2/17 User friendly messages
            return couldNotBeAddededMessage(guest, "grey");
        if(whitelistContains(guest) )
            return couldNotBeAddededMessage(guest, "white");
        if(blacklistContains(guest))
            return guest.name +" already exists on this list";

        blacklist.add(guest);
        return guest.name +" added to " + name +"'s black list";
    }



    public String removeFromBlacklist(Guest guest){
        if (!blacklistContains(guest))
            return guest.name +" was not found on this list";
        blacklist.remove(guest);
        return guest.name +"removed";
    }

    public boolean blacklistContains(Guest guest){
        return blacklist.contains(guest);
    }

    /**
     *  greylist add/remove
     */
    public String addGuestToGreylist(Guest guest){

        if(blacklistContains(guest) )
            return couldNotBeAddededMessage(guest, "black");
        if(whitelistContains(guest) )
            return couldNotBeAddededMessage(guest, "white");
        if(greylistContains(guest))
            return guest.name +" already exists on this list";

        greylist.add(guest);
        return guest.name +" added to " + name +"'s grey list";
    }

    public String removeFromGreylist(Guest guest){
        if (!greylistContains(guest))
            return guest.name +" was not found on this list";
        greylist.remove(guest);
        return guest.name +"removed";
    }

    public boolean greylistContains(Guest guest){
        return greylist.contains(guest);
    }

    /**
     *  whitelist add/remove
     */
    public String addGuestToWhitelist(Guest guest){

        if (whitelist.size() >= 2)
            return "Only 2 guests can be added to the white list at a time";

        if(blacklistContains(guest) )
            return couldNotBeAddededMessage(guest, "black");
        if(greylistContains(guest) )
            return couldNotBeAddededMessage(guest, "grey");
        if(whitelistContains(guest))
            return guest.name +" already exists on this list";

        whitelist.add(guest);
        return guest.name +" added to " + name +"'s white list";
    }

    public String removeFromWhitelist(Guest guest){
        if (!whitelistContains(guest))
            return guest.name +" was not found on this list";
        whitelist.remove(guest);
        return guest.name +"removed";
    }

    public boolean whitelistContains(Guest guest){
        return whitelist.contains(guest);
    }

    private String couldNotBeAddededMessage(Guest guest, String color) {
        return guest.name +" could not be added to this list. " + (guest.male? "He":"She") + " already exists on " + name +"'s "+ color+"  list";
    }

    /**
     * Preference List methods
     */

    public void addToPreferenceList(Guest guest){
        preferenceList.put(guest, 5);
        System.out.println(guest.name);
    }

    public void removeFromPreferenceList(Guest guest){
        preferenceList.remove(guest);
    }


    public int getPreference(Guest guest){
        if(preferenceList.containsKey(guest))
            return preferenceList.get(guest);
        else
            return 0;
    }

    public void setPreference(Guest guest, int val){
        preferenceList.put(guest, val);
    }



    @Override
    public String toString(){
        return name + " (" + age +") ";
    }

    @Override
    public int compareTo(Guest guest) {
        return name.compareTo(guest.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Guest))
            return false;

        Guest guest = (Guest) obj;

        return name.equals(guest.name);

    }
}
