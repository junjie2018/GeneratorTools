package ${packet};

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;
import java.math.BigDecimal;

<@include tpl="Common.ftl" fragment="importPackets"/>

@Data
@EqualsAndHashCode(callSuper = true)
public class ${tableClass}Data extends ${tableClass} {

}
