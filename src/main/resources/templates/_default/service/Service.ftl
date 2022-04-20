package junjie.fun.mywiki.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

<@include tpl="Common.ftl" fragment="importPackets"/>

@Service
public interface ${tableClass}Service extends IService<${tableClass}> {

    Long create${tableClass}(Create${tableClass}Request request);

    void delete${tableClass}s(List<Long> ${tableObject}Ids);

    Long update${tableClass}(Update${tableClass}Request request);

    PageData<${tableClass}Data> page${tableClass}(Page${tableClass}Request request);

    List<${tableClass}Data> query${tableClass}s(List<Long> ${tableObject}Ids);

}
