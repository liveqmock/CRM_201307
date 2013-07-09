/**
 * 
 */
package com.oasis.tmsv5.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.oasis.tmsv5.common.util.page.BasePageSO;
import com.oasis.tmsv5.util.query.Cache;

/**
 * <p>
 * 
 * @author liyue
 * @date 2013-5-7 下午05:23:06
 * @version v1.0
 */
public interface BaseDAO<T extends Serializable> {

    @Cache(action = Cache.FIND)
    T find(Long id);

    @Cache(action = Cache.UPDATE)
    T update(T paraObject);

    Long insert(T paraObject);

    @Cache(action = Cache.DELETE)
    int delete(Long id);

    <E> List<E> getQueryList(BasePageSO bpo);

    <E> List<E> getQueryList(@SuppressWarnings("rawtypes") Map inputParameter);

    T getModelByPara(Map<String, Object> map);

    List<T> getModelListByPara(Map<String, Object> map);

    List<Long> batchInsert(List<T> list);

    @Cache(action = Cache.DEL_LIST)
    int deleteByIds(List<Long> ids);

    <E> List<E> getListByIds(List<Long> ids);

    List<T> getListByFKId(Long fkId);
    
}
