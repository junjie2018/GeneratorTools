package ${packet};

import java.time.LocalDateTime;
import java.math.BigDecimal;
import lombok.Data;

<#if packetsToImport??>
    <#list packetsToImport as packetToImport>
import ${packetToImport};
    </#list>
</#if>

@Data
public class Create${beanClass}Request {

    <#list columns as column>

        <#-- 忽略字段 -->
        <#if column.logicName == "id"
        || column.logicName == "org_id"
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
        <#if column.internalBean??>
            /**
             * ${column.comment}
             */
            private List<String> ${column.beanObject};<#continue>
        </#if>

        <#-- 普通字段类型 -->
        /**
         * ${column.comment}
         */
        private ${column.fieldType} ${column.beanObject};

    </#list>

}