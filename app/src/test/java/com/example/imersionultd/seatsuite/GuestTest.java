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


}