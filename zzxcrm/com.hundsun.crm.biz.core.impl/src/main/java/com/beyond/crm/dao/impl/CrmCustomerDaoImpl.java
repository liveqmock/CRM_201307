package com.beyond.crm.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.beyond.common.base.AbstractBaseDao;
import com.beyond.crm.bean.CrmCustomer;
import com.beyond.crm.dao.CrmCustomerDao;


@Repository
public class CrmCustomerDaoImpl extends AbstractBaseDao<CrmCustomer> implements CrmCustomerDao{
	
	public List<CrmCustomer> queryForPage(CrmCustomer bean,Integer pageNum, Integer pageSize) {
		return getObjectList(getStatementNamespace()+".findCustomerPage", pageNum, pageSize, bean,"customber");
	}	

	
}
