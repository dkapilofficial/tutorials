package org.baeldung.gson.primitives.models;

public class PrimitiveBundleInitialized {
    // @formatter:off
    public byte byteValue       = (byte) 1;
    public short shortValue     = (short) 1;
    public int intValue         = 1;
    public long longValue       = 1L;
    public float floatValue     = 1.0f;
    public double doubleValue   = 1;
    public boolean booleanValue = true;
    public char charValue       = 'a';
    // @formatter:on

    public String toString() {
        return "{" + "byte: " + byteValue + ", " + "short: " + shortValue + ", "
            + "int: " + intValue + ", " + "long: " + longValue + ", "
            + "float: " + floatValue + ", " + "double: " + doubleValue + ", "
            + "boolean: " + booleanValue + ", " + "char: " + charValue + "}";
    }
}
