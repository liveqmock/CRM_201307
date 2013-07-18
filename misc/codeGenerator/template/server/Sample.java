package com.oasis.spiderman.model.sample;

import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.oasis.tmsv5.model.BaseModel;

@Table(name="wl_sample")
@SequenceGenerator(name = "wl_sample_seq")
public class Sample extends BaseModel {
    private static final long serialVersionUID = 1L;
    
    /**
     * id
     */
    @Id
    private Long id;
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
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    
}
