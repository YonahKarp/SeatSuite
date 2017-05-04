package com.example.imersionultd.seatsuite.Classes;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by YonahKarp on 4/5/17.
 */
public class TableListTest {
    TableList list;
    private Guest steve;
    private Guest josh;
    private Guest anna;

    @Before
    public void setUp(){
        list = new TableList(true);
        steve = new Guest("steve", 22, true);
        josh = new Guest("josh", 27, true);
        anna = new Guest("anna", 24, false);
    }

    @Test (timeout = 200)
    public void insertWorks(){
        list.addAtStart(steve);

        assertEquals(1, list.size());
        assertEquals(list.elementAt(0).guest, steve);
    }

    @Test
    public void insertInMiddleWorks(){
        list.addAtStart(steve);
        list.addAtEnd(anna);
        list.addAfter(steve, josh);

        assertEquals(3, list.size());
        assertEquals(josh , list.elementAt(1).guest);
    }

    @Test
    public void swapWorks(){
        list.addAtStart(steve);
        list.addAtEnd(anna);
        list.addAfter(steve, josh);

        list.swap(0,1);

        assertEquals(steve , list.elementAt(1).guest);
        assertEquals(josh , list.elementAt(0).guest);

        list.swap(steve,josh);

        assertEquals(steve , list.elementAt(0).guest);
        assertEquals(josh , list.elementAt(1).guest);

    }

    @Test
    public void isCircularWorks(){

        list.addAtEnd(anna);
        list.addAtStart(steve);

        assertEquals(list.head.guest , list.tail.next.guest);
        assertEquals(list.tail.guest , list.head.previous.guest);

        list.addAtEnd(josh);

        assertEquals(list.head.guest , list.tail.next.guest);
        assertEquals(list.tail.guest , list.head.previous.guest);
    }

    @Test
    public void nonCircularWorks(){

        list = new TableList(false);

        list.addAtEnd(anna);
        list.addAtStart(steve);

        assertNull(list.head.previous);
        assertNull(list.tail.next);

        list.addAtEnd(josh);

        assertNull(list.head.previous);
        assertNull(list.tail.next);
    }


}