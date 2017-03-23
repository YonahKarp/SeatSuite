package com.example.imersionultd.seatsuite;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by YonahKarp on 3/2/17.
 */
public class GuestTest {
    private Guest guest;

    @Before
    public void setUp() throws Exception {
        guest = new Guest("Steve", 27, true);
    }

    @Test
    public void getPreferenceWorks(){
        GuestList list = new GuestList();

        list.add(guest);



        Guest newGuest = new Guest("Josh", 22, false);
        list.add(newGuest);

        System.out.println("guestList size: " +list.size());

        System.out.println(guest.getPreference(newGuest));

    }


}