package com.imersionultd.seatsuite.Classes;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by YonahKarp on 4/5/17.
 */
public class TableArrayTest {
    private TableArray list;
    private Guest steve;
    private Guest josh;
    private Guest anna;

    private Guest bob;
    private Guest tom;
    private Guest lara;

    @Before
    public void setUp() throws Exception {
        list = new TableArray();
        steve = new Guest("steve", 22, true);
        josh = new Guest("josh", 27, true);
        anna = new Guest("anna", 24, false);

        bob = new Guest("bob", 30, true);
        tom = new Guest("tom", 28, true);
        lara = new Guest("lara", 26, false);

    }

    @Test
    public void acrossIndex() throws Exception {


       assertEquals(7,list.acrossIndex(1));
    }

    @Test
    public void circularWorks() throws Exception {
        list.add(steve);
        list.add(josh);
        list.add(anna);

        assertEquals(steve, list.get(-3));
    }

    @Test
    public void arraySize() throws Exception {

        list.set(4, steve);

        assertEquals(steve, list.get(4));
        assertEquals(null, list.get(5));
    }

    @Test
    public void guestConnectionsCountCorrect() throws Exception {

        list.set(0, anna);
        list.set(1,josh);

        assertEquals(1, josh.connections);
        assertEquals(2, anna.connections);

        list.set(7, steve);

        assertEquals(2, josh.connections);
        assertEquals(2, steve.connections);
        assertEquals(3, anna.connections);

        list.set(2, bob);

        assertEquals(1, bob.connections);
        assertEquals(3, josh.connections);
        assertEquals(2, steve.connections);
        assertEquals(3, anna.connections);
    }

    /*
    @Test
    public void circularIndeciesWorksPositive() throws Exception {
        list.add(steve);
        list.add(josh);
        list.add(anna);
        list.add(steve);
        list.add(steve);


        //positive
        assertEquals(2, list.getIndex(2));
        assertEquals(0, list.getIndex(0));
        assertEquals(0, list.getIndex(5));
        assertEquals(3, list.getIndex(8));


    }

    @Test
    public void circularIndeciesWorksNegative() throws Exception {

        list.add(steve);
        list.add(josh);
        list.add(anna);
        list.add(steve);

        //negative
        assertEquals(3, list.getIndex(-1));
        assertEquals(0, list.getIndex(-4));
        assertEquals(1, list.getIndex(-3));
        assertEquals(2, list.getIndex(-6));

    }*/

}