package com.dingup.onlinetest.enums;

/**
 * Created by yanggavin on 16/3/12.
 */
public enum ReadingQuestionTypeEnum {
    ARROW("arrow", "段首箭头提示"),
    LIGHT("light", "高亮显示"),
    INSERT("insert", "断句插入"),
    TABLE("table", "多选题"),
    MULTIPLE("multiple", "多选题"),
    SUMMARY("summary", "总结题");

    private final String code;
    private final String description;

    ReadingQuestionTypeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
