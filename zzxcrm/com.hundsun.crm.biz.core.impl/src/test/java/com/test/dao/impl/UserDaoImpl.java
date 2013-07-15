/**
 * 
 */
package com.test.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.test.User;
import com.test.dao.UserDao;

/**
 * @author liyue
 * 
 */
@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private SqlMapClient client;

	@SuppressWarnings("unchecked")
	public List<User> findAll() throws SQLException {
		return (List<User>) client.queryForList("user.findAll");
	}
}
