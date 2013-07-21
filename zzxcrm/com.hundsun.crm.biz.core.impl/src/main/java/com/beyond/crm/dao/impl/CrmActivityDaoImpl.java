/**
 * 
 */
package com.beyond.crm.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.beyond.common.base.AbstractBaseDao;
import com.beyond.common.so.CrmActivitySO;
import com.beyond.common.vo.CrmActivityVO;
import com.beyond.crm.bean.CrmActivity;
import com.beyond.crm.dao.CrmActivityDao;

/**
 * @author liyue
 *
 */
@Repository
public class CrmActivityDaoImpl extends AbstractBaseDao<CrmActivity> implements CrmActivityDao {
	
	@SuppressWarnings("unchecked")
	public List<CrmActivityVO> queryByParam(CrmActivitySO so){
		return getSqlMapClientTemplate().queryForList(getStatementNamespace()+".queryByParam", so);
	}
}
