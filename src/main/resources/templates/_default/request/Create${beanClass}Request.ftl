package ${packet};

import java.time.LocalDateTime;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

<#if packetsToImport??>
<#list packetsToImport as packetToImport>
import ${packetToImport};
</#list>
</#if>

@Data
public class Create${beanClass}Request {

    <#list columns as column>

    <#-- 忽略审计字段 -->
    <#if column.logicName == "id"
        || column.logicName == "org_id"
        || column.logicName == "creator"
        || column.logicName == "modifier"
        || column.logicName == "is_delete"
        || column.logicName == "gmt_create_time"
        || column.logicName == "gmt_modify_time">
        <#continue>
    </#if>

    <@noSpaceLine>

    <#if column.enumInfo??>  <#-- 如果列为枚举类型 -->

    /**
     * ${column.comment}
     *
     * @see ${properties.enumsPackage}.${column.enumInfo.enumClass}#value
     */
    private ${column.enumInfo.enumValueType} ${column.beanObject};

     <#elseif column.internalClassInfo??>

    /**
     * ${column.comment}
     */
    private JSONObject ${column.beanObject};

    <#else><#-- 如果列为普通类型 -->

    /**
     * ${column.comment}
     */
    private ${column.fieldType} ${column.beanObject};

    </#if>

    </@noSpaceLine>

    </#list>

}