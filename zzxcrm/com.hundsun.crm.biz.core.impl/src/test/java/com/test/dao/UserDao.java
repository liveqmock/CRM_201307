/**
 * 
 */
package com.test.dao;

import java.sql.SQLException;
import java.util.List;

import com.test.User;

/**
 * @author liyue
 * 
 */
public interface UserDao {

	List<User> findAll() throws SQLException;
}
