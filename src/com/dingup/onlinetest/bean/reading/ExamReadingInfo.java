package com.dingup.onlinetest.bean.reading;

import java.util.Map;

/**
 * Created by yanggavin on 16/3/16.
 */
public class ExamReadingInfo {
    /**
     * 当前进度
     * 注:"文章号-题号"格式字符串
     */
    private String curProgress;
    /**
     * 答题时间(秒)
     */
    private Integer answerTime;
    /**
     * 考题map
     * 注:key为article的序号, value为article的id
     */
    private Map<Integer, Integer> articleMap;
    /**
     * 答题结果
     * 注:第一层Integer为article的id,第二层Integer为question的num
     */
    private Map<Integer, Map<Integer, String>> answerMap;

    public String getCurProgress() {
        return curProgress;
    }

    public void setCurProgress(String curProgress) {
        this.curProgress = curProgress;
    }

    public Integer getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(Integer answerTime) {
        this.answerTime = answerTime;
    }

    public Map<Integer, Integer> getArticleMap() {
        return articleMap;
    }

    public void setArticleMap(Map<Integer, Integer> articleMap) {
        this.articleMap = articleMap;
    }

    public Map<Integer, Map<Integer, String>> getAnswerMap() {
        return answerMap;
    }

    public void setAnswerMap(Map<Integer, Map<Integer, String>> answerMap) {
        this.answerMap = answerMap;
    }
}
