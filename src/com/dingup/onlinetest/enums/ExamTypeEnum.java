package com.dingup.onlinetest.enums;

/**
 * Created by yanggavin on 16/3/16.
 */
public enum ExamTypeEnum {
    PRACTICE("practice", "练习"),
    MOCK("mock", "模考");

    private final String value;
    private final String des;

    ExamTypeEnum(String value, String des) {
        this.value = value;
        this.des = des;
    }

    public String getValue() {
        return value;
    }

    public String getDes() {
        return des;
    }
}
