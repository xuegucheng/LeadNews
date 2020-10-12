package com.heima.model.behavior.pojos;

import lombok.Data;
import lombok.Getter;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * APP行为实体表,一个行为实体可能是用户或者设备，或者其它
 */
@Data
public class ApBehaviorEntry {
    //主键
    private Integer id;
    //实体类型 0终端设备  1用户
    private Short type;
    //实体ID
    private Integer entryId;
    //创建时间
    private Date createdTime;
    //分片
    public String burst;

    @Alias("ApBehaviorEntryEnumType")
    public enum  Type{
        USER((short)1),EQUIPMENT((short)0);
        @Getter
        short code;
        Type(short code){
            this.code = code;
        }
    }

    public boolean isUser(){
        if(this.getType()!=null&&this.getType()== Type.USER.getCode()){
            return true;
        }
        return false;
    }
}