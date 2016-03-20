package com.dingup.onlinetest.controller;

import com.dingup.onlinetest.bean.reading.TsReadingArticle;
import com.dingup.onlinetest.bean.reading.TsReadingQuestion;
import com.dingup.onlinetest.dao.TsReadingArticleDAO;
import com.dingup.onlinetest.dao.TsReadingQuestionDAO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by yanggavin on 16/3/3.
 */
@Controller
@RequestMapping("/")
public class HomeController {
    private Logger logger = Logger.getLogger(HomeController.class);
    @Autowired
    private TsReadingArticleDAO tsReadingArticleDAO;
    @Autowired
    private TsReadingQuestionDAO tsReadingQuestionDAO;

    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap model){
        String subjectName = "test01";
        int articleNum = 1;
        TsReadingArticle ta = tsReadingArticleDAO.get(subjectName, articleNum);
        List<TsReadingQuestion> tqList = tsReadingQuestionDAO.getQuestions(subjectName, articleNum);
        String questionXml = tqList.get(0).getQuestionXml().toString();
        String questionJson = tqList.get(0).getQuestionXml().toJsonString();
        logger.debug("ygl-debug");
        logger.info("ygl-info.");
        logger.warn("ygl-warn");
        logger.error("ygl-error");
        model.addAttribute("msg", "Hello World!");
        return "index";
    }
}
