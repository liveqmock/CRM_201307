package com.oasis.spiderman.common.vo.sample;

import com.oasis.tmsv5.common.vo.BaseVO;

public class SampleVO extends BaseVO {
    private static final long serialVersionUID = 1L;
    
    private String name;
    private String remark;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
