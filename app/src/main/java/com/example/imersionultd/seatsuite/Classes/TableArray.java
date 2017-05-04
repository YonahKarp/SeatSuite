package com.example.imersionultd.seatsuite.Classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by YonahKarp on 4/5/17.
 */

public class TableArray extends ArrayList<Guest>{


    int x[];
    private boolean isCircular = true;
    private int tableSize = 8;

    public TableArray(){
        while(size() < 8)
            add(null); //fixme find better way to deal with out of bounds;
    }

    public TableArray(int tableSize){
        this.tableSize = tableSize;
        while(size() < tableSize)
            add(null);
    }

    public TableArray(boolean isCircular){
        this.isCircular = isCircular;
    }


    @Override
    public Guest set(int index, Guest guest) {
        guest.isSeated = true;

        Guest temp = get(index-1);
        if(temp != null) {
            guest.connections += 1;
            temp.connections += 1;
        }

        temp = get(index+1);
        if(temp != null) {
            guest.connections += 1;
            temp.connections += 1;
        }

        temp = get(acrossIndex(index));
        if (temp != null) {
            guest.connections += 1;
            temp.connections += 1;
        }

        //ensure that both heads hide their "3rd connection" (as they're only allowed 2 irl)
        if(index == 0 || (index == tableSize / 2 && tableSize % 2 == 0))
            guest.connections += 1;


        int trueIndex = getIndex(index);
        return super.set(trueIndex, guest);
    }

    @Override
    public Guest get(int index) {
        if(isCircular)
            return super.get(getIndex(index));

        return super.get(index);
    }

     public int getIndex(int index){
        int size = size();

        if(index >= 0)
            return index % size;
        else
            return size + ((index % size != 0 )? index % size: -size);
    }

    public void swap(int index1, int index2){
        Guest guest1 = get(index1);
        Guest guest2 = get(index2);

        set(index1, guest2);
        set(index2, guest1);
    }

    public void swap(Guest guest1, Guest guest2){
        swap(indexOf(guest1), indexOf(guest2));
    }

    public int acrossIndex(int index){
        if (index == 0)
            return 0;
        if(tableSize % 2 == 0 && index == tableSize / 2){
            return tableSize / 2;
        }

        return getIndex(-1 * index);
    }




}
