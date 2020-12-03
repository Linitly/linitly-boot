package org.linitly.boot.base.helper.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.linitly.boot.base.enums.DBCommandTypeEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: linxiunan
 * @date: 2020/12/2 11:29
 * @descrption:
 */
@Data
@Accessors(chain = true)
public class LogHelper {

    private DBCommandTypeEnum commandTypeEnum;

    private List<Long> entityIds = new ArrayList<>();

    private List<BaseEntity> changeEntities = new ArrayList<>();
}
