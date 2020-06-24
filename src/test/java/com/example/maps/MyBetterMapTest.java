package com.example.maps;

import org.junit.jupiter.api.BeforeEach;

class MyBetterMapTest extends MyMapAbstractTest{
    /**
     * @throws java.lang.Exception
     */
    @BeforeEach
    public void setUp() throws Exception {
        map = new MyBetterMap<String, Integer>();
        map.put("One", 1);
        map.put("Two", 2);
        map.put("Three", 3);
        map.put(null, 0);
    }

}