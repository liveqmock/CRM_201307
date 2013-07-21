/**
 * 
 */
package com.beyond.crm.dao;

import java.util.List;

import com.beyond.common.base.BaseDao;
import com.beyond.common.so.CrmActivitySO;
import com.beyond.common.vo.CrmActivityVO;
import com.beyond.crm.bean.CrmActivity;

/**
 * @author liyue
 * 
 */
public interface CrmActivityDao extends BaseDao<CrmActivity> {

	List<CrmActivityVO> queryByParam(CrmActivitySO so);
}
