package com.heima.article.service.impl;

import com.heima.article.service.AppArticleService;
import com.heima.common.article.constans.ArticleConstans;
import com.heima.model.article.dtos.ArticleHomeDto;
import com.heima.model.article.pojos.ApArticle;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.mappers.app.ApArticleMapper;
import com.heima.model.mappers.app.ApUserArticleListMapper;
import com.heima.model.user.pojos.ApUser;
import com.heima.model.user.pojos.ApUserArticleList;
import com.heima.utils.threadlocal.AppThreadLocalUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@SuppressWarnings("all")
public class AppArticleServiceImpl implements AppArticleService {

    // 单页最大加载的数字
    private static final short MAX_PAGE_SIZE = 50;

    @Autowired
    private ApArticleMapper apArticleMapper;

    @Autowired
    private ApUserArticleListMapper apUserArticleListMapper;

    /**
     * 加载首页文章
     * @param dto 封装数据
     * @param type 1 加载更多  2 加载更新
     * @return 数据列表
     */
    @Override
    public ResponseResult load(ArticleHomeDto dto, Short type) {
        //参数校验
        if(dto ==null ){
            dto = new ArticleHomeDto();
        }
        //时间校验
        if(dto.getMaxBehotTime()==null){
            dto.setMaxBehotTime(new Date());
        }

        if(dto.getMinBehotTime()==null){
            dto.setMinBehotTime(new Date());
        }

        //分页参数的校验
        Integer size = dto.getSize();
        if(size==null || size <= 0){
            size = 20;
        }
        size = Math.min(size,MAX_PAGE_SIZE);
        dto.setSize(size);

        //文章频道参数校验
        if(StringUtils.isEmpty(dto.getTag())){
            dto.setTag(ArticleConstans.DEFAULT_TAG);
        }

        //类型参数校验
        if(!type.equals(ArticleConstans.LOADTYPE_LOAD_MORE)&&!type.equals(ArticleConstans.LOADTYPE_LOAD_NEW)){
            type = ArticleConstans.LOADTYPE_LOAD_MORE;
        }

        //获取用户的信息
        ApUser user = AppThreadLocalUtils.getUser();

        //判断用户是否存在
        if(user != null){
            //存在 已登录 加载推荐的文章
            List<ApArticle> apArticleList = getUserArticle(user,dto,type);
            return ResponseResult.okResult(apArticleList);
        }else{
            //不存在 未登录，加载默认的文章
            List<ApArticle> apArticles = getDefaultArticle(dto,type);
            return ResponseResult.okResult(apArticles);
        }

    }



    /**
     * 加载默认的文章信息
     * @param dto
     * @param type
     * @return
     */
    private List<ApArticle> getDefaultArticle(ArticleHomeDto dto, Short type) {
        return apArticleMapper.loadArticleListByLocation(dto,type);
    }


    /**
     * 先从用户的推荐表中查找文章信息，如果没有再从默认文章信息获取数据
     * @param user
     * @param dto 封装数据
     * @param type 1 加载更多  2 加载更新
     * @return
     */
    private List<ApArticle> getUserArticle(ApUser user, ArticleHomeDto dto, Short type) {
        List<ApUserArticleList> list = apUserArticleListMapper.loadArticleIdListByUser(user,dto,type);
        if(!list.isEmpty()){
            return apArticleMapper.loadArticleListByIdList(list);
        }else{
            return getDefaultArticle(dto,type);
        }
    }
}
