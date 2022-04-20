package ${packet};

import junjie.fun.mywiki.common.entity.BaseEntity;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

<@include tpl="Common.ftl" fragment="importPackets"/>

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("${tableName}")
public class ${tableClass} extends BaseEntity {

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
