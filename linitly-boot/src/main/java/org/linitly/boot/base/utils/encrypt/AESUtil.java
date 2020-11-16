package org.linitly.boot.base.utils.encrypt;

import org.linitly.boot.base.constant.admin.AdminCommonConstant;
import org.linitly.boot.base.exception.AESDecryptKeyException;

/**
 * @author: linxiunan
 * @date: 2020/5/27 15:12
 * @descrption:
 */
public class AESUtil {

    public static Long getAdminId(String encryptId) {
        Long id;
        try {
            id = Long.valueOf(AES.decryptFromBase64(encryptId, AdminCommonConstant.ADMIN_REST_AES_KEY));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new AESDecryptKeyException();
        }
        return id;
    }

    public static void main(String[] args) {
        System.out.println(AES.encryptToBase64(String.valueOf(1), AdminCommonConstant.ADMIN_REST_AES_KEY));
    }
}
