package ${packet};

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ${enumComment}
 *
 * @author wujj
 */
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public enum ${enumClass} {

<#list enumItems as enumItem>
    /**
     * ${enumItem.enumItemComment}
     */
    <#if enumValueType=="String">
    ${enumItem.enumItemName}("${enumItem.enumItemValue}"),
    <#else>
    ${enumItem.enumItemName}(${enumItem.enumItemValue}),
    </#if>
</#list>
    ;

    @Getter
    private ${enumValueType} value;

    <#if enumValueType=="String">
    public static ${enumClass} convert(String inputValue) {
    <#else>
    public static ${enumClass} convert(Integer inputValue) {
    </#if>
        for (${enumClass} enumItem : ${enumClass}.values()) {
            if (enumItem.getValue().equals(inputValue)) {
                return enumItem;
            }
        }
        throw new RuntimeException("Enum Transfer Wrong.");
    }
}