package com.baeldung.java_8_features;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Alex Vengrov
 */
public class Detail {

    private static final List<String> PARTS = Arrays.asList("turbine", "pump");

    public List<String> getParts() {
        return PARTS;
    }
}
