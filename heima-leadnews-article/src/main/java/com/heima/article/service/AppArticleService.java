package com.heima.article.service;

import com.heima.model.article.dtos.ArticleHomeDto;
import com.heima.model.common.dtos.ResponseResult;

public interface AppArticleService {

    /**
     * 加载首页文章
     * @param dto 封装数据
     * @param type 1 加载更多  2 加载更新
     * @return 数据列表
     */
    ResponseResult load(ArticleHomeDto dto, Short type);
}
