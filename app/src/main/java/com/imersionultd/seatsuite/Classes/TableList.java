package com.imersionultd.seatsuite.Classes;

/**
 * Created by YonahKarp on 4/5/17.
 */

public class TableList {
    private int currSize = 0;
    GuestNode head = null;
    GuestNode tail = null;

    private boolean isCircular = true;

    public TableList(){

    }

    public TableList(boolean isCircular){
        this.isCircular = isCircular;
    }

    public boolean addAtStart(Guest guest){
        GuestNode node = new GuestNode(guest);
        if(currSize == 0){
            head = node;
            tail = node;
            head.previous = (isCircular? tail:null);
            tail.next = (isCircular? head:null);
        }else{
            node.next = head;
            head.previous = node;
            head = node;
            head.previous = (isCircular? tail:null);
            tail.next = (isCircular? head:null);

        }
        currSize++;
        return true;
    }
    public boolean addAtEnd(Guest guest){
        GuestNode node = new GuestNode(guest);
        if(currSize==0){
            head = node;
            tail = node;

            head.previous = (isCircular? tail:null);
            tail.next = (isCircular? head:null);
        }else{
            tail.next = node;
            node.previous = tail;
            tail = node;

            head.previous = (isCircular? tail:null);
            tail.next = (isCircular? head:null);
        }
        currSize++;
        return true;
    }
    public boolean addAfter(GuestNode prevNode, Guest guest){
        if(prevNode == null){
            return false;
        }else if(prevNode == tail){
            return addAtEnd(guest);
        }else{
            GuestNode node = new GuestNode(guest);
            GuestNode nextNode = prevNode.next;
            node.next = nextNode;
            prevNode.next = node;
            nextNode.previous = node;
            node.previous = prevNode;
            currSize++;
            return true;
        }
    }

    public boolean addAfter(Guest prevGuest, Guest guest){
        return addAfter(getNodeOf(prevGuest), guest);
    }


    public GuestNode getNodeOf(Guest guest){

        int index = 0;

        GuestNode node = head;
        do{
            if(node.guest.equals(guest))
                return elementAt(index);
            node = node.next;
            index++;
        }while ((node.next != (isCircular? head: null)));

        return null;
    }


    public void setAt(int index, Guest guest){
        GuestNode guestNode = elementAt(index);
        guestNode.guest = guest;
    }

    public void swap(int index1, int index2){
        Guest guest1 = elementAt(index1).guest;
        Guest guest2 = elementAt(index2).guest;

        setAt(index1, guest2);
        setAt(index2, guest1);
    }

    public void swap(Guest guest1, Guest guest2){ //throws null pointer exception
        GuestNode temp = getNodeOf(guest2);
        getNodeOf(guest1).guest = guest2;
        temp.guest = guest1;
    }


    public void deleteFromStart(){
        if(currSize == 0){
        }else{
            head = head.next;
            head.previous = (isCircular? tail:null);
            currSize--;
        }
    }
    public boolean deleteFromEnd(){
        if(currSize==0){
            return false;
        }else if(currSize == 1){
            deleteFromStart();
            return true;
        }else{

            tail = tail.previous;

            tail.next = (isCircular? head:null);

            currSize--;
            return true;
        }
    }



    public GuestNode elementAt(int index){
        if(index > currSize){
            return null;
        }
        GuestNode node = head;
        while(index-1 >= 0){
            node = node.next;
            index--;
        }
        return node;
    }

    public int size(){
        return currSize;
    }


    public void print(){
        GuestNode temp = head;
        System.out.println("Doubly Linked List: ");
        do{
            System.out.println(" " + temp.guest);
            temp = temp.next;
        }while (temp != (isCircular? head:null));
        System.out.println();
    }


    class GuestNode{
        Guest guest;
        GuestNode next;
        GuestNode previous;
        GuestNode across = null;

        public GuestNode(Guest guest){
            this.guest = guest;
            next = null;
            previous = null;
        }


    }
}
