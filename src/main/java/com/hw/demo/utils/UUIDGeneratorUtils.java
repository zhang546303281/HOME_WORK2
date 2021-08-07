package com.hw.demo.utils;

import java.util.UUID;

public class UUIDGeneratorUtils {

    private UUIDGeneratorUtils() {

    }

    public static String getUUID32() {
        String s = UUID.randomUUID().toString();
        return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18)
                + s.substring(19, 23) + s.substring(24, 36);
    }
}
