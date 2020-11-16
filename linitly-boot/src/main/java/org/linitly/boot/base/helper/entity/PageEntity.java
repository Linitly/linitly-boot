package org.linitly.boot.base.helper.entity;

import lombok.Data;

/**
 * @Description 分页实体工具类
 * @author linxiunan
 * @date 2019年1月7日
 */
@Data
public class PageEntity {

	// 分页查询时数据总数
	public long totalCount;
	
	// 总页数
	public Integer totalPages;
	
	// 当前页
	public Integer pageNumber;
	
	// 当前页的数量
	public Integer pageSize;
	
	//是否为第一页
    public boolean isFirstPage = false;
    
    //是否为最后一页
    public boolean isLastPage = false;
    
    //是否有前一页
    public boolean hasPreviousPage = false;
    
    //是否有下一页
    public boolean hasNextPage = false;

    public PageEntity() {}
    
    public PageEntity(long totalCount) {
		this.totalCount = totalCount;
	}
	
	public PageEntity(long totalCount, int totalPages) {
		this.totalCount = totalCount;
		this.totalPages = totalPages;
	}
	
	public PageEntity(long totalCount, int totalPages, int pageNumber, int pageSize) {
		this.totalCount = totalCount;
		this.totalPages = totalPages;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
	}
	
	public PageEntity(long totalCount, int totalPages, int pageNumber, int pageSize,
			boolean isFirstPage, boolean isLastPage, boolean hasPreviousPage, boolean hasNextPage) {
		this.totalCount = totalCount;
		this.totalPages = totalPages;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.isFirstPage = isFirstPage;
		this.isLastPage = isLastPage;
		this.hasPreviousPage = hasPreviousPage;
		this.hasNextPage = hasNextPage;
	}
}
