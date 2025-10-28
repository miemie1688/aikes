package com.mindskip.xzs.utility.mybatis;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.postgresql.util.PGobject;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

// 假设你的Java字段类型是 Map<String, Object>
public class JsonbTypeHandler extends BaseTypeHandler<Map<String, Object>> {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Map<String, Object> parameter, JdbcType jdbcType) throws SQLException {
        try {
            // 将 Map 对象转换为 JSON 字符串
            String json = MAPPER.writeValueAsString(parameter);

            // 创建 PGobject，设置 type 为 jsonb
            PGobject jsonObject = new PGobject();
            jsonObject.setType("jsonb");
            jsonObject.setValue(json);

            ps.setObject(i, jsonObject);
        } catch (Exception e) {
            throw new SQLException("Error setting JSONB parameter: " + e.getMessage(), e);
        }
    }

    @Override
    public Map<String, Object> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        try {
            String json = rs.getString(columnName);
            if (json != null) {
                // 将 JSON 字符串解析回 Map 对象
                return MAPPER.readValue(json, Map.class);
            }
            return null;
        } catch (Exception e) {
            throw new SQLException("Error getting JSONB result: " + e.getMessage(), e);
        }
    }

    @Override
    public Map<String, Object> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        // 实现逻辑与上一个方法相同
        return getNullableResult(rs, rs.getMetaData().getColumnName(columnIndex));
    }

    @Override
    public Map<String, Object> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        // 实际应用中可能需要更复杂的处理，这里简化处理
        return null;
    }
}