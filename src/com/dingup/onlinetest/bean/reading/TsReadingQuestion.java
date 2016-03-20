package com.dingup.onlinetest.bean.reading;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by yanggavin on 16/3/10.
 */
public class TsReadingQuestion implements Serializable {
    private int id;
    private String subjectName;
    private int articleNum;
    private int questionNum;
    private int paragraphNum;
    private ReadingQuestionXml questionXml;
    private Date createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getArticleNum() {
        return articleNum;
    }

    public void setArticleNum(int articleNum) {
        this.articleNum = articleNum;
    }

    public int getQuestionNum() {
        return questionNum;
    }

    public void setQuestionNum(int questionNum) {
        this.questionNum = questionNum;
    }

    public int getParagraphNum() {
        return paragraphNum;
    }

    public void setParagraphNum(int paragraphNum) {
        this.paragraphNum = paragraphNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public ReadingQuestionXml getQuestionXml() {
        return questionXml;
    }

    public void setQuestionXml(ReadingQuestionXml questionXml) {
        this.questionXml = questionXml;
    }
}
