package ${packet};

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import junjie.fun.mywiki.common.response.PageData;
import junjie.fun.mywiki.common.response.ResponseVo;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

<@include tpl="Common.ftl" fragment="importPackets"/>

/**
 * ${tableComment}管理
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class ${tableClass}Controller {

    private final ${tableClass}Service ${tableObject}Service;

    /**
     * 分页查询${tableClass}
     */
    @PostMapping("/${tableObject}/page${tableClass}")
    public ResponseVo<PageData<${tableClass}Data>> page${tableClass}(@Valid @RequestBody Page${tableClass}Request request) {
        return ResponseVo.success(${tableObject}Service.page${tableClass}(request));
    }

    /**
     * 创建${tableClass}
     */
    @PostMapping("/${tableObject}/create${tableClass}")
    public ResponseVo<Long> create${tableClass}(@Valid @RequestBody Create${tableClass}Request request) {
        return ResponseVo.success(${tableObject}Service.create${tableClass}(request));
    }

    /**
     * 更新${tableClass}
     */
    @PostMapping("/${tableObject}/update${tableClass}")
    public ResponseVo<Long> update${tableClass}(@Valid @RequestBody Update${tableClass}Request request) {
        return ResponseVo.success(${tableObject}Service.update${tableClass}(request));
    }

    /**
     * 根据Id查询${tableClass}
     *
     * @param ${tableObject}Id ${tableClass}的主键Id
     */
    @PostMapping("/${tableObject}/query${tableClass}")
    public ResponseVo<${tableClass}Data> query${tableClass}(@RequestParam("${tableObject}Id") Long ${tableObject}Id) {

        List<${tableClass}Data> ${tableObject}Data = ${tableObject}Service.query${tableClass}s(Collections.singletonList(${tableObject}Id));

        return ResponseVo.success(CollectionUtils.isNotEmpty(${tableObject}Data) ? ${tableObject}Data.get(0) : null);
    }

    /**
     * 根据Id数组批量查询${tableClass}
     *
     * @param ${tableObject}Ids ${tableClass}的主键Id数组
     */
    @PostMapping("/${tableObject}/query${tableClass}s")
    public ResponseVo<List<${tableClass}Data>> query${tableClass}s(@RequestBody List<Long> ${tableObject}Ids) {

        return ResponseVo.success(${tableObject}Service.query${tableClass}s(${tableObject}Ids));

    }

    /**
     * 根据Id删除${tableClass}
     *
     * @param ${tableObject}Id ${tableClass}的主键Id
     */
    @PostMapping("/${tableObject}/delete${tableClass}")
    public ResponseVo<Long> delete${tableClass}(@RequestParam("${tableObject}Id") Long ${tableObject}Id) {
        ${tableObject}Service.delete${tableClass}s(Collections.singletonList(${tableObject}Id));

        return ResponseVo.success();
    }

    /**
     * 根据Id数组批量删除${tableClass}
     *
     * @param ${tableObject}Ids ${tableClass}的主键Id数组
     */
    @PostMapping("/${tableObject}/delete${tableClass}s")
    public ResponseVo<Void> delete${tableClass}s(@RequestBody List<Long> ${tableObject}Ids) {

        ${tableObject}Service.delete${tableClass}s(${tableObject}Ids);

        return ResponseVo.success();
    }
}
