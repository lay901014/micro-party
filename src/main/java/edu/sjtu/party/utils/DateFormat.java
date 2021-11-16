package edu.sjtu.party.utils;

public enum DateFormat {

    DATE("yyyy-MM-dd"),
    TIME("HH:mm:ss"),
    DATETIME("yyyy-MM-dd HH:mm:ss"),
    CHINESE_DATE_TIME("yyyy年MM月dd日 HH时mm分"),
    MONTH("yyyy-MM");

    private String value;

    private DateFormat(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }

}
