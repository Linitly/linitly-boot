package org.linitly.boot.base.helper.entity;

import lombok.Data;

/**
 * 分页查询时返回对象
 * @author siwei
 * @date 2018年7月26日
 */
@Data
public class PageResponseResult<T> {
	
	// 分页信息
	private PageEntity pagination;
	
	// 分页查询时当前页查询出的结果集
	private T result;

	public PageResponseResult(PageEntity pageEntity, T result) {
		this.pagination = pageEntity;
		this.result = result;
	}
	
	public PageResponseResult(long totalCount, T result) {
		PageEntity pageEntity = new PageEntity(totalCount);
		this.pagination = pageEntity;
		this.result = result;
	}
	
	public PageResponseResult(long totalCount, int totalPages, T result) {
		PageEntity pageEntity = new PageEntity(totalCount, totalPages);
		this.pagination = pageEntity;
		this.result = result;
	}
	
	public PageResponseResult(long totalCount, int totalPages, int pageNumber, int pageSize, T result) {
		PageEntity pageEntity = new PageEntity(totalCount, totalPages, pageNumber, pageSize);
		this.pagination = pageEntity;
		this.result = result;
	}
	
	public PageResponseResult(long totalCount, int totalPages, int pageNumber, int pageSize,
			boolean isFirstPage, boolean isLastPage, boolean hasPreviousPage, boolean hasNextPage, T result) {
		PageEntity pageEntity = new PageEntity(totalCount, totalPages, pageNumber, pageSize, isFirstPage, isLastPage, hasPreviousPage, hasNextPage);
		this.pagination = pageEntity;
		this.result = result;
	}
}
