package com.oasis.tmsv5.common.util.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BasePageSO implements Serializable{
    
    Long domainId = 60000L;
    /**
     * 
     */
    private static final long serialVersionUID = -4954347835813925828L;
    /**
     * 当前页码
     */
    private int pageNumber = 1;
    /**
     * 每页记录数 page size
     */
    private int objectsPerPage = PageUtil.defaultPageSize;
    
    /**
     * 排序字段
     */
    private List<String> orderFields;
    
    /**
     * 排序类型
     */
    private OrderType orderType = OrderType.DESC;
    /**
     * 排序类型
     */
    private List<String> orderTypes;
    
    
    /**
     * 是否需要取数据列表回来
     * 用来处理某些查询只需要返回记录总条数的情况
     */
    private boolean withoutListData = false;
    
    public BasePageSO() {
    }
    public BasePageSO(String orderFieldName) {
        orderFields = new ArrayList<String>();
        orderFields.add(orderFieldName);
    }
    public BasePageSO(String orderFieldName, OrderType orderType) {
        this.orderFields = new ArrayList<String>();
        this.orderFields.add(orderFieldName);
        this.orderType = orderType;
        this.orderTypes = new ArrayList<String>();
        if(orderType == OrderType.DESC)
        	this.orderTypes.add("DESC");
        else
        	this.orderTypes.add("ASC");
    }
    public BasePageSO(int start, int limit) {
        super();
        this.pageNumber = PageUtil.getPageNumFromStartAndLimit(start, limit);
        this.objectsPerPage = limit;
    }
    public int getPageNumber() {
        return pageNumber;
    }
    public void setPageNumber(int pageNumber) {
        if(pageNumber <= 0){
            pageNumber = 1;
        }
        this.pageNumber = pageNumber;
    }
    public int getObjectsPerPage() {
        return objectsPerPage;
    }
    public void setObjectsPerPage(int objectsPerPage) {
        this.objectsPerPage = objectsPerPage;
    }
    public List<String> getOrderFields() {
        return orderFields;
    }
    public void setOrderFields(List<String> orderFields) {
        this.orderFields = orderFields;
    }
    public OrderType getOrderType() {
        return orderType;
    }
    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
        if(this.orderTypes == null)
        	this.orderTypes = new ArrayList<String>();
        this.orderTypes.add(orderType ==  OrderType.DESC?"DESC":"ASC");
    }
    
    public List<String> getOrderTypes() {
		return orderTypes;
	}
	public void setOrderTypes(List<String> orderTypes) {
		this.orderTypes = orderTypes;
	}
	public void addOrderField(String orderField){
        if(orderFields == null){
            orderFields = new ArrayList<String>();
        }
        orderFields.add(orderField);
    }
    public boolean getWithoutListData() {
        return withoutListData;
    }
    public void setWithoutListData(boolean withoutListData) {
        this.withoutListData = withoutListData;
    }
}
