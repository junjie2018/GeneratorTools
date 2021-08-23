package ${properties.package};

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

<#if packagesToImport??>
<#list packagesToImport as packageToImport>
import ${packageToImport};
</#list>
</#if>

@Data
public class ${beanClass}IdsRequest {
    /**
     * 需要删除的${entityName}Id
     */
    @NotNull
    private List<String> ids;
}