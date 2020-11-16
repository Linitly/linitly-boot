/**
 * @author: linxiunan
 * @date: 2020/5/27 10:07
 * @descrption:
 */
package org.linitly.boot.base.constant.entity;

/**
 * @author: linxiunan
 * @date: 2020/5/27 10:07
 * @descrption:
 */
public interface SysQuartzJobConstant {

    /****************************************************后台************************************************************************/

    String JOB_NAME_EMPTY_ERROR = "任务名称不能为空";

    int MAX_JOB_NAME_LENGTH = 20;

    String JOB_NAME_LENGTH_ERROR = "任务名称长度不符合限制";

    String JOB_CLASS_NAME_EMPTY_ERROR = "执行类名称不能为空";

    int MAX_JOB_CLASS_NAME_LENGTH = 100;

    String JOB_CLASS_NAME_LENGTH_ERROR = "任务执行名称长度不符合限制";

    String CORN_EXPRESSION_EMPTY_ERROR = "执行时间corn表达式不能为空";

    int MAX_CORN_EXPRESSION_LENGTH = 100;

    String CORN_EXPRESSION_LENGTH_ERROR = "执行时间corn表达式长度不符合限制";

    int MAX_DESCRIPTION_LENGTH = 255;

    String DESCRIPTION_LENGTH_ERROR = "任务描述长度不符合限制";
}
