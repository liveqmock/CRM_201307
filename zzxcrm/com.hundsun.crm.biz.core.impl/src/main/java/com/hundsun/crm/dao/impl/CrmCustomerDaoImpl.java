package com.hundsun.crm.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hundsun.beyond.common.base.AbstractBaseDao;
import com.hundsun.beyond.common.vo.PageInfo;
import com.hundsun.crm.bean.CrmCustomer;
import com.hundsun.crm.dao.CrmCustomerDao;


@Repository
public class CrmCustomerDaoImpl extends AbstractBaseDao<CrmCustomer> implements CrmCustomerDao{
	
	public PageInfo<CrmCustomer> findPage(PageInfo<CrmCustomer> pageInfo,Integer pageNum, Integer pageSize,Map<String, Object> paramMap) {
		return getPageInfoByParamMap(pageInfo,"CrmCustomerSQL.countTotal","CrmCustomerSQL.findPage", pageNum, pageSize, paramMap);
	}	

	
	public CrmCustomer saveCustomer(CrmCustomer bean){
		getSqlMapClientTemplate().insert("CrmCustomerSQL.saveCustomer",bean);
    	return bean;
	}
	
	public void updateCustomer(Integer custId){
		getSqlMapClientTemplate().update("sysUserSQL.updateUser",custId);
	}
	
	public void deleteCustomer(Integer custId){
		getSqlMapClientTemplate().delete("sysUserSQL.deleteCustomer",custId);
	}
}
