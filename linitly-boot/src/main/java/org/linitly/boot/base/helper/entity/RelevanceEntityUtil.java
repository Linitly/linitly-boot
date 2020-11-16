package org.linitly.boot.base.helper.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author: linxiunan
 * @date: 2020/5/22 13:55
 * @descrption: 使用UUID的主键方式时，插入关联表需要生成id，无法像自增主键方式一样循环插入，需要生成关联对象list插入
 */
public class RelevanceEntityUtil {

    public static <S, T> List<RelevanceEntity> getRelevanceEntities(S oneTableId, List<T> manyTableIds) {
        if (null == manyTableIds || manyTableIds.isEmpty() || null == oneTableId || "".equals(oneTableId)) {
            return null;
        }
        List<RelevanceEntity> relevanceEntities = new ArrayList<>(manyTableIds.size());
        RelevanceEntity relevanceEntity = null;
        for (int i = 0; i < manyTableIds.size(); i ++) {
            relevanceEntity =
                    new RelevanceEntity(UUID.randomUUID().toString().replace("-", ""), oneTableId, manyTableIds.get(i));
            relevanceEntities.add(i, relevanceEntity);
        }
        return relevanceEntities;
    }
}
