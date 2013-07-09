package com.oasis.tmsv5.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Table(name = "TEST_MODEL")
@SequenceGenerator(name = "SEQ_TEST_MODEL")
public class TestModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    private String name;

    @Column(name = "REF_ID")
    private Long refId;

    @Column(name = "BIRTH_DATE")
    private Date birthDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getRefId() {
        return refId;
    }

    public void setRefId(Long refId) {
        this.refId = refId;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

}
