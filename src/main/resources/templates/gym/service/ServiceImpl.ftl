package ${packet};

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import fun.junjie.common.domain.PageData;
import fun.junjie.common.utils.CopyUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

<@include tpl="Common.ftl" fragment="importPackets"/>

@Slf4j
@Service
@RequiredArgsConstructor
public class ${tableClass}ServiceImpl extends ServiceImpl<${tableClass}Mapper, ${tableClass}> implements ${tableClass}Service {

    @Override
    public Integer create${tableClass}(Create${tableClass}Request request) {
        ${tableClass} eBookInert = ${tableClass}.builder()

                <#list columnInfos as columnInfo>
                    <#if columnInfo.columnName == "id"
                    || columnInfo.columnName == "creator"
                    || columnInfo.columnName == "modifier"
                    || columnInfo.columnName == "is_delete"
                    || columnInfo.columnName == "version"
                    || columnInfo.columnName == "create_time"
                    || columnInfo.columnName == "modify_time">
                        <#continue>
                    </#if>

                    .${columnInfo.columnObject}(request.get${columnInfo.columnClass}())

                </#list>
                .build();

        this.baseMapper.insert(eBookInert);

        return eBookInert.getId();
    }

    @Override
    public void delete${tableClass}s(List<Long> ${tableObject}Ids) {
        this.baseMapper.deleteBatchIds(${tableObject}Ids);
    }

    @Override
    public Integer update${tableClass}(Update${tableClass}Request request) {
        LambdaUpdateWrapper<${tableClass}> updateWrapper = new LambdaUpdateWrapper<${tableClass}>()
                .eq(${tableClass}::getId, request.getId())
                <#list columnInfos as columnInfo>
                    <#if columnInfo.columnName == "id"
                    || columnInfo.columnName == "creator"
                    || columnInfo.columnName == "modifier"
                    || columnInfo.columnName == "is_delete"
                    || columnInfo.columnName == "version"
                    || columnInfo.columnName == "create_time"
                    || columnInfo.columnName == "modify_time">
                        <#continue>
                    </#if>

                    .set(${tableClass}::get${columnInfo.columnClass}, request.get${columnInfo.columnClass}())

                </#list>;

        this.baseMapper.update(null, updateWrapper);

        return request.getId();
    }

    @Override
    public PageData<${tableClass}Data> page${tableClass}(Page${tableClass}Request request) {

        Page<${tableClass}> pageEntity = request.getPage(${tableClass}.class);
        Page${tableClass}Request.Condition condition = request.getCondition();

        LambdaQueryWrapper<${tableClass}> queryWrapper = new LambdaQueryWrapper<${tableClass}>()
                .orderByDesc(${tableClass}::getCreateTime);

        baseMapper.selectPage(pageEntity, queryWrapper);

        return CopyUtils.copyPageData(pageEntity, ${tableClass}Data.class);
    }

    @Override
    public List<${tableClass}Data> query${tableClass}s(List<Long> ${tableObject}Ids) {

        LambdaQueryWrapper<${tableClass}> queryWrapper = new LambdaQueryWrapper<${tableClass}>()
                .in(${tableClass}::getId, ${tableObject}Ids);

        return CopyUtils.copyList(baseMapper.selectList(queryWrapper), ${tableClass}Data.class);
    }
}