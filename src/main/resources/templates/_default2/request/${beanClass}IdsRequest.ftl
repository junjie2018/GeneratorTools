package ${packet};

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

<#if packetsToImport??>
    <#list packetsToImport as packetToImport>
        import ${packetToImport};
    </#list>
</#if>

@Data
public class ${beanClass}IdsRequest {
    /**
     * ${entityName}Id
     */
    @NotNull
    private List<String> ids;
}