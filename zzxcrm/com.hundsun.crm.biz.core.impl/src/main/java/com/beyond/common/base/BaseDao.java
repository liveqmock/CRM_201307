/**
 * 
 */
package com.beyond.common.base;

import java.io.Serializable;
import java.util.List;

/**
 * @author liyue
 * 
 */
public interface BaseDao<T extends Serializable> {

	int insert(T t);

	T update(T t);

	int delete(Integer id);

	T find(Integer id);

	void batchInsert(List<T> paramObjects);

	int deleteByIds(List<Long> ids);
}
