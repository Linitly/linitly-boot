package org.linitly.boot.base.utils.page;

import org.linitly.boot.base.helper.entity.PageEntity;
import org.linitly.boot.base.helper.entity.PageResponseResult;

import java.util.List;

/**
 * @author: linxiunan
 * @date: 2020/5/6 15:56
 * @descrption: 分页结果工具类(适用于集合数据量小的方式):数据库一对多查询时，会造成分页信息错误
 */
public class PageResponseResultUtil {

    public static <T> PageResponseResult getPageResponseResult(List<T> list, int pageNumber, int pageSize) {
        PageEntity pageEntity = getPageEntity(list, pageNumber, pageSize);
        pageNumber = pageNumber * pageSize > pageEntity.getTotalCount() ? pageEntity.getTotalPages() : pageNumber;
        list = list.subList((pageNumber - 1) * pageSize, pageNumber * pageSize > pageEntity.getTotalCount() ? list.size() : pageNumber * pageSize);
        return new PageResponseResult<>(pageEntity, list);
    }

    private static <T> PageEntity getPageEntity(List<T> list, int pageNumber, int pageSize) {
        PageEntity pageEntity = new PageEntity();
        if (list == null) {
            pageEntity.setPageNumber(0);
            pageEntity.setPageSize(pageSize);
            pageEntity.setTotalCount(0);
            return pageEntity;
        }
        long totalCount = list.size();
        long totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;

        pageEntity.setPageNumber(pageNumber > totalPage ? (int)totalPage : pageNumber);
        pageEntity.setPageSize(pageSize);
        pageEntity.setTotalCount(totalCount);
        pageEntity.setLastPage(pageNumber >= totalPage);
        pageEntity.setTotalPages((int)totalPage);
        pageEntity.setHasNextPage(totalPage > pageNumber);
        pageEntity.setFirstPage(totalPage == 1);
        pageEntity.setHasPreviousPage(pageNumber > 1 && totalPage > 1);
        return pageEntity;
    }
}
