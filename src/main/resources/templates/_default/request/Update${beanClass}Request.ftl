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
    <@noSpaceLine>

    <#-- 忽略审计字段 -->
    <#if column.logicName == "org_id"
        || column.logicName == "creator"
        || column.logicName == "modifier"
        || column.logicName == "is_delete"
        || column.logicName == "gmt_create_time"
        || column.logicName == "gmt_modify_time">
        <#continue>
    </#if>

    <#if column.enumeration??>

    /**
     * ${column.comment}
     *
     * @see ${properties.enumsPackage}.${column.enumeration.enumClass}#value
     */

    <#if column.logicName == "id">
    @NotBlank
    </#if>

    private ${column.enumeration.enumValueType} ${column.beanObject};

    <#elseif column.internalClassInfo??>

    /**
     * ${column.comment}
     */
    private JSONObject ${column.beanObject};

    <#else>

    /**
     * ${column.comment}
     */
    <#if column.logicName == "id">
    @NotBlank
    </#if>

    private ${column.fieldType} ${column.beanObject};

    </#if>

    </@noSpaceLine>

</#list>
}
