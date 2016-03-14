package com.dingup.onlinetest.dao.handler;

import com.dingup.onlinetest.enums.ReadingQuestionTypeEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by yanggavin on 16/3/12.
 */
public class ReadingQuestionTypeEnumHandler extends BaseTypeHandler<ReadingQuestionTypeEnum> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, ReadingQuestionTypeEnum readingQuestionTypeEnum, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, readingQuestionTypeEnum.getCode());
    }

    @Override
    public ReadingQuestionTypeEnum getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return (resultSet.wasNull() ? null : getEnumByCode(resultSet.getString(s)));
    }

    @Override
    public ReadingQuestionTypeEnum getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return (resultSet.wasNull() ? null : getEnumByCode(resultSet.getString(i)));
    }

    @Override
    public ReadingQuestionTypeEnum getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return (callableStatement.wasNull() ? null : getEnumByCode(callableStatement.getString(i)));
    }

    private ReadingQuestionTypeEnum getEnumByCode(String code){
        for (ReadingQuestionTypeEnum em : ReadingQuestionTypeEnum.class.getEnumConstants()){
            if (em.getCode().equalsIgnoreCase(code)){
                return em;
            }
        }
        return null;
    }
}
