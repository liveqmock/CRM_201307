/**
 * 
 */
package com.beyond.common.base;

import java.io.Serializable;

/**
 * @author liyue
 * 
 */
public interface BaseDao<T extends Serializable> {

	T insert(T t);
	
	int update(T t);
	
	int delete(Long id);
	
	T find(Long id);
}
