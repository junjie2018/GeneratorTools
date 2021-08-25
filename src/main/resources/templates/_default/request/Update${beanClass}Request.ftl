package ${packet};

import java.time.LocalDateTime;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import javax.validation.constraints.NotBlank;

<#if packetsToImport??>
    <#list packetsToImport as packetToImport>
import ${packetToImport};
    </#list>
</#if>

@Data
public class Update${beanClass}Request {

    <#list columns as column>

        <#-- 忽略字段 -->
        <#if column.logicName == "org_id"
        || column.logicName == "creator"
        || column.logicName == "modifier"
        || column.logicName == "is_delete"
        || column.logicName == "gmt_create_time"
        || column.logicName == "gmt_modify_time">
            <#continue>
        </#if>

        <#-- 枚举类型 -->
        <#if column.enumeration??>
            /**
             * ${column.comment}
             *
             * @see ${packagesProperties.enums}.${column.enumeration.enumClass}#value
             */
            private ${column.enumeration.itemType} ${column.beanObject};<#continue>
        </#if>

        <#-- 对象类型 -->
        <#if column.internalClassInfo??>
            /**
             * ${column.comment}
             */
            private JSONObject ${column.beanObject};<#continue>
        </#if>

        <#-- 其他类型 -->
        /**
         * ${column.comment}
         */
        <#if column.logicName == "id">
            @NotBlank
        </#if>
        private ${column.fieldType} ${column.beanObject};

    </#list>
}
