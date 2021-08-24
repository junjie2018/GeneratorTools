package ${packet};

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ${comment}
 *
 * @author wujj
 */
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public enum ${enumClass} {

    <#list enumItems as enumItem>
    /**
     * ${enumItem.comment}
     */
    <#if itemType=="String">
    ${enumItem.itemName}("${enumItem.itemValue}"),
    <#else>
    ${enumItem.itemName}(${enumItem.itemValue}),
    </#if>
    </#list>
    ;

    @Getter
    private final ${itemType} value;

    <#-- 如果Value的类型为String -->
    <#if itemType=="String">
    public static ${enumClass} convert(String inputValue) {
        for (${enumClass} enumItem : ${enumClass}.values()) {
            if (enumItem.getValue().equals(inputValue)) {
                return enumItem;
            }
        }
        throw new RuntimeException("Enum Transfer Wrong.");
    }
    <#-- 如果Value的类型为Integer -->
    <#else>
    public static ${enumClass} convert(Integer inputValue) {
        for (${enumClass} enumItem : ${enumClass}.values()) {
            if (enumItem.getValue().equals(inputValue)) {
                return enumItem;
            }
        }
        throw new RuntimeException("Enum Transfer Wrong.");
    }
    </#if>

}