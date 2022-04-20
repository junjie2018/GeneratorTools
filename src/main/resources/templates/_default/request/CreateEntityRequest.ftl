package ${packet};

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Create${tableClass}Request {

<#list columnInfos as columnInfo>

    <#-- 忽略字段 -->
    <#if columnInfo.columnName == "id"
        || columnInfo.columnName == "creator"
        || columnInfo.columnName == "modifier"
        || columnInfo.columnName == "is_delete"
        || columnInfo.columnName == "version"
        || columnInfo.columnName == "create_time"
        || columnInfo.columnName == "modify_time">
        <#continue>
    </#if>

    <#-- 枚举字段 -->
    <#if columnInfo.enumInfo ??>
        /**
         * ${columnInfo.columnComment}
         */
        private ${columnInfo.enumInfo.enumItemType} ${columnInfo.columnObject};<#continue>
    </#if>

    <#-- 内部类字段 -->
    <#if columnInfo.internalInfo ??>
        /**
         * ${columnInfo.columnComment}
         */
        private JSONObject ${columnInfo.columnObject};<#continue>
    </#if>

    <#-- 其他字段 -->
    /**
     * ${columnInfo.columnComment}
     */
    private ${columnInfo.fieldType} ${columnInfo.columnObject};

</#list>
}
