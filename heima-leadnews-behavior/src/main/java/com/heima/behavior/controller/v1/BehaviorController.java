package com.heima.behavior.controller.v1;

import com.heima.article.apis.BehaviorControllerApi;
import com.heima.behavior.service.AppShowBehaviorService;
import com.heima.model.behavior.dtos.ShowBehaviorDto;
import com.heima.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述:
 * 用户行为Controller
 *
 * @author XueGuCheng
 * @create 2020-10-11 19:13
 */
@RestController
@RequestMapping("/api/v1/behavior")
public class BehaviorController implements BehaviorControllerApi {


    @Autowired
    private AppShowBehaviorService appShowBehaviorService;


    /**
     * 保存用户行为
     * @param dto 用户行为封装数据
     * @return
     */
    @PostMapping("/show_behavior")
    @Override
    public ResponseResult saveShowBehavior(@RequestBody ShowBehaviorDto dto) {
        return appShowBehaviorService.saveShowBehavior(dto);
    }



}
