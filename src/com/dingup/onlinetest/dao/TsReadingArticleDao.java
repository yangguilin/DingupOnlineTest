package com.dingup.onlinetest.dao;

import com.dingup.onlinetest.bean.reading.TsReadingArticle;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by yanggavin on 16/3/10.
 */
public interface TsReadingArticleDAO {
    boolean add(TsReadingArticle article);
    boolean update(TsReadingArticle article);

    @Delete("DELETE FROM ts_reading_articles WHERE id=#{0}")
    boolean delete(Integer id);

    @Select("SELECT * FROM ts_reading_articles WHERE subject_name=#{0} AND article_num=#{1}")
    @ResultMap("TsReadingArticleTableMap")
    TsReadingArticle get(String subjectName, Integer articleNum);

    @Select("SELECT * FROM ts_reading_articles WHERE id=#{0}")
    @ResultMap("TsReadingArticleTableMap")
    TsReadingArticle getById(Integer id);

    @Select("SELECT * FROM ts_reading_articles WHERE subject_name=#{0}")
    @ResultMap("TsReadingArticleTableMap")
    List<TsReadingArticle> getList(String subjectName);
}
