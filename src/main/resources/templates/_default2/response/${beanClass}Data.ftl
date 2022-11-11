package ${packet};

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import com.alibaba.fastjson.annotation.JSONField;

<#if packetsToImport??>
<#list packetsToImport as packetToImport>
import ${packetToImport};
</#list>
</#if>

@Data
public class ${beanClass}Data {

    <#list columns as column>

        <#if column.enumerations??>
            /**
             * ${column.comment}
             *
             * @see ${packagesProperties.enums}.${column.enumerations.enumClass}#value
             */
            private ${column.enumerations.itemType} ${column.beanObject};<#continue>
        </#if>

        <#if column.internalClassInfo??>
            /**
             * ${column.comment}
             */
            private JSONObject ${column.beanObject};<#continue>
        </#if>

        /**
         * ${column.comment}
         */
        private ${column.fieldType} ${column.beanObject};

    </#list>
}
