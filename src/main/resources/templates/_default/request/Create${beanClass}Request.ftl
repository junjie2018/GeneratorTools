package ${properties.package};

import java.time.LocalDateTime;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

<#if packagesToImport??>
<#list packagesToImport as packageToImport>
import ${packageToImport};
</#list>
</#if>

@Data
public class Create${beanClass}Request {

    <#list columnInfos as columnInfo>

    <#-- 忽略审计字段 -->
    <#if columnInfo.columnName == "id"
        || columnInfo.columnName == "org_id"
        || columnInfo.columnName == "creator"
        || columnInfo.columnName == "modifier"
        || columnInfo.columnName == "is_delete"
        || columnInfo.columnName == "gmt_create_time"
        || columnInfo.columnName == "gmt_modify_time">
        <#continue>
    </#if>

    <@noSpaceLine>

    <#if columnInfo.enumInfo??>  <#-- 如果列为枚举类型 -->

    /**
     * ${columnInfo.columnComment}
     *
     * @see ${properties.enumsPackage}.${columnInfo.enumInfo.enumClass}#value
     */
    private ${columnInfo.enumInfo.enumValueType} ${columnInfo.beanObject};

     <#elseif columnInfo.internalClassInfo??>

    /**
     * ${columnInfo.columnComment}
     */
    private JSONObject ${columnInfo.beanObject};

    <#else><#-- 如果列为普通类型 -->

    /**
     * ${columnInfo.columnComment}
     */
    private ${columnInfo.fieldType} ${columnInfo.beanObject};

    </#if>

    </@noSpaceLine>

    </#list>

}