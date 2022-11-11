package ${packet};

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

<@include tpl="Common.ftl" fragment="importPackets"/>

@Mapper
public interface ${tableClass}Mapper extends BaseMapper<${tableClass}> {
}
