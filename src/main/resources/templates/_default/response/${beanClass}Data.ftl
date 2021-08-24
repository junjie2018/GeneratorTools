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

    <#list columnInfos as columnInfo>

    <@noSpaceLine>

    <#if columnInfo.enumInfo??>

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

    <#else>

    /**
     * ${columnInfo.columnComment}
     */
    private ${columnInfo.fieldType} ${columnInfo.beanObject};

    </#if>

    </@noSpaceLine>

    </#list>

}
