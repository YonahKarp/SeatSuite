package com.example.imersionultd.seatsuite;

import com.example.imersionultd.seatsuite.Classes.Guest;
import com.example.imersionultd.seatsuite.Classes.GuestList;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by YonahKarp on 3/29/17.
 */
public class GuestListTest {
    private GuestList list;
    private Guest steve;
    private Guest josh;
    private Guest anna;

    @Before
    public void setUp() {
        list = new GuestList();
        steve = new Guest("steve", 22, true);
        josh = new Guest("josh", 27, true);
        anna = new Guest("anna", 24, false);


        list.add(steve);
        list.add(josh);
        list.add(anna);
    }

    @Test
    public void ListContainsGuestsAdded() {

        assertTrue(list.contains(steve));
        assertTrue(list.contains(josh));
        assertTrue(list.contains(anna));
    }

    @Test
    public void ListDoesntContainGuestsNotAdded() {
        Guest mac = new Guest("mac", 21, true);
        assertFalse(list.contains(mac));
    }

    @Test
    public void FilteredListDoesntContainFilteredGuest() {
        GuestList filteredList = list.filtered(josh);

        assertTrue(list.contains(josh));
        assertFalse(filteredList.contains(josh));
    }

    @Test
    public void SortingListByPreferenceWorks() {
        Guest joe = new Guest("joe", 50, true);
        Guest max = new Guest("max", 41, true);
        Guest ela = new Guest("ela", 12, false);


        list.add(joe);
        list.add(max);
        list.add(ela);

        max.sortPreferencesByValue();

        for (Map.Entry<Guest, Double> entry: max.preferenceList.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

    @Test(timeout = 100)
    public void BasicSortWorking() {

        Guest max = new Guest("max", 41, true);
        Guest ela = new Guest("ela", 12, false);
        Guest sara = new Guest("sara", 15, false);
        Guest joe = new Guest("joe", 50, true);
        Guest tom = new Guest("tom", 28, true);
        Guest emma = new Guest("emma", 34, false);
        Guest helen = new Guest("helen", 60, false);
        Guest jerry = new Guest("jerry", 30, true);
        Guest tory = new Guest("tory", 45, true);
        Guest george = new Guest("george", 60, true);


        josh.setPreference(steve, 0.0);


        list.add(max);
        list.add(ela);
        list.add(sara);
        list.add(joe);
        list.add(tom);
        list.add(emma);
        list.add(helen);
        list.add(jerry);
        list.add(tory);
        list.add(george);

        for (Guest guest: list.basicSeatingSort()) {
            System.out.println(guest);
        }
    }

}