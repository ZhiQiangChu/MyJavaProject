package com.hz.yk;

import java.util.Map;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        String str="ab06e715bea9f99c0abcd3d3ac9a161401e8507448c3da0d17c455890551e2fe";
        String s="hello";
        s=null;
        testNotNull(null);
        System.out.println(str.hashCode());
    }

    static void testNotNull(Map from) {
        System.out.println(from.get("s"));

    }
}
