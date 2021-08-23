package ${properties.package};

import lombok.Data;
import javax.validation.constraints.NotNull;

<#if packagesToImport??>
<#list packagesToImport as packageToImport>
import ${packageToImport};
</#list>
</#if>

@Data
public class Page${beanClass}Request {
    /**
     * 当前页数
     */
    @NotNull
    private Integer current;

    /**
     * 每页记录数量
     */
    @NotNull
    private Integer limit;

    /**
     * 额外的查询条件
     */
    private Condition condition;

    @Data
    public static class Condition {

    }
}
