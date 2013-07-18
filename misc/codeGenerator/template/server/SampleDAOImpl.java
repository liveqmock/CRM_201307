package com.oasis.spiderman.dao.sample;

import java.util.List;


import com.oasis.spiderman.common.so.sample.SampleSO;
import com.oasis.spiderman.common.vo.sample.SampleVO;
import com.oasis.tmsv5.dao.DefaultBaseDaoImpl;
import com.oasis.spiderman.model.sample.Sample;
import org.springframework.stereotype.Repository;

@Repository
public class SampleDAOImpl extends DefaultBaseDaoImpl<Sample> implements SampleDAO {

    @Override
    public List<SampleVO> getPaginatedList(SampleSO sampleSO) {
        return super.getPaginatedList(sampleSO);
    }

    @Override
    public Integer getPaginatedListCount(SampleSO sampleSO) {
        return super.getPaginatedListCount(sampleSO);
    }

}
