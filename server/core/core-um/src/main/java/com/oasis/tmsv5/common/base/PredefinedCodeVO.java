package com.oasis.tmsv5.common.base;

import java.util.List;

import com.oasis.tmsv5.common.vo.BaseVO;
public class PredefinedCodeVO extends BaseVO {
   
    private static final long serialVersionUID = 5642263563521074295L;

    private Long id;
    
    private String code;
    
    private String value;
    
    private String description;
    
    private String type;
    
    private String preType;
    
    private List<PredefinedCodeVO> predefinedCodeList;

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public String getPreType() {
		return preType;
	}

	public void setPreType(String preType) {
		this.preType = preType;
	}

	public List<PredefinedCodeVO> getPredefinedCodeList() {
        return predefinedCodeList;
    }

    public void setPredefinedCodeList(List<PredefinedCodeVO> predefinedCodeList) {
        this.predefinedCodeList = predefinedCodeList;
    }

}
