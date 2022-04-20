package com.example.generator.tools.generator.disposer;

import com.example.generator.tools.database.domain.Column;
import com.example.generator.tools.generator.domain.Type;

public class MariaTypeDisposer implements TypeDisposer {

    @Override
    public boolean isInternal(Column column) {
        return column.getType().equals("jsonb") || column.getType().equals("json");
    }

    @Override
    public Type getType(Column column) {
        switch (column.getType()) {
            case "BIGINT":
                return Type.LONG;
            case "VARCHAR":
            case "CHAR":
            case "MEDIUMTEXT":
                return Type.STRING;
            case "INT":
                return Type.INTEGER;
            case "TIMESTAMP":
                return Type.LOCAL_DATE_TIME;
            default:
                throw new RuntimeException("未准备的类型：" + column.getType());
        }
    }
}
