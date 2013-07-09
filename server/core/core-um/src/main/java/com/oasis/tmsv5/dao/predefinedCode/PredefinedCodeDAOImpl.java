package com.oasis.tmsv5.dao.predefinedCode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.oasis.tmsv5.common.base.PredefinedCodeVO;
import com.oasis.tmsv5.common.enums.type.PredefinedCodeType;
import com.oasis.tmsv5.common.so.PredefinedCodeSO;
import com.oasis.tmsv5.dao.DefaultBaseDAOImpl;
import com.oasis.tmsv5.model.base.PredefinedCode;

@Repository
public class PredefinedCodeDAOImpl extends DefaultBaseDAOImpl<PredefinedCode> implements PredefinedCodeDAO {

    private static final String FIND_PARA = ".findByPara";
    private static final String GETLIST_PARA = ".getListByPara";
    private static final String GETLIST_CODE = ".getListByCode";
    private static final String SELECT_PAGELIST = ".selectPageList";
    private static final String FIND_BY_TYPE = ".findByType";
    private static final String SEL_EXIST = ".selExist";
    private static final String GET_TYPE_LIST = ".getTypeList";
    private static final String CHECK_IF_TYPE_EXIST= ".checkIfTypeExist";
    private static final String DELETE_BY = ".deleteBy";
    private static final String GETLIST_DESCRIPTION = ".getListDescription";
    private static Map<String, PredefinedCodeVO> codeKeyCacheMap = new ConcurrentHashMap<String, PredefinedCodeVO>();

    private static Map<String, List<PredefinedCodeVO>> typeKeyCacheMap = new ConcurrentHashMap<String, List<PredefinedCodeVO>>();

    private static final int REFRESH_TIME = 60;

    private static Date loadTime = Calendar.getInstance().getTime();

    private static final Log logger = LogFactory.getLog(PredefinedCodeDAOImpl.class);

    @Override
    public PredefinedCodeVO getCachePredefinedCodeByCode(String code) {
        if(StringUtils.isNotEmpty(code)){
            if (codeKeyCacheMap.isEmpty() || isLoadTime() || codeKeyCacheMap.get(code) == null) {
                logger.info("not in cache : " + code);
                putAllPredefinedCodeToMap();
            }
            return codeKeyCacheMap.get(code);
        }
        return null;
    }

    public List<PredefinedCodeVO> getCachePredefinedCodesByType(String type) {
        if (typeKeyCacheMap.isEmpty() || isLoadTime() || typeKeyCacheMap.get(type.toString()) == null) {

            putAllPredefinedCodeToMap();
        }
        return typeKeyCacheMap.get(type.toString());
    }
    
    @SuppressWarnings("unchecked")
    public List<PredefinedCodeVO> getPredefinedCodesByType(String type) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("type", type);
        buildMap(map);
        List<PredefinedCodeVO> list = (List<PredefinedCodeVO>)super.getSession().selectList(getStatementNamespace() + SELECT_PAGELIST, map);
        return list;
    }

    @SuppressWarnings("unchecked")
    private void putAllPredefinedCodeToMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        buildMap(map);
        List<PredefinedCodeVO> ret =(List<PredefinedCodeVO>)super.getSession().selectList(getStatementNamespace() + SELECT_PAGELIST, map);
        for (PredefinedCodeVO prd : ret) {
            /**
             * full codeKeyCacheMap
             */
            codeKeyCacheMap.put(prd.getCode(), prd);
            /**
             * full typeKeyCacheMap
             */
            List<PredefinedCodeVO> typeCodeList = typeKeyCacheMap.get(prd.getType());
            if (typeCodeList == null) {
                typeCodeList = new ArrayList<PredefinedCodeVO>();
                typeCodeList.add(prd);
            } else {
                boolean have = false;
                 for (PredefinedCodeVO prdVO :typeCodeList) {
                    if (prdVO.getId().equals(prd.getId()) ) {
                        have = true;
                    }
                 }
                 if (!have) {
                     typeCodeList.add(prd);
                 }
            }
            typeKeyCacheMap.put(prd.getType().toString(), typeCodeList);
        }
        loadTime = Calendar.getInstance().getTime();
        logger.info("PredefinedCode relaoded.");
    }

    public boolean hasPredefinedCodeByTypeAndValue(String type, String value) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("type", type);
        map.put("value", value);
        buildMap(map);
        @SuppressWarnings("unchecked")
        List<PredefinedCode> res = (List<PredefinedCode>) super.getSession().selectList(getStatementNamespace() + GETLIST_PARA, map);
        if (res.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    @SuppressWarnings("unchecked")
    public List<PredefinedCode> queryPredefinedCodes(PredefinedCodeSO so) {
        return (List<PredefinedCode>) super.getSession().selectList(super.getStatementNamespace() + FIND_PARA, so);
    }

    private boolean isLoadTime() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, REFRESH_TIME * (-1));
        if (cal.getTime().after(loadTime)) {
            return true;
        }
        return false;
    }
    public static void main(String... s) {
        loadTime=Calendar.getInstance().getTime();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, REFRESH_TIME * (-1));
        System.out.print(cal.getTime());
        if (cal.getTime().after(loadTime)) {
            System.out.print(true);
        }
    }

    @Override
    public PredefinedCode getByValue(String value) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("type", PredefinedCodeType.TRUCK_BRAND.name());
        map.put("value", value);
        buildMap(map);
        PredefinedCode res = (PredefinedCode)super.getSession().selectOne(getStatementNamespace() + GETLIST_PARA, map);
        return res;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PredefinedCode> findByType(String type) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("type", type);
        buildMap(map);
        List<PredefinedCode> res = (List<PredefinedCode>)super.getSession().selectList(getStatementNamespace() + FIND_BY_TYPE, map);
        return res;
    }
    
    @Override
    public List<PredefinedCodeVO> getPaginatedList(PredefinedCodeSO so) {
        return super.getPaginatedList(so);
    }
    
    @Override
    public int getPaginatedListCount(PredefinedCodeSO so) {
        return super.getPaginatedListCount(so);
    }
    
    /**
     * 查询code是否存在
     */
    @SuppressWarnings("unchecked")
    @Override
    public Boolean selCodeExist(String code){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", code);
        buildMap(map);
        List<PredefinedCode> res = (List<PredefinedCode>)super.getSession().selectList(getStatementNamespace() + SEL_EXIST, map);
        return res.size() > 0 ? true : false;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public Boolean selTypeExist(String type){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", type);
        buildMap(map);
        List<PredefinedCode> res = (List<PredefinedCode>)super.getSession().selectList(getStatementNamespace() + SEL_EXIST, map);
        return res.size() > 0 ? true : false;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<PredefinedCode> selListByCode(String code){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", code);
        buildMap(map);
        List<PredefinedCode> res = (List<PredefinedCode>)super.getSession().selectList(getStatementNamespace() + SEL_EXIST, map);
        return res;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PredefinedCodeVO> getTypeList(PredefinedCodeSO so) {
        List<PredefinedCodeVO> res = (List<PredefinedCodeVO>)super.getSession().selectList(getStatementNamespace() + GET_TYPE_LIST,so);
        return res;
    }

    @SuppressWarnings("unchecked")
    @Override
    public int getTypeListCount(PredefinedCodeSO so) {
        List<PredefinedCodeVO> res = (List<PredefinedCodeVO>)super.getSession().selectList(getStatementNamespace() + GET_TYPE_LIST,so);
        if(res!=null){
            return res.size();
        }
        return 0;
    }

    @Override
    public int deleteBySO(PredefinedCodeSO so) {
        return super.getSession().delete(getStatementNamespace() + DELETE_BY, so);
    }
    
    
    public int deleteByType(String type){
        return getSession().delete(getStatementNamespace() + ".deleteByType", type);
    }

    @Override
    public PredefinedCode getByTypeAndValue(String type, String value) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("type", type);
        map.put("value", value);
        buildMap(map);
        @SuppressWarnings("unchecked")
        List<PredefinedCode> res = (List<PredefinedCode>) super.getSession().selectList(getStatementNamespace() + GETLIST_PARA, map);
        if(res != null && res.size() > 0){
            return res.get(0);
        } else {
            return null;
        }
    }
    
    @Override
    public PredefinedCode getByTypeAndCode(String type, String code) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("type", type);
        map.put("code", code);
        buildMap(map);
        @SuppressWarnings("unchecked")
        List<PredefinedCode> res = (List<PredefinedCode>) super.getSession().selectList(getStatementNamespace() + GETLIST_CODE, map);
        if(res != null && res.size() > 0){
            return res.get(0);
        } else {
            return null;
        }
    }
    @Override
    public PredefinedCode getByTypeAndDescription(String type, String description) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("type", type);
        map.put("description", description);
        buildMap(map);
        @SuppressWarnings("unchecked")
        List<PredefinedCode> res = (List<PredefinedCode>) super.getSession().selectList(getStatementNamespace() + GETLIST_DESCRIPTION, map);
        if(res != null && res.size() > 0){
            return res.get(0);
        } else {
            return null;
        }
    }
    @Override
    public PredefinedCode getByStringTypeAndValue(String type, String value) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("type", type);
        map.put("value", value);
        buildMap(map);
        @SuppressWarnings("unchecked")
        List<PredefinedCode> res = (List<PredefinedCode>) super.getSession().selectList(getStatementNamespace() + GETLIST_PARA, map);
        if(res != null && res.size() > 0){
            return res.get(0);
        } else {
            return null;
        }
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<PredefinedCode> checkIfTypeExist(String type) {
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("type", type);
        return (List<PredefinedCode>) super.getSession().selectList(getStatementNamespace() + CHECK_IF_TYPE_EXIST, map);
        
	}
}
