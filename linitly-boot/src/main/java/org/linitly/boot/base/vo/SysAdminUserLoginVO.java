package org.linitly.boot.base.vo;

import lombok.Data;
import lombok.experimental.Accessors;
import org.linitly.boot.base.entity.SysAdminUser;

/**
 * @author: linxiunan
 * @date: 2020/11/27 15:50
 * @descrption:
 */
@Data
@Accessors(chain = true)
public class SysAdminUserLoginVO extends SysAdminUser {

    private String token;

    private String refreshToken;
}