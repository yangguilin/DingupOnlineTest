package com.dingup.onlinetest.bean.reading;

import java.util.List;

/**
 * Created by yanggavin on 16/3/19.
 */
public class ReadingSummaryInfo {
    private Integer articleNum;
    private List<ReadingAnswerInfo> answerInfoList;

    public Integer getArticleNum() {
        return articleNum;
    }

    public void setArticleNum(Integer articleNum) {
        this.articleNum = articleNum;
    }

    public List<ReadingAnswerInfo> getAnswerInfoList() {
        return answerInfoList;
    }

    public void setAnswerInfoList(List<ReadingAnswerInfo> answerInfoList) {
        this.answerInfoList = answerInfoList;
    }
}
