package com.oasis.tmsv5.dao.asso;

import java.util.List;

import com.oasis.tmsv5.common.enums.type.AssociateTable;

public interface AssociateDAO {
	void batchInsert(AssociateTable table, Long assocCol, List<Long> inverseCols);
	
	void batchInsertByString(AssociateTable table, Long assocCol, List<String> inverseCols);
	
	int  deleteByAssoc(AssociateTable table, Long id);
	
	int deleteByAssocIds(AssociateTable table, List<Long> ids);
	
	List<?> selectListByAssoc(AssociateTable table, Long id);
	
    int deleteByAssocString(AssociateTable table, String str) ;
}
