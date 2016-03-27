package com.dingup.onlinetest.controller;

import com.dingup.onlinetest.bean.ExaminationInfo;
import com.dingup.onlinetest.bean.HttpResContent;
import com.dingup.onlinetest.bean.reading.*;
import com.dingup.onlinetest.dao.TsReadingArticleDAO;
import com.dingup.onlinetest.dao.TsReadingQuestionDAO;
import com.dingup.onlinetest.enums.ExamProjectTypeEnum;
import com.dingup.onlinetest.enums.ExamTypeEnum;
import com.dingup.onlinetest.module.SExaminationQueueCache;
import com.dingup.onlinetest.util.HttpResUtil;
import com.dingup.onlinetest.util.SysUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by yanggavin on 16/3/16.
 */
@Controller
@RequestMapping("/reading")
public class ReadingController {
    private final String REQUEST_PARAMETER_SUBJECT_NAME = "subject_name";
    private final String REQUEST_PARAMETER_ARTICLE_NUM = "article_num";
    private final String REQUEST_PARAMETER_QUESTION_NUM = "question_num";
    private final String REQUEST_PARAMETER_EXAMINATION_ID = "examination_id";
    private final String REQUEST_PARAMETER_ANSWER = "answer";
    private Logger logger = Logger.getLogger(ReadingController.class);
    @Autowired
    private TsReadingArticleDAO tsReadingArticleDAO;
    @Autowired
    private TsReadingQuestionDAO tsReadingQuestionDAO;


    @RequestMapping(method= RequestMethod.GET)
    public String index(){
        return "reading/reading";
    }

    @RequestMapping(value = "/summary", method = RequestMethod.GET)
    public String summary(){
        return "reading/summary";
    }

    /**
     * 获取阅读文章
     * @param subjectName   套题名称
     * @param articleNum    文章序号
     * @param response  response
     */
    @RequestMapping(value = "/get_article.do", method=RequestMethod.GET)
    public void getArticle(@RequestParam(REQUEST_PARAMETER_SUBJECT_NAME) String subjectName,
                           @RequestParam(REQUEST_PARAMETER_ARTICLE_NUM) Integer articleNum,
                           HttpServletRequest request,
                           HttpServletResponse response)
    {
        HttpResContent<TsReadingArticle> hrc = new HttpResContent<>();
        try {
            if (subjectName == null || articleNum == null || subjectName.isEmpty() || articleNum <= 0) {
                throw new IllegalArgumentException(String.format("parameter error/subject_name:%s/article_num:%s", subjectName, articleNum));
            }
            TsReadingArticle article = tsReadingArticleDAO.get(subjectName, articleNum);
            if (article == null) {
                throw new RuntimeException("tsReadingArticleDAO get article fail.");
            }
            hrc.fillData(200, "已获取文章信息.", article);
        } catch (Exception e){
            logger.error(e.getMessage());
            hrc.fillData(501, "获取文章信息失败.", null);
        } finally {
            try {
                HttpResUtil.writeResContent(hrc, request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取问题
     * @param subjectName   套题名称
     * @param articleNum    文章序号
     * @param questionNum   问题序号
     * @param response  response
     */
    @RequestMapping(value = "/get_question.do", method = RequestMethod.GET)
    public void getQuestion(@RequestParam(REQUEST_PARAMETER_SUBJECT_NAME) String subjectName,
                            @RequestParam(REQUEST_PARAMETER_ARTICLE_NUM) Integer articleNum,
                            @RequestParam(REQUEST_PARAMETER_QUESTION_NUM) Integer questionNum,
                            HttpServletRequest request,
                            HttpServletResponse response)
    {
        HttpResContent<TsReadingQuestion> hrc = new HttpResContent<>();
        try{
            if (subjectName == null || articleNum == null || questionNum == null
                    || subjectName.isEmpty() || articleNum <= 0 || questionNum <= 0){
                throw new IllegalArgumentException(String.format("parameter error/subject_name:%s/article_num:%s/question_num:%s", subjectName, articleNum, questionNum));
            }
            TsReadingQuestion question = tsReadingQuestionDAO.getQuestion(subjectName, articleNum, questionNum);
            if (question == null){
                throw new RuntimeException("tsReadingQuestionDAO get question fail.");
            }
            hrc.fillData(200, "已获取题目信息.", question);
        } catch (Exception e){
            logger.error(e.getMessage());
            hrc.fillData(501, "获取题目信息失败.", null);
        } finally {
            try {
                HttpResUtil.writeResContent(hrc, request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取问题列表
     * @param subjectName   套题名称
     * @param articleNum    文章序号
     * @param response  response
     */
    @RequestMapping(value = "/get_questions.do", method = RequestMethod.GET)
    public void getQuestions(@RequestParam(REQUEST_PARAMETER_SUBJECT_NAME) String subjectName,
                             @RequestParam(REQUEST_PARAMETER_ARTICLE_NUM) Integer articleNum,
                             HttpServletRequest request,
                             HttpServletResponse response)
    {
        HttpResContent<List<TsReadingQuestion>> hrc = new HttpResContent<>();
        try{
            if (subjectName == null || articleNum == null || subjectName.isEmpty() || articleNum <= 0){
                throw new IllegalArgumentException(String.format("parameter error/subject_name:%s/article_num:%s", subjectName, articleNum));
            }
            List<TsReadingQuestion> questionList = tsReadingQuestionDAO.getQuestions(subjectName, articleNum);
            if (questionList == null || questionList.size() == 0){
                throw new RuntimeException("tsReadingQuestionDAO get question fail.");
            }
            hrc.fillData(200, "已获取题目列表信息.", questionList);
        } catch (Exception e){
            logger.error(e.getMessage());
            hrc.fillData(501, "获取题目列表信息失败.", null);
        } finally {
            try {
                HttpResUtil.writeResContent(hrc, request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 保存某一问题答案
     * @param examinationId 考试ID
     * @param subjectName   套题名称
     * @param articleNum    文章序号
     * @param questionNum   问题序号
     * @param answer    答案:a,b
     * @param response  response
     */
    @RequestMapping(value = "/save_answer.do", method = RequestMethod.GET)
    public void saveAnswer(@RequestParam(REQUEST_PARAMETER_EXAMINATION_ID) String examinationId,
                           @RequestParam(REQUEST_PARAMETER_SUBJECT_NAME) String subjectName,
                           @RequestParam(REQUEST_PARAMETER_ARTICLE_NUM) Integer articleNum,
                           @RequestParam(REQUEST_PARAMETER_QUESTION_NUM) Integer questionNum,
                           @RequestParam(REQUEST_PARAMETER_ANSWER) String answer,
                           HttpServletRequest request,
                           HttpServletResponse response)
    {
        HttpResContent<Boolean> hrc = new HttpResContent<>();
        try {
            if (examinationId == null || subjectName == null ||
                    articleNum == null || questionNum == null ||
                    examinationId.isEmpty() || subjectName.isEmpty() ||
                    articleNum <= 0 || questionNum <= 0)
            {
                throw new IllegalArgumentException(
                        String.format("parameter error/examinationId:%s/subject_name:%s/article_num:%s/question_num:%s",
                                examinationId, subjectName, articleNum, questionNum)
                );
            }
            ExamReadingInfo eri = SExaminationQueueCache.getInstance().getExamReadingInfo(examinationId);
            if (eri.getAnswerMap().get(articleNum).containsKey(questionNum)){
                eri.getAnswerMap().get(articleNum).put(questionNum, answer);
            }
            hrc.fillData(200, "答案保存成功", true);
        } catch (Exception e){
            logger.error(e.getMessage());
            hrc.fillData(501, "答案保存失败", false);
        } finally {
            try {
                HttpResUtil.writeResContent(hrc, request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取答题统计信息
     * @param examinationId 考试ID
     * @param response  response
     */
    @RequestMapping(value = "/get_summary_info.do", method = RequestMethod.GET)
    public void getSummaryInfo(@RequestParam(REQUEST_PARAMETER_EXAMINATION_ID) String examinationId,
                               HttpServletRequest request,
                               HttpServletResponse response){
        HttpResContent<List<ReadingSummaryInfo>> hrc = new HttpResContent<>();
        try {
            ExamReadingInfo eri = SExaminationQueueCache.getInstance().getExamReadingInfo(examinationId);
            if (eri == null){
                throw new RuntimeException("Can't find reading part by examination id:" + examinationId);
            }
            List<ReadingSummaryInfo> readingSummaryInfoList = new ArrayList<>();
            for (Map.Entry<Integer, Integer> entry : eri.getArticleMap().entrySet()){
                ReadingSummaryInfo rsi = new ReadingSummaryInfo();
                rsi.setArticleNum(entry.getKey());
                TsReadingArticle tra = tsReadingArticleDAO.getById(entry.getValue());
                List<ReadingAnswerInfo> answerInfoList = new ArrayList<>();
                if (tra != null) {
                    List<TsReadingQuestion> questionList = tsReadingQuestionDAO.getQuestions(tra.getSubjectName(), entry.getKey());
                    for (Map.Entry<Integer, String> answer : eri.getAnswerMap().get(entry.getKey()).entrySet()) {
                        ReadingAnswerInfo rai = new ReadingAnswerInfo();
                        rai.setQuestionNum(answer.getKey());
                        rai.setStudentAnswer(answer.getValue());
                        for (TsReadingQuestion trq : questionList){
                            if (trq.getQuestionNum() == answer.getKey()){
                                if (trq.getQuestionXml() != null) {
                                    rai.setQuestionTitle(trq.getQuestionXml().getTitle());
                                    rai.setCorrectAnswer(StringUtils.join(trq.getQuestionXml().getAnswer(), ","));
                                }
                                break;
                            }
                        }
                        answerInfoList.add(rai);
                    }
                }
                rsi.setAnswerInfoList(answerInfoList);
                readingSummaryInfoList.add(rsi);
            }
            hrc.fillData(200, "已获取答题统计信息", readingSummaryInfoList);
        } catch (Exception e){
            logger.error(e.getMessage());
            hrc.fillData(501, "获取答题统计信息失败", null);
        } finally {
            try {
                HttpResUtil.writeResContent(hrc, request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 开始阅读考试(本来应该是整个考试开始调用入口,目前仅有阅读部分,所以先放这里,稍后移走)
     * @param subjectName   套题名称
     * @param response  response
     */
    @RequestMapping(value = "/start_exam.do", method = RequestMethod.GET)
    public void startExam(@RequestParam(REQUEST_PARAMETER_SUBJECT_NAME) String subjectName,
                          HttpServletRequest request,
                          HttpServletResponse response){
        HttpResContent<String> hrc = new HttpResContent<>();
        try {
            if (subjectName == null || subjectName.isEmpty()) {
                throw new IllegalArgumentException(String.format("parameter error/subject_name:%s", subjectName));
            }
            ExaminationInfo ei = new ExaminationInfo();
            ei.setId(SysUtil.genExamId(ExamTypeEnum.PRACTICE, "def"));
            ei.setUserId("def");
            ei.setExamTypeEnum(ExamTypeEnum.PRACTICE);
            ei.setCurExamProjectType(ExamProjectTypeEnum.READING);
            ei.setBeginTime(new Date());
            ei.setEndTime(null);

            // init reading part article and answer map.
            Map<Integer, Integer> articleMap = new HashMap<>();
            Map<Integer, Map<Integer, String>> answerMap = new HashMap<>();
            List<TsReadingArticle> articles = tsReadingArticleDAO.getList(subjectName);
            for (TsReadingArticle article : articles) {
                articleMap.put(article.getArticleNum(), article.getId());
                List<TsReadingQuestion> questions = tsReadingQuestionDAO.getQuestions(subjectName, article.getArticleNum());
                Map<Integer, String> map = new HashMap<>();
                for (TsReadingQuestion question : questions) {
                    map.put(question.getQuestionNum(), "");
                }
                answerMap.put(article.getArticleNum(), map);
            }

            ExamReadingInfo eri = new ExamReadingInfo();
            eri.setCurProgress("1-1"); // default the first article and first question.
            eri.setAnswerTime(0);
            eri.setArticleMap(articleMap);
            eri.setAnswerMap(answerMap);
            ei.setExamReadingInfo(eri);
            // add to global examination queue.
            SExaminationQueueCache.getInstance().addItem(ei);
            hrc.fillData(200, "初始化考试实例成功,可以开始考试.", ei.getId());
        } catch (Exception e){
            logger.error(e.getMessage());
            hrc.fillData(501, "初始化考试实例失败.", "");
        } finally {
            try {
                HttpResUtil.writeResContent(hrc, request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
