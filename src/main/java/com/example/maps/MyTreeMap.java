package com.example.maps;

import java.util.*;

public class MyTreeMap<K, V> implements Map<K, V> {


    private int size = 0;
    private Node root = null;

    /**
     * Represents a node in the tree.
     *
     */
    protected class Node {
        public K key;
        public V value;
        public Node left = null;
        public Node right = null;
        public Node parent = null;

        /**
         * @param key
         * @param value
         */
        public Node(K key, V value, Node parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }

    }

    @Override
    public void clear() {
        size = 0;
        root = null;
    }

    @Override
    public boolean containsKey(Object target) {
        return findNode(target) != null;
    }

    /**
     * Returns the entry that contains the target key, or null if there is none.
     *
     * @param target
     */
    private Node findNode(Object target) {
        // some implementations can handle null as a key, but not this one
        if (target == null) {
            throw new IllegalArgumentException();
        }

        // something to make the compiler happy
        @SuppressWarnings("unchecked")
        Comparable<? super K> k = (Comparable<? super K>) target;

        Node current = root;
        while (current != null) {
            int compare = k.compareTo(current.key);
            if (compare < 0) {
                current = current.left;
            } else if (compare > 0){
                current = current.right;
            } else {
                return current;
            }
        }
        return null;
    }

    /**
     * Compares two keys or two values, handling null correctly.
     *
     * @param target
     * @param obj
     * @return
     */
    private boolean equals(Object target, Object obj) {
        if (target == null) {
            return obj == null;
        }
        return target.equals(obj);
    }

    @Override
    public boolean containsValue(Object target) {
        return containsValueHelper(root, target);
    }

    private boolean containsValueHelper(Node node, Object target) {
        if (node == null)
            return false;
        if (equals(target, node.value))
            return true;
        Collection<V> values = values();
        return values.contains(target);
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V get(Object key) {
        Node node = findNode(key);
        if (node == null) {
            return null;
        }
        return node.value;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new LinkedHashSet<K>();
        Node current = root;
        Deque<Node> stack = new ArrayDeque<>();
        while (current !=null || !stack.isEmpty()) {
            if (current != null) {
                stack.push(current);
                current = current.left;
            } else {
                current = stack.pop();
                set.add(current.key);
                current = current.right;
            }
        }
        return set;
    }

    @Override
    public V put(K key, V value) {
        if (key == null) {
            throw new NullPointerException();
        }
        if (root == null) {
            root = new Node(key, value, null);
            size++;
            return null;
        }
        return putHelper(root, key, value);
    }

    private V putHelper(Node node, K key, V value) {
        Comparable<? super K> k = (Comparable<? super K>) key;
        int compare = k.compareTo(node.key);
            if (compare < 0) {
                if (node.left == null) {
                    node.left = new Node(key, value, node);
                    size++;
                    return null;
                } else {
                    return putHelper(node.left, key, value);
                }

            }
            if (compare > 0) {
                if (node.right == null) {
                    node.right = new Node(key, value, node);
                    size++;
                    return null;
                } else {
                    return putHelper(node.right, key, value);
                }
            }
        V old = node.value;
        node.value = value;
        return old;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        for (Map.Entry<? extends K, ? extends V> entry: map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public V remove(Object key) {
        Node node = findNode(key);
        if (node == null) {
            return null;

        }

        V old = node.value;
        if (node.left == null && node.right == null) {
            if (equals(root, node)) {
                //TreeMap gets empty
                clear();
                return old;
            } else {
                replaceInParent(node, null);
            }
        } else if (node.left != null && node.right == null) {
            if (equals(root, node)) {
                node.left.parent = null;
                root = node.left;
            } else {
                replaceInParent(node, node.left);
            }
        } else if (node.left == null) {
            if (equals(node, root)) {
                node.right.parent = null;
                root = node.right;
            } else {
                replaceInParent(node, node.right);
            }
        } else {
            //both children are here
            Node min = findMinNode(node.right);
            //substitute the node with min
            min.parent.left = min.right;
            min.right.parent = min.parent;
            node.right.parent = min;
            node.left.parent = min;
            min.left = node.left;
            min.right = node.right;
            if (equals(node, root)) {
                root = min;
            }

        }
        size--;
        return old;
    }

    private void replaceInParent(Node node, Node replacingNode){
        if (equals(node, node.parent.left)) {
            node.parent.left = replacingNode;
            if (replacingNode != null)
                replacingNode.parent = node.parent;
        } else {
            node.parent.right = replacingNode;
            if (replacingNode != null)
                replacingNode.parent = node.parent;
        }
    }

    /**
     * Find minimum node in a subtree starting from node
     * @param node
     * @return minimum node under the node subtree
     */
    private Node findMinNode(Node node){
        if (node.left != null) {
            return findMinNode(node.left);
        } else {
            return node;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Collection<V> values() {
        Set<V> set = new HashSet<V>();
        Deque<Node> stack = new LinkedList<Node>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            if (node == null) continue;
            set.add(node.value);
            stack.push(node.left);
            stack.push(node.right);
        }
        return set;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        Map<String, Integer> map = new MyTreeMap<String, Integer>();
        map.put("Word1", 1);
        map.put("Word2", 2);
        Integer value = map.get("Word1");
        System.out.println(value);

        for (String key: map.keySet()) {
            System.out.println(key + ", " + map.get(key));
        }
    }

    /**
     * Makes a node.
     *
     * This is only here for testing purposes.  Should not be used otherwise.
     *
     * @param key
     * @param value
     * @return
     */
    public MyTreeMap<K, V>.Node makeNode(K key, V value, Node parent) {
        return new Node(key, value, parent);
    }

    /**
     * Sets the instance variables.
     *
     * This is only here for testing purposes.  Should not be used otherwise.
     *
     * @param node
     * @param size
     */
    public void setTree(Node node, int size ) {
        this.root = node;
        this.size = size;
    }

    /**
     * Returns the height of the tree.
     *
     * This is only here for testing purposes.  Should not be used otherwise.
     *
     * @return
     */
    public int height() {
        return heightHelper(root);
    }

    private int heightHelper(Node node) {
        if (node == null) {
            return 0;
        }
        int left = heightHelper(node.left);
        int right = heightHelper(node.right);
        return Math.max(left, right) + 1;
    }
}
