package com.dingup.onlinetest.dao;

import com.dingup.onlinetest.bean.TsReadingQuestion;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by yanggavin on 16/3/12.
 */
public interface TsReadingQuestionDAO {
    boolean add(TsReadingQuestion question);
    boolean update(TsReadingQuestion article);

    @Delete("DELETE FROM ts_reading_questions WHERE id=#{0}")
    boolean delete(int id);

    @Select("SELECT * FROM ts_reading_questions WHERE subject_name=#{0} and article_num=#{1}")
    @ResultMap("TsReadingQuestionTableMap")
    List<TsReadingQuestion> getQuestions(String subjectName, int articleNum);
}
