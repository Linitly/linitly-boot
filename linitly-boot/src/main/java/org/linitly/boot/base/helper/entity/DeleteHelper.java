package org.linitly.boot.base.helper.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 * @author: linxiunan
 * @date: 2020/12/3 14:14
 * @descrption:
 */
@Data
@Accessors(chain = true)
public class DeleteHelper {

    private String deleteTableName;

    private List<Map<String, Object>> deleteData;
}
