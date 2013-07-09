package com.oasis.tmsv5.model.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.oasis.tmsv5.model.BaseModel;

@Table(name = "GT_PREDEFINEDCODE")
@SequenceGenerator(name = "gt_predefinedcode_seq")
public class PredefinedCode extends BaseModel {

    private static final long serialVersionUID = 5642263563521074295L;

    @Id
    private Long id;

    private String code;

    @Column(name = "value")
    private String val;

    private String description;

    private String type;

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

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
