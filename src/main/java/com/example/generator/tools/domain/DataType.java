package com.example.generator.tools.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum DataType {
    /**
     * varchar
     */
    VARCHAR(Collections.singletonList("varchar")),

    /**
     * int
     */
    INT(Arrays.asList("int2", "int4", "int8")),

    /**
     * numeric
     */
    NUMERIC(Collections.singletonList("numeric")),

    /**
     * jsonb
     */
    JSONB(Collections.singletonList("jsonb")),

    /**
     * date
     */
    DATE(Arrays.asList("date", "timestamp", "timestamptz")),
    ;

    @Getter
    private final List<String> typeNameFromJdbcTemplate;


    public static DataType convert(String typeName) {

        for (DataType columnType : DataType.values()) {
            if (columnType.getTypeNameFromJdbcTemplate().contains(typeName)) {
                return columnType;
            }
        }

        throw new RuntimeException("转换成DataType失败，未定义该类型：" + typeName);
    }
}
