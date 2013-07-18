package com.oasis.spiderman.facade.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Service;

import com.oasis.spiderman.common.so.sample.SampleSO;
import com.oasis.spiderman.common.vo.sample.SampleVO;
import com.oasis.tmsv5.common.ClientContext;
import com.oasis.tmsv5.common.util.page.PageList;
import com.oasis.spiderman.service.sample.SampleComponent;

@RemotingDestination
@Service
public class SampleServiceFacade {
    @Autowired
    private SampleComponent sampleService;
    
    public PageList<SampleVO> getPageList(ClientContext clientContext,SampleSO so)  {
        return sampleService.getPageList(so);
    }
}
