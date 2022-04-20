package ${packet};

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

<@include tpl="Common.ftl" fragment="importPackets"/>

@Data
@EqualsAndHashCode(callSuper = true)
public class ${tableClass}Data extends ${tableClass} {

}
