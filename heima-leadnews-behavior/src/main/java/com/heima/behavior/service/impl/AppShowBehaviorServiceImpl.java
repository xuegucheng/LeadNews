package com.heima.behavior.service.impl;

import com.heima.behavior.service.AppShowBehaviorService;
import com.heima.model.behavior.dtos.ShowBehaviorDto;
import com.heima.model.behavior.pojos.ApBehaviorEntry;
import com.heima.model.behavior.pojos.ApShowBehavior;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.mappers.app.ApBehaviorEntryMapper;
import com.heima.model.mappers.app.ApShowBehaviorMapper;
import com.heima.model.user.pojos.ApUser;
import com.heima.utils.threadlocal.AppThreadLocalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 描述:
 * 用户行为服务类
 *
 * @author XueGuCheng
 * @create 2020-10-11 19:19
 */
@Service
@SuppressWarnings("all")
public class AppShowBehaviorServiceImpl implements AppShowBehaviorService {

    @Autowired
    private ApShowBehaviorMapper apShowBehaviorMapper;

    @Autowired
    private ApBehaviorEntryMapper apBehaviorEntryMapper;



    /**
     * 保存用户行为
     * @param dto 用户行为封装数据
     * @return
     */
    @Override
    public ResponseResult saveShowBehavior(ShowBehaviorDto dto) {
        ApUser user = AppThreadLocalUtils.getUser();
        // 用户和设备不能同时为空
        if (user == null && (dto.getArticleIds() == null || dto.getArticleIds().isEmpty())){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }
        Long userId = null;
        if (user != null) {
            userId = user.getId();
        }
        ApBehaviorEntry apBehaviorEntry = apBehaviorEntryMapper.selectByUserIdOrEquipemntId(userId,dto.getEquipmentId());
        // 行为实体找以及注册了，逻辑上这里是必定有值的，除非参数错误
        if(apBehaviorEntry==null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        // 过滤新数据
        //获取前台传递过来的文章列表id
        Integer[] articleIds = new Integer[dto.getArticleIds().size()];
        for (int i = 0; i < articleIds.length; i++) {
            articleIds[i] = dto.getArticleIds().get(i).getId();
        }
        //根据行为实体id和文章列表id查询app行为表  ap_show_behavior
        List<ApShowBehavior> list = apShowBehaviorMapper.selectListByEntryIdAndArticleIds(apBehaviorEntry.getId(),articleIds);
        //数据的过滤，需要删除表中已经存在的文章id
        ArrayList<Integer> stinglist = new ArrayList(Arrays.asList(articleIds));
        if (!list.isEmpty()){
            for (ApShowBehavior apShowBehavior : list) {
                Integer articleId = apShowBehavior.getArticleId();
                stinglist.remove(articleId);
            }
        }

        //保存操作
        if (!stinglist.isEmpty()){
            articleIds = new Integer[stinglist.size()];
            stinglist.toArray(articleIds);
            apShowBehaviorMapper.saveShowBehavior(articleIds,apBehaviorEntry.getId());
        }

        return ResponseResult.okResult(0);
    }
}
