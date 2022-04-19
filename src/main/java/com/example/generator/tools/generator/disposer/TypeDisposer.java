package com.example.generator.tools.generator.disposer;

import com.example.generator.tools.database.domain.Column;
import com.example.generator.tools.generator.domain.ColumnInfo;
import com.example.generator.tools.generator.domain.EnumInfo;
import com.example.generator.tools.generator.domain.InternalInfo;
import com.example.generator.tools.generator.domain.Type;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface TypeDisposer {

    // 推荐使用的枚举用到的模式
    Pattern ENUM_COMMENT_PATTERN =
            Pattern.compile("^([A-Za-z\\u4e00-\\u9fa5]{1,})（(([A-Za-z0-9-]+：[\\u4e00-\\u9fa5A-Za-z0-9-]{1,}，?)+)）$");

    Pattern NUMBER_PATTERN =
            Pattern.compile("^[0-9]*$");

    default boolean isInternal(Column column) {
        return false;
    }

    default void disposeInternal(InternalInfo internalInfo, ColumnInfo columnInfo) {
        internalInfo.setColumnInfo(columnInfo);
    }

    default boolean isEnum(Column column) {
        if (StringUtils.isEmpty(column.getComment())) {
            return false;
        }
        return ENUM_COMMENT_PATTERN.matcher(column.getComment()).matches();
    }

    default void disposeEnum(EnumInfo enumInfo, ColumnInfo columnInfo) {
        Matcher enumColumnMatcher = ENUM_COMMENT_PATTERN.matcher(columnInfo.getColumnComment());

        //noinspection ResultOfMethodCallIgnored
        enumColumnMatcher.matches();

        // 判断枚举是否是数字类型
        boolean numberEnumFlag = true;
        System.out.println(columnInfo.getColumnComment());
        for (String enumItem : enumColumnMatcher.group(2).split("，")) {
            String[] enumItems = enumItem.split("：");
            if (!NUMBER_PATTERN.matcher(enumItems[0]).matches()) {
                numberEnumFlag = false;
            }
        }

        List<EnumInfo.EnumItem> enumItems = new ArrayList<>();

        Type type = numberEnumFlag ? Type.INTEGER : Type.STRING;

        // 填充枚举项信息
        for (String enumItem : enumColumnMatcher.group(2).split("，")) {

            String[] enumItemSegments = enumItem.split("：");

            EnumInfo.EnumItem enumItemInfo = EnumInfo.EnumItem.builder()
                    .type(type)
                    .itemType(type.getJavaType())
                    .itemName(numberEnumFlag ? "TMP_" + enumItemSegments[0] : StringUtils.upperCase(enumItemSegments[0]))
                    .itemComment(enumItemSegments[1])
                    .itemValue(enumItemSegments[0])
                    .build();

            enumItems.add(enumItemInfo);
        }

        enumInfo.setColumnInfo(columnInfo);
        enumInfo.setType(type);
        enumInfo.setEnumItems(enumItems);
    }

    Type getType(Column column);
}
