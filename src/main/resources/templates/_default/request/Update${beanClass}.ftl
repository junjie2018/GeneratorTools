package ${properties.package};

import java.time.LocalDateTime;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import javax.validation.constraints.NotBlank;

<#if packagesToImport??>
<#list packagesToImport as packageToImport>
import ${packageToImport};
</#list>
</#if>

@Data
public class Update${beanClass}Request {

<#list columnInfos as columnInfo>
    <@noSpaceLine>

    <#-- 忽略审计字段 -->
    <#if columnInfo.columnName == "org_id"
        || columnInfo.columnName == "creator"
        || columnInfo.columnName == "modifier"
        || columnInfo.columnName == "is_delete"
        || columnInfo.columnName == "gmt_create_time"
        || columnInfo.columnName == "gmt_modify_time">
        <#continue>
    </#if>

    <#if columnInfo.enumInfo??>

    /**
     * ${columnInfo.columnComment}
     *
     * @see ${properties.enumsPackage}.${columnInfo.enumInfo.enumClass}#value
     */

    <#if columnInfo.columnName == "id">
    @NotBlank
    </#if>

    private ${columnInfo.enumInfo.enumValueType} ${columnInfo.beanObject};

    <#elseif columnInfo.internalClassInfo??>

    /**
     * ${columnInfo.columnComment}
     */
    private JSONObject ${columnInfo.beanObject};

    <#else>

    /**
     * ${columnInfo.columnComment}
     */
    <#if columnInfo.columnName == "id">
    @NotBlank
    </#if>

    private ${columnInfo.fieldType} ${columnInfo.beanObject};

    </#if>

    </@noSpaceLine>

</#list>
}
