package ${properties.package};

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

<#if packagesToImport??>
<#list packagesToImport as packageToImport>
import ${packageToImport};
</#list>
</#if>

@Mapper
public interface ${beanClass}Mapper extends BaseMapper<${beanClass}> {

}