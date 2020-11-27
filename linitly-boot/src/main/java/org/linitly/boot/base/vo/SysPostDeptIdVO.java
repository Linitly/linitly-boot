package org.linitly.boot.base.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: linxiunan
 * @date: 2020/11/27 16:09
 * @descrption:
 */
@Data
@Accessors(chain = true)
public class SysPostDeptIdVO {

    private Long postId;

    private Long deptId;
}
