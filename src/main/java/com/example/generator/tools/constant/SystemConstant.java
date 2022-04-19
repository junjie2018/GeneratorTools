package com.example.generator.tools.constant;

import java.util.regex.Pattern;

public class SystemConstant {

    public static final Pattern FRAGMENT_PATTERN;





    public static final String JAVA_TYPE_INTEGER = "Integer";
    public static final String JAVA_TYPE_STRING = "String";
    public static final String JAVA_TYPE_JSON = "JSONObject";

    public static final String SUFFIX_OF_TEMPLATES_CONFIG_FILE = "templates";

    static {

        FRAGMENT_PATTERN = Pattern.compile(
                "<@fragment name=\"([A-Za-z0-9-_]+)\">(.*?)</@fragment>",
                Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    }
}
