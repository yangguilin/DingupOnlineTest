package com.dingup.onlinetest.module;

import com.dingup.onlinetest.bean.ExaminationInfo;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by yanggavin on 16/3/16.
 */
public class SExaminationQueueCache {
    private static class LazyHolder {
        private static final SExaminationQueueCache INSTANCE = new SExaminationQueueCache();
    }
    public static SExaminationQueueCache getInstance() {
        return LazyHolder.INSTANCE;
    }
    private SExaminationQueueCache() { }

    /**
     * 考试总时长(秒)
     * 注:听说读写各一个小时
     */
    private final int EXAMINATION_TIME_MAX = 4 * 60 * 60;
    /**
     * 阅读考试时长(秒)
     */
    private final int EXAMINATION_READING_TIME_MAX = 60 * 60;
    /**
     * 队列清理时间(秒)
     * 注:每天清理一次
     */
    private final int QUEUE_RECYCLE_SECOND = 5000;
    /**
     * 进行中的考试队列
     */
    private final ConcurrentMap<String, ExaminationInfo> proceedingListMap = new ConcurrentHashMap<>();
    /**
     * 当天结束的考试队列
     */
    private final ConcurrentMap<String, ExaminationInfo> endListMap = new ConcurrentHashMap<>();

    /**
     * 添加新的考试记录
     * @param examInfo  考试信息
     */
    public void addItem(ExaminationInfo examInfo){
        if (!proceedingListMap.containsKey(examInfo.getId())){
            proceedingListMap.put(examInfo.getId(), examInfo);
        }
    }
}
