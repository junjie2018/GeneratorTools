package com.example.generator.tools.generator.disposer;

import com.example.generator.tools.database.domain.Column;
import com.example.generator.tools.generator.domain.Type;

public class PGTypeDisposer implements TypeDisposer {

    @Override
    public boolean isInternal(Column column) {
        return column.getType().equals("jsonb") || column.getType().equals("json");
    }

    @Override
    public Type getType(Column column) {
        switch (column.getType()) {
            case "varchar":
                return Type.STRING;
            case "jsonb":
            case "json":
                return Type.OBJECT;
            case "int2":
            case "int4":
            case "int8":
            case "numeric":
                return Type.LONG;
            case "date":
            case "timestamp":
            case "timestamptz":
                return Type.LOCAL_DATE_TIME;
            default:
                throw new RuntimeException("未准备的类型");
        }
    }
}
