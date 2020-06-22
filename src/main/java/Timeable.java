public interface Timeable {
    /*
     * setup is invoked before the clock starts.
     */
    void setup(int n);

    /*
     * timeMe does whatever operation we are timing.
     */
    void timeMe(int n);
}
