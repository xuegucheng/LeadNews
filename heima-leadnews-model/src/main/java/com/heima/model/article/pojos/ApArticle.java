package com.heima.model.article.pojos;


import com.heima.model.annotation.DateConvert;
import com.heima.model.annotation.IdEncrypt;
import lombok.Data;

import java.util.Date;

/**
 * 文章信息表，存储已发布的文章
 */
@Data
public class ApArticle {
    //主键
    private Integer id;
    //标题
    private String title;
    //文章作者的ID
    @IdEncrypt
    private Long authorId;
    //作者昵称
    private String authorName;
    //文章所属频道ID
    @IdEncrypt
    private Integer channelId;
    //频道名称
    private String channelName;
    //文章布局          0 无图文章           1 单图文章           2 多图文章
    private Short layout;
    //文章标记            0 普通文章           1 热点文章            2 置顶文章            3 精品文章           4 大V 文章
    private Byte flag;
    //文章图片          多张逗号分隔
    private String images;
    //文章标签最多3个 逗号分隔
    private String labels;
    //点赞数量
    private Integer likes;
    //收藏数量
    private Integer collection;
    //评论数量
    private Integer comment;
    //阅读数量
    private Integer views;
    //省市
    private Integer provinceId;
    //市区
    private Integer cityId;
    //区县
    private Integer countyId;
    //创建时间
    @DateConvert("yyyyMMddHHmmss")
    private Date createdTime;
    //发布时间
    @DateConvert("yyyyMMddHHmmss")
    private Date publishTime;
    //同步状态
    private Boolean syncStatus;

}