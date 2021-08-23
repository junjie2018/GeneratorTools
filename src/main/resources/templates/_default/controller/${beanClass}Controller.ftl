package ${properties.package};

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.sdstc.core.constants.APICons;
import com.sdstc.core.vo.ResponseVo;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

<#if packagesToImport??>
<#list packagesToImport as packageToImport>
import ${packageToImport};
</#list>
</#if>

/**
 * ${entityName}管理
 */
@RestController
@RequiredArgsConstructor
public class ${beanClass}Controller {

    private final ${beanClass}Service ${beanObject}Service;

    <@include tpl="CreateEntity.ftl" fragment="ControllerMethod"/>

    <@include tpl="UpdateEntity.ftl" fragment="ControllerMethod"/>

    <@include tpl="QueryEntity.ftl" fragment="ControllerMethod"/>

    <@include tpl="PageEntity.ftl" fragment="ControllerMethod"/>

    <@include tpl="DeleteEntity.ftl" fragment="ControllerMethod"/>

}
