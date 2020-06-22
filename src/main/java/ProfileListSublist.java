import java.util.List;

public class ProfileListSublist {

    /**
     * @param args
     */
    public static void main(String[] args) {
        profileMyArraListSublist();
    }

    /**
     * Characterize the run time of adding to the end of an ArrayList
     */
    public static void profileMyArraListSublist() {
        Timeable timeable = new Timeable() {
            List<String> list;

            public void setup(int n) {
                list = new MyLinkedList<>();
                for (int i=0; i<n; i++) {
                    list.add("a string");
                }
            }

            public void timeMe(int n) {
                for (int i=0; i<n; i++) {
                    list.subList(0, n-1);
                }
            }
        };
        int startN = 10;
        int endMillis = 10000;
        ProfileListAdd.runProfiler("ArrayList add end", timeable, startN, endMillis);
    }
}
