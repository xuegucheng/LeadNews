package com.heima.model.behavior.pojos;

import lombok.Data;
import lombok.Setter;

import java.util.Date;

/**
 * 文章展现行为表
 */
@Data
public class ApShowBehavior {
    //主键
    private Integer id;
    //实体ID
    private Integer entryId;
    //文章ID
    private Integer articleId;
    //是否点击
    private Boolean isClick;
    //文章加载时间
    private Date showTime;
    //登录时间
    private Date createdTime;

}