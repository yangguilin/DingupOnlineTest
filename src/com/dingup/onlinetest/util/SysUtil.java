package com.dingup.onlinetest.util;

import com.dingup.onlinetest.enums.ExamTypeEnum;

/**
 * Created by yanggavin on 16/3/27.
 */
public class SysUtil {
    /**
     * 生成考试ID
     * 注:ID由四部分组成:type_username_date_random
     * @param type  考试类型:practice或mock
     * @param username  用户名
     * @return  考试ID
     */
    public static String genExamId(ExamTypeEnum type, String username){
        return String.format("%s_%s_%s_%s", type, username, DateUtil.getCurShortDateStr(), StrUtil.getRandomStr(4)).toLowerCase();
    }
}
