package com.heima.model.user.pojos;

import lombok.Data;
import lombok.Setter;

import java.util.Date;

/**
 * 用户文章列表
 */
@Data
public class ApUserArticleList {
    //主键
    private Integer id;
    //用户ID
    private Integer userId;
    //频道ID
    private Integer channelId;
    //文章ID
    private Integer articleId;
    //是否展示
    private Boolean isShow;
    //推荐时间
    private Date recommendTime;
    //是否阅读
    private Boolean isRead;
    //推荐算法
    private Integer strategyId;

}