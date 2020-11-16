package org.linitly.boot.base.utils.page;

import com.github.pagehelper.PageInfo;
import org.linitly.boot.base.constant.global.GlobalConstant;
import org.linitly.boot.base.helper.entity.PageResponseResult;
import org.linitly.boot.base.helper.entity.ResponseResult;

import java.util.List;

/**
 * @Description 返回结果工具类
 * @author linxiunan
 * @date 2019年2月27日
 */
public class ResponseResultUtil {

	/**
	 * 通过框架分页信息类和结果集封装成统一分页返回封装类
	 * @param pageInfo pageHelper框架中分页信息类
	 * @return
	 */
	public static <T> PageResponseResult copyToPageResponseResult(PageInfo<T> pageInfo) {
		return pageInfo == null ? null : new PageResponseResult<>(pageInfo.getTotal(), pageInfo.getPages(),
				pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.isIsFirstPage(), pageInfo.isIsLastPage(),
				pageInfo.isHasPreviousPage(), pageInfo.isHasNextPage(), pageInfo.getList());
	}

	/**
	 * 通过结果list返回统一返回封装类
	 * @param list 查询的结果list
	 * @return
	 */
	public static <T> ResponseResult getResponseResult(List<T> list) {
		PageInfo<T> pageInfo = new PageInfo<>(list);
		PageResponseResult pageResponseResult = copyToPageResponseResult(pageInfo);
		return pageResponseResult == null ? new ResponseResult<>(new PageResponseResult<>(0, null)) : new ResponseResult<>(pageResponseResult);
	}

	/**
	 * 通过结果list返回统一返回封装类
	 * @param list 查询的结果list
	 * @param specialData 特殊数据
	 * @return
	 */
	public static <T> ResponseResult getResponseResult(List<T> list, Object specialData) {
		PageInfo<T> pageInfo = new PageInfo<>(list);
		PageResponseResult pageResponseResult = copyToPageResponseResult(pageInfo);
		return pageResponseResult == null ? new ResponseResult<>(new PageResponseResult<>(0, null)) : new ResponseResult<>(pageResponseResult, specialData);
	}

	/**
	 * 返回错误码为405的统一返回封装类
	 * @param message 错误提示
	 * @return
	 */
	public static ResponseResult generalError(String message) {
		return new ResponseResult(GlobalConstant.GENERAL_ERROR, message);
	}

}
