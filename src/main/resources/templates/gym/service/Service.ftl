package ${packet};

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import fun.junjie.common.domain.PageData;

import java.util.List;

<@include tpl="Common.ftl" fragment="importPackets"/>

@Service
public interface ${tableClass}Service extends IService<${tableClass}> {

    /**
     * 创建${tableComment}
     */
    Integer create${tableClass}(Create${tableClass}Request request);

    /**
     * 删除${tableComment}
     */
    void delete${tableClass}s(List<Long> ${tableObject}Ids);

    /**
     * 编辑${tableComment}
     */
    Integer update${tableClass}(Update${tableClass}Request request);

    /**
     * 分页查找${tableComment}
     */
    PageData<${tableClass}Data> page${tableClass}(Page${tableClass}Request request);

    /**
     * 根据Id数组查找${tableComment}
     */
    List<${tableClass}Data> query${tableClass}s(List<Long> ${tableObject}Ids);
}
