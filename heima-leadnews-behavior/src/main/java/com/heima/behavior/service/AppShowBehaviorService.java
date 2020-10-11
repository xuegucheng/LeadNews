package com.heima.behavior.service;

import com.heima.model.behavior.dtos.ShowBehaviorDto;
import com.heima.model.common.dtos.ResponseResult;

public interface AppShowBehaviorService {


    /**
     * 保存用户行为
     * @param dto 用户行为封装数据
     * @return
     */
    public ResponseResult saveShowBehavior(ShowBehaviorDto dto);


}
