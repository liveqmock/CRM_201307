package com.oasis.tmsv5.dao.predefinedCode;

import java.util.List;

import com.oasis.tmsv5.common.base.PredefinedCodeVO;
import com.oasis.tmsv5.common.so.PredefinedCodeSO;
import com.oasis.tmsv5.dao.DAO;
import com.oasis.tmsv5.model.base.PredefinedCode;

public interface PredefinedCodeDAO extends DAO<PredefinedCode> {

    PredefinedCodeVO getCachePredefinedCodeByCode(String code);

    List<PredefinedCodeVO> getCachePredefinedCodesByType(String type);

    List<PredefinedCodeVO> getPredefinedCodesByType(String type);

    boolean hasPredefinedCodeByTypeAndValue(String type, String value);

    List<PredefinedCode> queryPredefinedCodes(PredefinedCodeSO so);

    PredefinedCode getByValue(String value);

    List<PredefinedCode> findByType(String type);

    List<PredefinedCodeVO> getPaginatedList(PredefinedCodeSO so);

    int getPaginatedListCount(PredefinedCodeSO so);

    Boolean selCodeExist(String code);

    Boolean selTypeExist(String type);

    List<PredefinedCode> selListByCode(String code);

    List<PredefinedCodeVO> getTypeList(PredefinedCodeSO so);

    int getTypeListCount(PredefinedCodeSO so);

    int deleteBySO(PredefinedCodeSO so);
    
    int deleteByType(String type);

    PredefinedCode getByTypeAndValue(String type, String value);

    PredefinedCode getByTypeAndCode(String type, String code);

    PredefinedCode getByTypeAndDescription(String type, String description);

    PredefinedCode getByStringTypeAndValue(String type, String value);
    //检查类型是否存在
    List<PredefinedCode> checkIfTypeExist(String type);
}
