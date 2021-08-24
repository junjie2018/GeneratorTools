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
public class ${beanClass} extends BaseModel {

     <#list columns as column>

     <@noSpaceLine>

     <#if column.logicName == "id"
        || column.logicName == "creator"
        || column.logicName == "modifier"
        || column.logicName == "is_delete"
        || column.logicName == "gmt_create_time"
        || column.logicName == "gmt_modify_time">
        <#continue>
    </#if>

     /**
      * ${column.comment}
      */

     <#if column.logicName == "id">
     @TableId(type = IdType.ID_WORKER_STR)
     </#if>

     <#if column.logicName == "is_delete">
     @TableLogic
     </#if>

     <#if column.enumInfo??>
     private ${column.enumInfo.enumValueType} ${column.beanObject};
     <#elseif column.internalClassInfo??>
     private JSONObject ${column.beanObject};


    <#else>private ${column.fieldType} ${column.beanObject};</#if>

     </@noSpaceLine>

    </#list>

}
