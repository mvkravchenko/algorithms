import org.junit.Before;

import java.util.ArrayList;

public class MyLinkedListTest extends MyListAbstractTest<MyLinkedList> {


    @Override
    protected MyLinkedList createMylistInstance() {
        return new MyLinkedList<Integer>();
    }
}