package com.dingup.onlinetest.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by yanggavin on 16/3/10.
 */
public class TsReadingArticle implements Serializable {
    private int id;
    private String subjectName;
    private int articleNum;
    private String articleTitle;
    private String articleContent;
    private String articleChContent;
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

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public String getArticleChContent() {
        return articleChContent;
    }

    public void setArticleChContent(String articleChContent) {
        this.articleChContent = articleChContent;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
