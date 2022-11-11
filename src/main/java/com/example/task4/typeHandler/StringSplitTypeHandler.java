package com.example.task4.typeHandler;

import lombok.SneakyThrows;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StringSplitTypeHandler extends BaseTypeHandler<List<String>> {
    @Override
    @SneakyThrows
    public void setNonNullParameter(PreparedStatement ps, int i, List<String> parameter, JdbcType jdbcType) {
        Array array = ps.getConnection().createArrayOf("text", parameter.toArray());
        ps.setArray(i, array);
    }

    @Override
    @SneakyThrows
    public List<String> getNullableResult(ResultSet rs, String columnName) {
        return toList(rs.getArray(columnName));
    }

    @Override
    @SneakyThrows
    public List<String> getNullableResult(ResultSet rs, int columnIndex) {
        return toList(rs.getArray(columnIndex));
    }

    @Override
    @SneakyThrows
    public List<String> getNullableResult(CallableStatement cs, int columnIndex) {
        return toList(cs.getArray(columnIndex));
    }

    @SneakyThrows
    private List<String> toList(Array pgArray) {
        if (pgArray == null) return Collections.emptyList();

        String[] strings = (String[]) pgArray.getArray();
        return containsOnlyNulls(strings) ? new ArrayList<>() : List.of(strings);
    }

    private boolean containsOnlyNulls(String[] strings) {
        for (String s : strings) {
            if (s != null) {
                return false;
            }
        }
        return true;
    }
}
