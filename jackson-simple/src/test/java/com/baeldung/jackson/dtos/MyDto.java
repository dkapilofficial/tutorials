package com.baeldung.jackson.dtos;

public class MyDto {

    private String stringValue;
    private int intValue;
    private boolean booleanValue;

    public MyDto() {
        super();
    }

    public MyDto(final String stringValue, final int intValue, final boolean booleanValue) {
        super();

        this.stringValue = stringValue;
        this.intValue = intValue;
        this.booleanValue = booleanValue;
    }

    // API

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(final String stringValue) {
        this.stringValue = stringValue;
    }

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(final int intValue) {
        this.intValue = intValue;
    }

    public boolean isBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(final boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    //

    @Override
    public String toString() {
        return "MyDto [stringValue=" + stringValue + ", intValue=" + intValue + ", booleanValue=" + booleanValue + "]";
    }

}
