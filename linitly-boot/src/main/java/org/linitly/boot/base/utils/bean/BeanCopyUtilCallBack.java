package org.linitly.boot.base.utils.bean;

/**
 * @author: linxiunan
 * @date: 2020/5/6 10:16
 * @descrption:
 */
@FunctionalInterface
public interface BeanCopyUtilCallBack <S, T> {

    /**
     * 定义默认回调方法
     * @param t
     * @param s
     */
    void callBack(S t, T s);
}
