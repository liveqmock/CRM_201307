package com.oasis.spiderman.service.sample;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oasis.spiderman.common.so.sample.SampleSO;
import com.oasis.spiderman.common.vo.sample.SampleVO;
import com.oasis.spiderman.dao.sample.SampleDAO;
import com.oasis.tmsv5.common.util.page.PageList;
import com.oasis.tmsv5.service.BaseComponent;

@Service
public class SampleComponent extends BaseComponent {
    @Autowired
    private SampleDAO sampleDAO;

    public PageList<SampleVO> getPageList(SampleSO so) {
        List<SampleVO> sampleList = sampleDAO.getPaginatedList(so);
        int cnt = sampleDAO.getPaginatedListCount(so);
        PageList<SampleVO> page = new PageList<SampleVO>();
        page.setList(sampleList);
        page.setFullListSize(cnt);
        return page;
    }
}
