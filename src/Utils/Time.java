package Utils;

/**
 * Created by aleks on 22.06.2017.
 */
public class Time {
    public static long second  = 1000000000l;
    public static long getTime() {
        return System.nanoTime();
    }
}
