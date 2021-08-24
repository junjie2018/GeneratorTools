package ${packet};

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import java.time.LocalDateTime;
import com.alibaba.fastjson.annotation.JSONField;

<#if packetsToImport??>
<#list packetsToImport as packetToImport>
import ${packetToImport};
</#list>
</#if>

@Data
public class ${beanClass}Data {

    <#list columns as column>

    <@noSpaceLine>

    <#if column.enumerations??>

    /**
     * ${column.comment}
     *
     * @see ${properties.enumsPackage}.${column.enumerations.enumClass}#value
     */
    private ${column.enumerations.enumValueType} ${column.beanObject};

    <#elseif column.internalClassInfo??>

    /**
     * ${column.comment}
     */
    private JSONObject ${column.beanObject};

    <#else>

    /**
     * ${column.comment}
     */
    private ${column.fieldType} ${column.beanObject};

    </#if>

    </@noSpaceLine>

    </#list>

}
