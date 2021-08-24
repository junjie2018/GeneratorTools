package com.example.generator.tools.utils;

public class AssertUtils {
    public static void assertNotNull(Object target, String msg) {
        if (target == null) {
            throw new RuntimeException(msg);
        }
    }

    public static void assertNotEqual(int length, int targetLength, String msg) {
        if (length != targetLength) {
            throw new RuntimeException(msg);
        }
    }
}
