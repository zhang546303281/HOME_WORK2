package com.hw.demo.enums;

public enum Datasource {

    WRITE_DATASOURCE("0", "write"),

    READ_DATASOURCE("1", "read");

    private String name;

    private String value;

    Datasource(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
