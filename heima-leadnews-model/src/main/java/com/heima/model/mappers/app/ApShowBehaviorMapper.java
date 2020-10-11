package com.heima.model.mappers.app;

import com.heima.model.behavior.pojos.ApShowBehavior;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ApShowBehaviorMapper {

    /**
     * 获取以及存在的用户数据
     * @param entryId 实体ID
     * @param articleIds 文章IDS
     * @return
     */
    List<ApShowBehavior> selectListByEntryIdAndArticleIds(@Param("entryId")Integer entryId,@Param("articleIds")Integer[] articleIds );


    /**
     * 保存用户展现行为数据
     * @param entryId 实体ID
     * @param articleIds 文章ID
     */
    void saveShowBehavior(@Param("articleIds") Integer[] articleIds, @Param("entryId") Integer entryId);

}
