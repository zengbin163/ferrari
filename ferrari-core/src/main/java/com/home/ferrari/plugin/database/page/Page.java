package com.home.ferrari.plugin.database.page;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class Page<T> implements Serializable {

    private static final long serialVersionUID = 8771606345010713187L;
 
    public static final String ASC = "asc";
    public static final String DESC = "desc";
    public static final Integer DEFAULT_SUM = 0;
    public static final Integer DEFAULT_PAGE_SIZE = 100;
    public static final String ORDER_BY_ID = "id";
    public static final String ORDER_BY_CREATE_TIME = "create_time";
    public static final String ORDER_BY_MODIFY_TIME = "modify_time";
    
    protected int pageNo = 0;
    protected int pageSize = 15;
    protected String orderBy = null;
    protected String order = null;
    protected boolean autoCount = true;
    protected long totalCount = -1;
    
    
    public Page() {
    }
    
    public Page(int pageSize) {
        this.pageSize = pageSize;
    }
    
    public Page(Integer pageNo, Integer pageSize){
    	this.pageNo = pageNo;
    	this.pageSize = pageSize;
    }

    public Page(Integer pageNo, Integer pageSize, String order, String orderBy){
    	this.pageNo = pageNo;
    	this.pageSize = pageSize;
    	this.order = order;
    	this.orderBy = orderBy;
    }
    
    /**
     * 获得当前页的页号,序号从1开始,默认为1.
     */
    public int getPageNo() {
        return pageNo;
    }
    
    /**
     * 设置当前页的页号,序号从0开始，小于0时自动调整为0.
     */
    public void setPageNo(final int pageNo) {
        this.pageNo = pageNo;
        
        if (pageNo < 0) {
            this.pageNo = 0;
        }
    }
    
    /**
     * 获得每页的记录数量,默认为1.
     */
    public int getPageSize() {
        return pageSize;
    }
    
    /**
     * 设置每页的记录数量,低于1时自动调整为1.
     */
    public void setPageSize(final int pageSize) {
        this.pageSize = pageSize;
        
        if (pageSize < 1) {
            this.pageSize = 1;
        }
    }
    
    /**
     * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从1开始.
     */
    public int getFirst() {
        return (pageNo == 0) ? 0 : (((pageNo - 1) * pageSize));
    }
    
    /**
     * 获得排序字段,无默认值.多个排序字段时用','分隔.
     */
    public String getOrderBy() {
        return orderBy;
    }
    
    /**
     * 设置排序字段,多个排序字段时用','分隔.
     */
    public void setOrderBy(final String orderBy) {
        this.orderBy = orderBy;
    }
    
    /**
     * 获得排序方向.
     */
    public String getOrder() {
        return order;
    }
    
    /**
     * 设置排序方式向.
     * 
     * @param order 可选值为desc或asc,多个排序字段时用','分隔.
     */
    public void setOrder(final String order) {
        // 检查order字符串的合法值
        String[] orders = StringUtils.split(StringUtils.lowerCase(order), ',');
        for (String orderStr : orders) {
            if (!StringUtils.equals(DESC, orderStr) && !StringUtils.equals(ASC, orderStr)) {
                throw new IllegalArgumentException("排序方向" + orderStr + "不是合法值");
            }
        }
        
        this.order = StringUtils.lowerCase(order);
    }
    
    /**
     * 是否已设置排序字段,无默认值.
     */
    public boolean isOrderBySetted() {
        return (StringUtils.isNotBlank(orderBy) && StringUtils.isNotBlank(order));
    }
    
    /**
     * 查询对象时是否自动另外执行count查询获取总记录数, 默认为false.
     */
    public boolean isAutoCount() {
        return autoCount;
    }
    
    /**
     * 查询对象时是否自动另外执行count查询获取总记录数.
     */
    public void setAutoCount(final boolean autoCount) {
        this.autoCount = autoCount;
    }
    
    /**
     * 取得总记录数, 默认值为-1.
     */
    public long getTotalCount() {
        return totalCount;
    }
    
    /**
     * 设置总记录数.
     */
    public void setTotalCount(final long totalCount) {
        this.totalCount = totalCount;
        
        if (totalCount < pageSize * pageNo)
            pageNo = (int)getTotalPages();
    }
    
    /**
     * 根据pageSize与totalCount计算总页数, 默认值为-1.
     */
    public long getTotalPages() {
        if (totalCount < 0) {
            return -1;
        }
        
        long count = totalCount / pageSize;
        if (totalCount % pageSize > 0) {
            count++;
        }
        if (count < 1)
            count = 1;
        return count;
    }
    
    /**
     * 是否还有下一页.
     */
    public boolean isHasNext() {
        return (pageNo + 1 <= getTotalPages());
    }
    
    /**
     * 取得下页的页号, 序号从1开始.
     * 当前页为尾页时仍返回尾页序号.
     */
    public int getNextPage() {
        if (isHasNext()) {
            return pageNo + 1;
        } else {
            return pageNo;
        }
    }
    
    /**
     * 是否还有上一页.
     */
    public boolean isHasPre() {
        return (pageNo - 1 >= 1);
    }
    
    /**
     * 取得上页的页号, 序号从1开始.
     * 当前页为首页时返回首页序号.
     */
    public int getPrePage() {
        if (isHasPre()) {
            return pageNo - 1;
        } else {
            return pageNo;
        }
    }
    
    /**
     * 返回总的页数
     * @param sum
     * @return
     */
    public static Integer getTotalPages(Integer sum) {
    	return sum % DEFAULT_PAGE_SIZE == 0 ? sum / DEFAULT_PAGE_SIZE : sum / DEFAULT_PAGE_SIZE + 1;
    }
}