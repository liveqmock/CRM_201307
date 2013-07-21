/**
 * Project: com.hundsun.crm.plugin.cep
 * 
 * File Created at 2013-7-14
 * $Id$
 * 
 * Copyright 2011 www.6677bank.com Croporation Limited.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * 6677bank Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with 6677bank.com.
 */
package com.hundsun.crm.cep;

import java.util.Date;
import java.util.List;

import com.beyond.crm.bean.CrmCustomer;
import com.beyond.crm.service.CrmCustomerService;
import com.hundsun.crm.common.listener.AppStartListener;
import com.hundsun.crm.common.util.WrapperUtil;
import com.hundsun.crm.wrapper.anotation.JresService;
import com.hundsun.jres.interfaces.cep.context.IEventContext;
import com.hundsun.jres.interfaces.share.dataset.IDataset;
import com.hundsun.jres.interfaces.share.dataset.IDatasetMetaData;
import com.hundsun.jres.interfaces.share.dataset.IDatasets;

/**
 * 客户前台实现类
 *
 * @author zhouzx
 * @version TestCepImpl.java 2013-7-14 下午02:14:25
 *
 */
public class CustomerCepImpl {
	
	private CrmCustomerService customerService = (CrmCustomerService) AppStartListener.getApplicationContext()
	.getBean("customerService");
	

	
	@JresService(alias = "customer.serviceInterface.cust.findCustomerPage", desc = "客户列表分页", value = "customer.serviceInterface.cust.findCustomerPage")
	public IDatasets findPage(IEventContext context,
			IDataset request)  {
		int start = request.getInt("start");
		int limit = request.getInt("limit");
		CrmCustomer crmCustomer = new CrmCustomer();
		List<CrmCustomer> list = customerService.queryForPage(crmCustomer, start, limit);
		
		return WrapperUtil.createListResult(list, CrmCustomer.class, list==null?0:list.size());
	}
	@JresService(alias = "customer.serviceInterface.cust.findCustomerDetail", desc = "客户详情信息", value = "customer.service.cust.findCustomerDetail")
	public IDatasets findCustomerDetail(IEventContext context,
			IDataset request)  {
		CrmCustomer obj = getObject("周星驰","男","星爷",30);
		
		return WrapperUtil.createObjectResult(obj, CrmCustomer.class);
	}
	@JresService(alias = "customer.serviceInterface.cust.insertCustomer", desc = "新增客户详情信息", value = "customer.serviceInterface.cust.insertCustomer")
	public IDatasets insertCustomer(IEventContext context,
			IDataset request)  {
		
		String custNo = request.getString("custNo");             
		String custName = request.getString("custName");           
		String fullPinyinName = request.getString("fullPinyinName");     
		String simplePinyinName = request.getString("simplePinyinName");   
		String stockCode = request.getString("stockCode");          
		String invoiceAddress = request.getString("invoiceAddress");     
		String custHot = request.getString("custHot");           
		String custSource = request.getString("custSource");         
		String custType = request.getString("custType");           
		String employeeTotal = request.getString("employeeTotal");     
		String custRegion = request.getString("custRegion");         
		String custLevel = request.getString("custLevel");          
		String custSatisfy = request.getString("custSatisfy");        
		String custCredit = request.getString("custCredit");         
		String country = request.getString("country");            
		String province = request.getString("province");           
		String city = request.getString("city");               
		String custAddr = request.getString("custAddr");           
		String custZipCode = request.getString("custZipCode");        
		String custTel = request.getString("custTel");            
		String custFax = request.getString("custFax");            
		String custWebsite = request.getString("custWebsite");        
		String custLicenceNo = request.getString("custLicenceNo");      
		String custChieftain = request.getString("custChieftain");      
		String custBankroll = request.getString("custBankroll");         
		String custTurnover = request.getString("custTurnover");         
		String custBank = request.getString("custBank");           
		String custBankAccount = request.getString("custBankAccount");    
		String custLocalTaxNo = request.getString("custLocalTaxNo");     
		String custNationalTaxNo = request.getString("custNationalTaxNo");  
		String custStatus = request.getString("custStatus");
		CrmCustomer obj = new CrmCustomer();
		obj.setCity(city);
		obj.setCountry(country);
		obj.setCreateDate(new Date());
		obj.setCreateUserId("admin");
		obj.setCustName(custName);
		this.customerService.save(obj);
		return WrapperUtil.createSuccessResult();
	}
	@JresService(alias = "customer.serviceInterface.cust.updateCustomer", desc = "修改客户详情信息", value = "customer.service.cust.updateCustomer")
	public IDatasets updateCustomer(IEventContext context,
			IDataset request)  {
		CrmCustomer obj = getObject("周星驰","男","星爷",30);
		
		return WrapperUtil.createSuccessResult();
	}
	@JresService(alias = "customer.serviceInterface.cust.delCustomer", desc = "删除客户详情信息", value = "customer.service.cust.delCustomer")
	public IDatasets delCustomer(IEventContext context,
			IDataset request)  {
		
		return WrapperUtil.createSuccessResult();
	}

	/**
	 * @return
	 */
	private CrmCustomer getObject(String name,String sex,String nick,int age) {
		CrmCustomer item = new CrmCustomer();
		item.setCustName(name);
		item.setInvoiceAddress("杭州市");
		item.setCustAddr("是的范德萨发的");		
		return item;
	}
}
