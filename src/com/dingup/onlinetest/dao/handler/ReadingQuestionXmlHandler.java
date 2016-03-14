package com.dingup.onlinetest.dao.handler;

import com.dingup.onlinetest.bean.ReadingQuestionXml;
import com.dingup.onlinetest.util.XmlUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by yanggavin on 16/3/12.
 */
public class ReadingQuestionXmlHandler extends BaseTypeHandler<ReadingQuestionXml> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, ReadingQuestionXml readingQuestionXml, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, readingQuestionXml.toString());
    }

    @Override
    public ReadingQuestionXml getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return (resultSet.wasNull() ? null : XmlUtil.parseReadingQuestionXmlContent(resultSet.getString(s)));
    }

    @Override
    public ReadingQuestionXml getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return (resultSet.wasNull() ? null : XmlUtil.parseReadingQuestionXmlContent(resultSet.getString(i)));
    }

    @Override
    public ReadingQuestionXml getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return (callableStatement.wasNull() ? null : XmlUtil.parseReadingQuestionXmlContent(callableStatement.getString(i)));
    }
}
