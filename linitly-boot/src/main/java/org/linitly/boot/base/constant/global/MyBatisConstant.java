package org.linitly.boot.base.constant.global;

import org.linitly.boot.base.helper.entity.DeleteHelper;
import org.linitly.boot.base.helper.entity.LogHelper;

public class MyBatisConstant {

    public static ThreadLocal<LogHelper> LOG_HELPER = new ThreadLocal<>();

    public static ThreadLocal<DeleteHelper> DELETE_HELPER = new ThreadLocal<>();

    public static ThreadLocal<Boolean> MYBATIS_INTERCEPT_PASS = new ThreadLocal<>();

    public static final String CREATED_USER_ID_FIELD = "createdUserId";

    public static final String LAST_MODIFIED_USER_ID_FIELD = "lastModifiedUserId";

    public static final String DELETED_USER_ID_COLUMN = "deleted_user_id";

    public static final String SYSTEM_CODE_COLUMN = "system_code";
}
