package com.example.imersionultd.seatsuite;

import java.io.Serializable;

/**
 * Wrapper Class to avoid bundle issues flattening GuestList to ArrayList
 */

class GuestListWrapper implements Serializable {
    GuestList list = null;
    GuestListWrapper(GuestList list){
        this.list = list;
    }
}
