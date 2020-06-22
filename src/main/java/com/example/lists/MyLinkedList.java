package com.example.lists;

import java.util.*;

/**
 * @author downey
 * @param <E>
 *
 */
public class MyLinkedList<E> implements List<E> {

    /**
     * Node is identical to ListNode from the example, but parameterized with T
     *
     * @author downey
     *
     */
    private static class Node<E> {
        public E data;
        public Node<E> next;

        public Node(E data) {
            this.data = data;
            this.next = null;
        }
        @SuppressWarnings("unused")
        public Node(E data, Node next) {
            this.data = data;
            this.next = next;
        }
        public String toString() {
            return "Node(" + data.toString() + ")";
        }
    }

    private int size;            // keeps track of the number of elements
    private Node head;           // reference to the first node

    /**
     *
     */
    public MyLinkedList() {
        head = null;
        size = 0;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // run a few simple tests
        List<Integer> mll = new MyLinkedList<Integer>();
        mll.add(1);
        mll.add(2);
        mll.add(3);
        System.out.println(Arrays.toString(mll.toArray()) + " size = " + mll.size());

        mll.remove(new Integer(2));
        System.out.println(Arrays.toString(mll.toArray()) + " size = " + mll.size());
    }

    @Override
    public boolean add(E element) {
        if (head == null) {
            head = new Node(element);
        } else {
            Node node = head;
            // loop until the last node
            for ( ; node.next != null; node = node.next) {}
            node.next = new Node(element);
        }
        size++;
        return true;
    }

    @Override
    public void add(int index, E element) {
        //bounds are checked in getNode(index)
        if (index == 0) {
            head = new Node(element, head);
        } else {
            Node before = getNode(index - 1);
            before.next = new Node(element, before.next);
        }
        size++;
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        boolean flag = true;
        for (E element: collection) {
            flag &= add(element);
        }
        return flag;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        head = null;
        size = 0;
    }

    @Override
    public boolean contains(Object obj) {
        return indexOf(obj) != -1;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        for (Object obj: collection) {
            if (!contains(obj)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public E get(int index) {
        Node<E> node = getNode(index);
        return node.data;
    }

    /** Returns the node at the given index.
     * @param index
     * @return
     */
    private Node<E> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node node = head;
        for (int i=0; i<index; i++) {
            node = node.next;
        }
        return node;
    }

    @Override
    public int indexOf(Object target) {
        Node node = head;
        for (int index = 0; index < size; index++){
            if (equals(node.data, target))
                return index;
            node = node.next;
        }
        return -1;
    }

    /** Checks whether an element of the array is the target.
     *
     * Handles the special case that the target is null.
     *
     * @param target
     * @param element
     */
    private boolean equals(Object target, Object element) {
        if (target == null) {
            return element == null;
        } else {
            return target.equals(element);
        }
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        E[] array = (E[]) toArray();
        return Arrays.asList(array).iterator();
    }

    @Override
    public int lastIndexOf(Object target) {
        Node node = head;
        int index = -1;
        for (int i=0; i<size; i++) {
            if (equals(target, node.data)) {
                index = i;
            }
            node = node.next;
        }
        return index;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public boolean remove(Object obj) {
        int index = indexOf(obj);
        if (index == -1) {
            return false;
        }
        remove(index);
        return true;
    }

    @Override
    public E remove(int index) {
        //boundaries checked in getNode
        E element = get(index);
        if (index == 0) {
            head = head.next;
        } else {
            Node before = getNode(index-1);
            before.next = before.next.next;
        }
        size--;
        return element;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        boolean flag = true;
        for (Object obj: collection) {
            flag &= remove(obj);
        }
        return flag;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E set(int index, E element) {
        Node<E> node = getNode(index);
        E old = node.data;
        node.data = element;
        return old;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex >= size || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException();
        }

        List<E> list = new MyLinkedList<E>();
        List<E> arrayList = new MyArrayList<>();
        Node<E> node = head;
        //linear
        for (int i = 0; i <= toIndex; i++) {
            if (i >= fromIndex) {
                //constant
                arrayList.add(node.data);
            }
            node = node.next;
        }
        //linear
        for (int j = arrayList.size()-1; j>=0; j--){
            //constant
            list.add(0, arrayList.get(j));
        }
        return list;
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        int i = 0;
        for (Node node=head; node != null; node = node.next) {
            // System.out.println(node);
            array[i] = node.data;
            i++;
        }
        return array;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }
}