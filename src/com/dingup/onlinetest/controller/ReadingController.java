package com.dingup.onlinetest.controller;

import com.dingup.onlinetest.bean.ExamReadingInfo;
import com.dingup.onlinetest.bean.ExaminationInfo;
import com.dingup.onlinetest.bean.TsReadingArticle;
import com.dingup.onlinetest.dao.TsReadingArticleDAO;
import com.dingup.onlinetest.enums.ExamProjectTypeEnum;
import com.dingup.onlinetest.enums.ExamTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yanggavin on 16/3/16.
 */
@Controller
@RequestMapping("/reading")
public class ReadingController {
    @Autowired
    private TsReadingArticleDAO tsReadingArticleDAO;

    @RequestMapping(method= RequestMethod.GET)
    public String index(ModelMap model){
        ExaminationInfo ei = new ExaminationInfo();
        ExamReadingInfo eri = new ExamReadingInfo();
        String subjectName = "test01";
        List<TsReadingArticle> articleList = tsReadingArticleDAO.getList(subjectName);
        Map<Integer, Integer> articleMap = new HashMap<>();
        articleMap.put(1, articleList.get(0).getId());
//        articleMap.put(2, articleList.get(1).getId());
//        articleMap.put(3, articleList.get(2).getId());
        eri.setArticleMap(articleMap);
        eri.setAnswerTime(0);
        eri.setCurProgress("1-1");
        ei.setExamReadingInfo(eri);
        ei.setId("ygl_201603161231");
        ei.setBeginTime((new Date()));
        ei.setEndTime(null);
        ei.setExamTypeEnum(ExamTypeEnum.PRACTICE);
        ei.setCurExamProjectType(ExamProjectTypeEnum.READING);

        model.addAttribute("examInfo", ei);

        return "reading";
    }
}
