package com.heima.model.mappers.app;

import com.heima.model.behavior.pojos.ApBehaviorEntry;
import org.apache.ibatis.annotations.Param;

public interface ApBehaviorEntryMapper {

    /**
     * 选择用户的行为对象，优先按用户选择
     * @param userId
     * @param equipmentId
     * @return
     */
    ApBehaviorEntry selectByUserIdOrEquipemntId(@Param("userId") Long userId, @Param("equipmentId") Integer equipmentId);

}
