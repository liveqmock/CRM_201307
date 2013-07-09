package com.oasis.tmsv5.dao.base;

import org.springframework.stereotype.Repository;

import com.oasis.tmsv5.dao.DefaultBaseDAOImpl;
import com.oasis.tmsv5.model.base.Domain;

@Repository
public class DomainDAOImpl extends DefaultBaseDAOImpl<Domain> implements DomainDAO {
}
