package ${packet};

import junjie.fun.mywiki.common.request.PageRequest;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
public class Page${tableClass}Request extends PageRequest {

    /**
     * 分页条件
     */
    private Condition condition = new Condition();

    @Data
    public static class Condition {

    }
}
