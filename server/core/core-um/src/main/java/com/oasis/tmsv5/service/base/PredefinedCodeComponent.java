package com.oasis.tmsv5.service.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.oasis.tmsv5.common.base.PredefinedCodeVO;
import com.oasis.tmsv5.common.so.PredefinedCodeSO;
import com.oasis.tmsv5.common.util.page.PageList;
import com.oasis.tmsv5.dao.predefinedCode.PredefinedCodeDAO;
import com.oasis.tmsv5.model.base.PredefinedCode;
import com.oasis.tmsv5.common.enums.type.PredefinedCodeType;
import com.oasis.tmsv5.service.BaseComponent;
import com.oasis.tmsv5.service.helper.ErrorDispHelper;
import com.oasis.tmsv5.util.exception.GLException;
import com.oasis.tmsv5.util.exception.ValidationException;

@Service
@Transactional
public class PredefinedCodeComponent extends BaseComponent {

    @Autowired
    private PredefinedCodeDAO predefinedCodeDAO;

    public PredefinedCodeVO getPredefinedCodesByCode(String code) {
        PredefinedCodeVO predefinedCode = predefinedCodeDAO.getCachePredefinedCodeByCode(code);
        return predefinedCode;
    }

    public List<PredefinedCodeVO> queryPredefinedCodes(PredefinedCodeSO so) {
        List<PredefinedCode> list = predefinedCodeDAO.queryPredefinedCodes(so);
        List<PredefinedCodeVO> result = getDozer().convertList(list, PredefinedCodeVO.class);
        return result;
    }

    public Boolean create(PredefinedCodeVO vo) {
        checkBrand(vo.getValue());
        PredefinedCode predefinedCode = super.getDozer().convert(vo, PredefinedCode.class);
        Long id = predefinedCodeDAO.insert(predefinedCode);

        predefinedCode.setId(id);
        String code = id.toString();
        code = StringUtils.leftPad(code, 3, "0");
        predefinedCode.setCode("prd" + code);
        predefinedCodeDAO.update(predefinedCode);
        return true;
    }

    /**
     * 
     * reviewer:中文
     */
    private void checkBrand(String brand) {
        Map<String, String> errors = new HashMap<String, String>();
        boolean flag = predefinedCodeDAO.hasPredefinedCodeByTypeAndValue(PredefinedCodeType.TRUCK_BRAND.name(), brand);
        if (flag) {
            errors.put("val", ErrorDispHelper.getInstance().getValue("BRAND_ERROR"));
            throw new ValidationException(errors);
        }
    }

    /**
     * 根据type取list
     * 
     * @param type
     * @return
     */
    public List<PredefinedCodeVO> findByType(String type) {
        List<PredefinedCode> list = predefinedCodeDAO.findByType(type);
        List<PredefinedCodeVO> result = dozer.convertList(list, PredefinedCodeVO.class);
        return result;
    }

    /**
     * 根据type取list
     * 
     * @param type
     * @return
     */
    public List<PredefinedCodeVO> findByTypes(List<String> types) {
        List<PredefinedCodeVO> res = new ArrayList<PredefinedCodeVO>();
        if (types != null && types.size() > 0) {
            List<PredefinedCodeVO> list;
            for (String type : types) {
                list = findByType(type);
                if (list != null && list.size() > 0) {
                    res.addAll(list);
                }
            }

        }
        return res;
    }

    public PageList<PredefinedCodeVO> getPageList(PredefinedCodeSO so) {
        int count = predefinedCodeDAO.getPaginatedListCount(so);
        List<PredefinedCodeVO> priceRouteList = predefinedCodeDAO.getPaginatedList(so);
        PageList<PredefinedCodeVO> pageList = new PageList<PredefinedCodeVO>(so);
        pageList.setFullListSize(count);
        pageList.setList(priceRouteList);
        return pageList;
    }

    public PageList<PredefinedCodeVO> getTypeList(PredefinedCodeSO so) {
        int count = predefinedCodeDAO.getTypeListCount(so);
        List<PredefinedCodeVO> typeList = predefinedCodeDAO.getTypeList(so);
        PageList<PredefinedCodeVO> pageList = new PageList<PredefinedCodeVO>(so);
        pageList.setFullListSize(count);
        pageList.setList(typeList);
        return pageList;
    }

    public void createPredefinedCode(PredefinedCodeVO vo) {
        if (vo.getCode() == null) {
            throw new ValidationException("Code can not be null!");
        }
        if (predefinedCodeDAO.selCodeExist(vo.getCode())) {
            throw new ValidationException("Code is already exist!");
        }
        predefinedCodeDAO.insert(super.getDozer().convert(vo, PredefinedCode.class));
    }

    public Boolean createByList(PredefinedCodeVO vo) {
    	//判断type是否存在
    	if(predefinedCodeDAO.checkIfTypeExist(vo.getType()) != null &&
    			predefinedCodeDAO.checkIfTypeExist(vo.getType()).size() > 1){
    		throw new GLException("该类型的预定义编码已经存在！");
    	}
        return createList(vo);
    }
    
    public Boolean createList(PredefinedCodeVO vo){
    	List<PredefinedCodeVO> createList = vo.getPredefinedCodeList();
    	if (CollectionUtils.isEmpty(createList)) {
            throw new GLException("没有输入预定义参数。");
        }
        for (PredefinedCodeVO predefinedCodeVO : createList) {
            if (predefinedCodeDAO.selCodeExist(predefinedCodeVO.getCode())) {
                throw new GLException("CODE:" + predefinedCodeVO.getCode() + " is duplicated.");
            }
            // must have code & value
            if (StringUtils.isNotBlank(predefinedCodeVO.getCode()) && StringUtils.isNotBlank(predefinedCodeVO.getValue())) {
                predefinedCodeVO.setType(vo.getType());
                predefinedCodeVO.setDomainId(vo.getDomainId());
                predefinedCodeDAO.insert(super.getDozer().convert(predefinedCodeVO, PredefinedCode.class));
            }
        }
        return true;
    }
    
    public Boolean updatePredefinedCode(PredefinedCodeVO vo) {
        List<PredefinedCode> res = predefinedCodeDAO.selListByCode(vo.getCode());
        if (res.size() != 0) {
            PredefinedCode pc = res.get(0);
            if (!pc.getId().equals(vo.getId())) {
                return false;
            }
        }
        predefinedCodeDAO.update(super.getDozer().convert(vo, PredefinedCode.class));
        return true;
    }

    public Boolean updateByList(PredefinedCodeVO vo) {
        List<PredefinedCode> updateList = getDozer().convertList(vo.getPredefinedCodeList(), PredefinedCode.class);

        if (CollectionUtils.isEmpty(updateList)) {
            throw new GLException("没有输入预定义参数。");
        }
        
        //编辑自身
        if(vo.getType().equals(vo.getPreType())){
        	//删除该类型所有的预定义编码
	        predefinedCodeDAO.deleteByType(vo.getType());
	        //添加新的预定义编码
	        return createList(vo);
        } else {
        	//判断编辑的类型是否存在
            List<PredefinedCode> typeList = predefinedCodeDAO.checkIfTypeExist(vo.getType());
            if(typeList != null && typeList.size() > 1){
            	throw new GLException("该类型的预定义编码已经存在！");
            }
            predefinedCodeDAO.deleteByType(vo.getPreType());
            return createList(vo);
        }
    }

    public int removePredefinedCodeByIds(List<Long> ids) {
        return predefinedCodeDAO.deleteByIds(ids);
    }

    public int deleteCodeByType(String type) {
        return predefinedCodeDAO.deleteByType(type);
    }

}