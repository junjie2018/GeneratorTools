package ${packet};

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;

import java.time.LocalDateTime;

import lombok.*;

<#if packetsToImport??>
    <#list packetsToImport as packetToImport>
        import ${packetToImport};
    </#list>
</#if>

import java.io.Serializable;

/**
* @author wujj
*/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("${logicName}")
public class ${beanClass} extends BaseModel{

    <#list columns as column>

        <#-- 忽略字段 -->
        <#if column.logicName == "id"
        || column.logicName == "creator"
        || column.logicName == "modifier"
        || column.logicName == "is_delete"
        || column.logicName == "gmt_create_time"
        || column.logicName == "gmt_modify_time">
            <#continue>
        </#if>

        <#-- 枚举字段 -->
        <#if column.enumeration ??>
        /**
         * ${column.comment}
         */
        private ${column.enumeration.itemType} ${column.beanObject};<#continue>
        </#if>

        <#-- 内部类字段 -->
        <#if column.internalBean ??>
        /**
         * ${column.comment}
         */
        private JSONObject ${column.beanObject};<#continue>
        </#if>

        <#-- 其他字段 -->
       /**
        * ${column.comment}
        */
       private ${column.fieldType} ${column.beanObject};

    </#list>

}
