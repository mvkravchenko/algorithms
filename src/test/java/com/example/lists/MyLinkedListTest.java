package com.example.lists;

public class MyLinkedListTest extends MyListAbstractTest<MyLinkedList> {


    @Override
    protected MyLinkedList createMylistInstance() {
        return new MyLinkedList<Integer>();
    }
}