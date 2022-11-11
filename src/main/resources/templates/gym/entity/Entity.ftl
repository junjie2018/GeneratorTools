package ${packet};

import java.time.LocalDateTime;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

<@include tpl="Common.ftl" fragment="importPackets"/>

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("${tableName}")
public class ${tableClass} {

<#list columnInfos as columnInfo>

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
