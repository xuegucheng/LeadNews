package com.heima.article.apis;

import com.heima.model.behavior.dtos.ShowBehaviorDto;
import com.heima.model.common.dtos.ResponseResult;

/**
 * 用户行为
 */
public interface BehaviorControllerApi {

    /**
     * 保存用户行为
     * @param dto 用户行为封装数据
     * @return
     */
    ResponseResult saveShowBehavior(ShowBehaviorDto dto);


}
