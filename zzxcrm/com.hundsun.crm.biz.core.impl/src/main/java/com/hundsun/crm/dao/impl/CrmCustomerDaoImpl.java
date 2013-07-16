package com.hundsun.crm.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hundsun.crm.bean.User;
import com.hundsun.crm.dao.CrmCustomerDao;
import com.ibatis.sqlmap.client.SqlMapClient;


@Repository
public class CrmCustomerDaoImpl implements CrmCustomerDao{
	@Autowired
	private SqlMapClient client;

	@SuppressWarnings("unchecked")
	public List<User> findAll() throws SQLException {
		return (List<User>) client.queryForList("user.findAll");
	}
}
