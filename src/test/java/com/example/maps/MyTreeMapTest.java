package com.example.maps;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.number.OrderingComparison.comparesEqualTo;

public class MyTreeMapTest {

    private MyTreeMap<String, Integer> map;

    /**
     * @throws java.lang.Exception
     */
    @BeforeEach
    public void setUp() throws Exception {
        map = new MyTreeMap<String, Integer>();
        //this node is going to be the root
        MyTreeMap<String, Integer>.Node node08 = map.makeNode("08", 8, null);

        MyTreeMap<String, Integer>.Node node03 = map.makeNode("03", 3, node08);
        MyTreeMap<String, Integer>.Node node10 = map.makeNode("10", 10, node08);
        node08.left = node03;
        node08.right = node10;

        MyTreeMap<String, Integer>.Node node01 = map.makeNode("01", 1, node03);
        MyTreeMap<String, Integer>.Node node06 = map.makeNode("06", 6, node03);
        MyTreeMap<String, Integer>.Node node14 = map.makeNode("14", 14, node10);
        node03.left = node01;
        node03.right = node06;
        node10.right = node14;

        MyTreeMap<String, Integer>.Node node04 = map.makeNode("04", 4, node06);
        MyTreeMap<String, Integer>.Node node07 = map.makeNode("07", 7, node06);
        MyTreeMap<String, Integer>.Node node13 = map.makeNode("13", 13, node14);
        node06.left = node04;
        node06.right = node07;
        node14.left = node13;

        map.setTree(node08, 9);
    }

    /**
     * Test method for {@link MyLinearMap#clear()}.
     */
    @Test
    public void testClear() {
        map.clear();
        assertThat(map.size(), comparesEqualTo(0));
    }

    /**
     * Test method for {@link MyLinearMap#containsKey(java.lang.Object)}.
     */
    @Test
    public void testContainsKey() {
        assertThat(map.containsKey("03"), is(true));
        assertThat(map.containsKey("05"), is(false));
    }

    /**
     * Test method for {@link MyLinearMap#containsValue(java.lang.Object)}.
     */
    @Test
    public void testContainsValue() {
        assertThat(map.containsValue(3), is(true));
        assertThat(map.containsValue(5), is(false));
    }

    /**
     * Test method for {@link MyLinearMap#get(java.lang.Object)}.
     */
    @Test
    public void testGet() {
        assertThat(map.get("01"), is(equalTo(1)));
        assertThat(map.get("03"), is(3));
        assertThat(map.get("04"), is(4));
        assertThat(map.get("06"), is(6));
        assertThat(map.get("07"), is(7));
        assertThat(map.get("08"), is(8));
        assertThat(map.get("10"), is(10));
        assertThat(map.get("13"), is(13));
        assertThat(map.get("14"), is(14));

        assertThat(map.get("02"), is(nullValue()));
        assertThat(map.get("05"), is(nullValue()));
    }

    /**
     * Test method for {@link MyLinearMap#isEmpty()}.
     */
    @Test
    public void testIsEmpty() {
        assertThat(map.isEmpty(), is(false));
        map.clear();
        assertThat(map.isEmpty(), is(true));
    }

    /**
     * Test method for {@link MyLinearMap#keySet()}.
     */
    @Test
    public void testKeySet() {
        Set<String> keySet = map.keySet();
        assertThat(keySet.size(), is(9));
        assertThat(keySet.contains("03"), is(true));
        assertThat(keySet.contains("05"), is(false));

        List<String> list = new ArrayList<String>();
        list.addAll(keySet);

        // check that the keys are in ascending order
        for (int i=0; i<list.size()-1; i++) {
            System.out.println(list.get(i));
            if (list.get(i).compareTo(list.get(i+1)) > 0) {
                assert(false);
            }
        }
        // TODO: fight with hamcrest
        // Collections.sort(list);
        // assertThat(keySet, contains(list));
    }

    /**
     * Test method for {@link MyLinearMap#put(java.lang.Object, java.lang.Object)}.
     */
    @Test
    public void testPut() {
        map.put("06", 66);
        assertThat(map.size(), is(9));
        assertThat(map.get("06"), is(66));

        map.put("05", 5);
        assertThat(map.size(), is(10));
        assertThat(map.get("05"), is(5));
    }

    /**
     * Test method for {@link MyLinearMap#putAll(java.util.Map)}.
     */
    @Test
    public void testPutAll() {
        Map<String, Integer> m = new HashMap<String, Integer>();
        m.put("02", 2);
        m.put("05", 5);
        m.put("12", 12);
        map.putAll(m);
        assertThat(map.size(), is(12));
    }

    /**
     * Test method for {@link MyLinearMap#remove(java.lang.Object)}.
     */
    @Test
    public void when_remove_not_existing_node_then_return_null_and_map_not_changed() throws NoSuchFieldException, IllegalAccessException {
        int initialSize = map.size();
        MyTreeMap.Node root = getRoot(map);

        assertThat(map.remove("not_existing_key"), is(nullValue()));
        assertThat(map.size(), is(equalTo(initialSize)));
        assertThat(getRoot(map), is(equalTo(root)));

    }

    @Test
    public void remove_not_root_node_no_children() throws NoSuchFieldException, IllegalAccessException {
        int initialSize = map.size();
        MyTreeMap.Node root = getRoot(map);

        Integer removed = map.remove("01");
        assertThat(map.size(), is(equalTo(initialSize-1)));
        assertThat(map.get("01"), is(nullValue()));
        assertThat(removed, is(equalTo(1)));
        assertThat("Tree root has changed", getRoot(map), is(equalTo(root)));

    }

    @Test
    public void remove_root_node_no_childen(){
        String key = "key";
        Map<String, Integer> map = new MyTreeMap<>();
        map.put(key, 100);
        assertThat(map.size(), is(equalTo(1)));

        Integer removed = map.remove(key);
        assertThat(map.isEmpty(), is(true));
        assertThat(removed, is(equalTo(100)));
    }

    @Test
    public void remove_not_root_node_with_only_left_child() throws NoSuchFieldException, IllegalAccessException {
        int initialSize = map.size();
        MyTreeMap.Node root = getRoot(map);

        Integer removed= map.remove("14");
        assertThat(map.size(), is(equalTo(initialSize-1)));
        assertThat(map.get("14"), is(nullValue()));
        assertThat(removed, is(equalTo(14)));
        assertThat(getRoot(map), is(equalTo(root)));
    }

    @Test
    public void remove_root_node_with__only_left_child() throws NoSuchFieldException, IllegalAccessException {
        MyTreeMap<Integer, Integer> map = new MyTreeMap<>();
        map.put(10, 10); map.put(5, 5); map.put(1, 1); map.put(7, 7);
        //     10
        //    /
        //    5
        //   /\
        // 1  7
        MyTreeMap.Node root = getRoot(map);
        int initialSize = map.size();

        Integer removed = map.remove(10);
        assertThat(map.size(), is(equalTo(initialSize-1)));
        assertThat(map.get(10), is(nullValue()));
        assertThat(removed, is(equalTo(10)));
        assertThat(getRoot(map).key, is(equalTo(5)));
    }

    @Test
    public void remove_not_root_node_with_only_right_child() throws NoSuchFieldException, IllegalAccessException {
        int initialSize = map.size();
        MyTreeMap.Node root = getRoot(map);

        Integer removed = map.remove("10");
        assertThat(map.size(), is(equalTo(initialSize-1)));
        assertThat(map.get("10"), is(nullValue()));
        assertThat(removed, is(equalTo(10)));
        assertThat(getRoot(map), is(equalTo(root)));
    }

    /**
     * Test method for {@link MyLinearMap#size()}.
     */
    @Test
    public void testSize() {
        assertThat(map.size(), is(9));
    }

    /**
     * Test method for {@link MyLinearMap#values()}.
     */
    @Test
    public void testValues() {
        Collection<Integer> keySet = map.values();
        assertThat(keySet.size(), is(9));
        assertThat(keySet.contains(3), is(true));
        assertThat(keySet.contains(5), is(false));
    }

    private MyTreeMap.Node getRoot(MyTreeMap map) throws NoSuchFieldException, IllegalAccessException {
        Field rootField = MyTreeMap.class.getDeclaredField("root");
        rootField.setAccessible(true);
        return (MyTreeMap.Node) rootField.get(map);

    }
}
