package edu.sjtu.party.utils;

public class ObjectUtil {

    public static int compareTo(Comparable... objects) {
        if (objects == null) throw new NullPointerException();
        if (objects.length == 0 || objects.length % 2 == 1) throw new IllegalArgumentException();
        for (int i = 0; i < objects.length; i += 2) {
            Comparable c1 = objects[i];
            Comparable c2 = objects[i + 1];
            if (c1 == c2) continue;
            if (c1 == null) {
                return -1;
            }
            if (c2 == null) {
                return 1;
            }
            int r = c1.compareTo(c2);
            if (r == 0) continue;
            return r;
        }
        return 0;
    }

}
