package com.dingup.onlinetest.bean;

import com.dingup.onlinetest.bean.reading.ExamReadingInfo;
import com.dingup.onlinetest.enums.ExamProjectTypeEnum;
import com.dingup.onlinetest.enums.ExamTypeEnum;

import java.util.Date;

/**
 * Created by yanggavin on 16/3/16.
 */
public class ExaminationInfo {
    private String id;
    private ExamTypeEnum examTypeEnum;
    private String userId;
    private Date beginTime;
    private Date endTime;
    // 当前考试项目类型:听说读写
    private ExamProjectTypeEnum curExamProjectType;
    private ExamReadingInfo examReadingInfo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ExamTypeEnum getExamTypeEnum() {
        return examTypeEnum;
    }

    public void setExamTypeEnum(ExamTypeEnum examTypeEnum) {
        this.examTypeEnum = examTypeEnum;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public ExamProjectTypeEnum getCurExamProjectType() {
        return curExamProjectType;
    }

    public void setCurExamProjectType(ExamProjectTypeEnum curExamProjectType) {
        this.curExamProjectType = curExamProjectType;
    }

    public ExamReadingInfo getExamReadingInfo() {
        return examReadingInfo;
    }

    public void setExamReadingInfo(ExamReadingInfo examReadingInfo) {
        this.examReadingInfo = examReadingInfo;
    }
}
