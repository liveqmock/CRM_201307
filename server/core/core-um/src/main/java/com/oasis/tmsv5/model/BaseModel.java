package com.oasis.tmsv5.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Version;

import org.apache.commons.lang.builder.HashCodeBuilder;

public abstract class BaseModel implements Serializable {

    private static final long serialVersionUID = -6632391328936486655L;

    @Column(name = "DOMAIN_ID")
    private Long domainId;

    @Column(name = "CREATEDTIME")
    private Date createdTime;

    @Column(name = "UPDATEDTIME")
    private Date updatedTime;

    @Column(name = "CREATOR_ID")
    private Long creatorId;

    @Column(name = "UPDATOR_ID")
    private Long updatorId;

    @Version
    @Column(name = "LOCKVERSION")
    private int lockVersion = 0;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final BaseModel other = (BaseModel) obj;

        Long id1 = getId();
        Long id2 = other.getId();
        if (id1 == null) {
            return false;
        } else {
            return id1.equals(id2);
        }
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.getId()).toHashCode();
    }

    public String toString() {
        return this.getClass().getName() + "[id=" + getId() + "]";
    }

    public abstract Long getId();

    public abstract void setId(Long id);

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Long getUpdatorId() {
        return updatorId;
    }

    public void setUpdatorId(Long updatorId) {
        this.updatorId = updatorId;
    }

    public int getLockVersion() {
        return lockVersion;
    }

    public void setLockVersion(int lockVersion) {
        this.lockVersion = lockVersion;
    }

	public Long getDomainId() {
		return domainId;
	}

	public void setDomainId(Long domainId) {
		this.domainId = domainId;
	}

}
