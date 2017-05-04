package com.example.imersionultd.seatsuite;

import com.example.imersionultd.seatsuite.Classes.Guest;
import com.example.imersionultd.seatsuite.Classes.GuestList;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by YonahKarp on 3/2/17.
 */
public class GuestTest {
    private Guest guest;

    @Before
    public void setUp() throws Exception {
        guest = new Guest("guest", 27, true);
    }

    @Test
    public void getBidWorks(){
        GuestList list = new GuestList();


        Guest bob = new Guest("bob", 24, true);
        Guest anna = new Guest("anna", 21, false);
        Guest steve = new Guest("steve", 22, true);
        Guest josh = new Guest("josh", 27, true);
        Guest tom = new Guest("tom", 28, true);
        Guest lara = new Guest("lara", 26, false);
        Guest joe = new Guest("joe", 23, true);


        list.add(anna);
        list.add(guest);
        list.add(bob);
        list.add(steve);
        list.add(josh);
        list.add(tom);
        list.add(lara);
        list.add(joe);


        System.out.println(steve.bid(bob, anna, tom));

        list.advancedSort();

    }

    @Test
    public void getPreferenceWorks(){
        GuestList list = new GuestList();

        list.add(guest);


        Guest newGuest = new Guest("Josh", 50, false);
        list.add(newGuest);

        System.out.println("guestList size: " +list.size());

        System.out.println(guest.getPreference(newGuest));

    }



}