package com.oasis.spiderman.dao.sample;

import java.util.List;

import com.oasis.spiderman.common.so.sample.SampleSO;
import com.oasis.spiderman.common.vo.sample.SampleVO;
import com.oasis.tmsv5.dao.DAO;
import com.oasis.spiderman.model.sample.Sample;

public interface SampleDAO extends BaseDao<Sample>{
    
    List<SampleVO> getPaginatedList(SampleSO sampleSO);
    
    Integer getPaginatedListCount(SampleSO sampleSO);
}
