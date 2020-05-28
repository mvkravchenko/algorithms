import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.fail;

public class MyArrayListTest extends MyListAbstractTest<MyArrayList> {

    @Override
    protected MyArrayList createMylistInstance() {
        return new MyArrayList<Integer>();
    }
}
