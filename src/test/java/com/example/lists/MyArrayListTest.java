package com.example.lists;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

public class MyArrayListTest extends MyListAbstractTest<MyArrayList> {

    @Override
    protected MyArrayList createMylistInstance() {
        return new MyArrayList<Integer>();
    }
}
