package com.beyond.crm.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.beyond.common.base.AbstractBaseDao;
import com.beyond.crm.bean.CrmCustomer;
import com.beyond.crm.dao.CrmCustomerDao;


@Repository
public class CrmCustomerDaoImpl extends AbstractBaseDao<CrmCustomer> implements CrmCustomerDao{
	
	public List<CrmCustomer> findCustomerPage(CrmCustomer bean,Integer pageNum, Integer pageSize) {
		return getObjectList("CrmCustomerSQL.findCustomerPage", pageNum, pageSize, bean,"customber");
	}	

	
	public CrmCustomer saveCustomer(CrmCustomer bean){
		getSqlMapClientTemplate().insert("CrmCustomerSQL.saveCustomer",bean);
    	return bean;
	}
	
	public CrmCustomer updateCustomer(CrmCustomer bean){
		getSqlMapClientTemplate().update("CrmCustomerSQL.updateCustomer",bean);
		return bean;
	}
	
	public void deleteCustomer(Integer custId){
		getSqlMapClientTemplate().delete("sysUserSQL.deleteCustomer",custId);
	}
}
