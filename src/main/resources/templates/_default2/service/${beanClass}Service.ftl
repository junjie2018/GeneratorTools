package ${packet};

import cn.hutool.core.bean.BeanUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.math.BigDecimal;

<#if packetsToImport??>
    <#list packetsToImport as packetToImport>
        import ${packetToImport};
    </#list>
</#if>

@Service
@AllArgsConstructor
public class ${beanClass}Service {

    private final ${beanClass}Mapper ${beanObject}Mapper;

    <@include tpl="CreateEntity.ftl" fragment="ServiceMethod"/>

    <@include tpl="UpdateEntity.ftl" fragment="ServiceMethod"/>

    <@include tpl="QueryEntity.ftl" fragment="ServiceMethod"/>

    <@include tpl="PageEntity.ftl" fragment="ServiceMethod"/>

    <@include tpl="DeleteEntity.ftl" fragment="ServiceMethod"/>

    <@include tpl="PrivateMethods.ftl" fragment="judgeEntityExistByIdAndTenantId"/>

    <@include tpl="PrivateMethods.ftl" fragment="judgeEntityExistByIdsAndTenantId"/>

    <@include tpl="PrivateMethods.ftl" fragment="judgeEntityExistById"/>

    <@include tpl="PrivateMethods.ftl" fragment="judgeEntityExistByIds"/>

}
