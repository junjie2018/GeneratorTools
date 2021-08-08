package com.example.generator.tools.constant;

import java.util.regex.Pattern;

public class SystemConstant {

    public static final Pattern FRAGMENT_PATTERN;

    public static final String PG_FLAG_TABLE_NAME = "table_name";
    public static final String PG_FLAG_COLUMN_NAME = "column_name";
    public static final String PG_FLAG_TYPE_NAME = "type_name";
    public static final String PG_FLAG_REMARKS = "remarks";

    public static final String PG_TYPES_TABLE = "TABLE";

    public static final String JAVA_TYPE_INTEGER = "Integer";
    public static final String JAVA_TYPE_STRING = "String";
    public static final String JAVA_TYPE_DATE = "LocalDateTime";
    public static final String JAVA_TYPE_JSON = "JSONObject";

    static {

        FRAGMENT_PATTERN = Pattern.compile(
                "<@fragment name=\"([A-Za-z0-9-_]+)\">(.*?)</@fragment>",
                Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    }
}
