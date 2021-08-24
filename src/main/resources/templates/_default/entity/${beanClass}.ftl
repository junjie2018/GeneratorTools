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
@TableName("${tableName}")
public class ${beanClass} extends BaseModel {

     <#list columnInfos as columnInfo>

     <@noSpaceLine>

     <#if columnInfo.columnName == "id"
        || columnInfo.columnName == "creator"
        || columnInfo.columnName == "modifier"
        || columnInfo.columnName == "is_delete"
        || columnInfo.columnName == "gmt_create_time"
        || columnInfo.columnName == "gmt_modify_time">
        <#continue>
    </#if>

     /**
      * ${columnInfo.columnComment}
      */

     <#if columnInfo.columnName == "id">
     @TableId(type = IdType.ID_WORKER_STR)
     </#if>

     <#if columnInfo.columnName == "is_delete">
     @TableLogic
     </#if>

     <#if columnInfo.enumInfo??>
     private ${columnInfo.enumInfo.enumValueType} ${columnInfo.beanObject};
     <#elseif columnInfo.internalClassInfo??>
     private JSONObject ${columnInfo.beanObject};


    <#else>private ${columnInfo.fieldType} ${columnInfo.beanObject};</#if>

     </@noSpaceLine>

    </#list>

}
