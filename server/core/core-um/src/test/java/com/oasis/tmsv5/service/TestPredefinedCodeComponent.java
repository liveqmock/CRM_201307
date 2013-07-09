/**
 * 
 */
package com.oasis.tmsv5.service;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.oasis.tmsv5.common.base.PredefinedCodeVO;
import com.oasis.tmsv5.service.base.PredefinedCodeComponent;
import com.oasis.tmsv5.util.exception.GLException;

/**
 * <p>
 * 
 * @date 2013-7-3 上午11:29:37
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "TestAccountComponent-context.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class TestPredefinedCodeComponent {

    @Autowired
    private PredefinedCodeComponent predefiCodeComponent;

    @Test
    public void test() {
    }

    /**
     * <p>
     * 将已经在DB中的code拿出来在新增一遍，应该会出现异常，CODE重复。
     */
    @Test(expected = GLException.class)
    public void createPredefineCodeDuplicate() {
        String type = "APPROVAL_FORM_TYPE";
        List<PredefinedCodeVO> codes1 = predefiCodeComponent.findByType(type);
        int beforeSize = codes1.size();

        PredefinedCodeVO vo = new PredefinedCodeVO();
        vo.setType("APPROVAL_FORM_TYPE");
        vo.setPredefinedCodeList(codes1);

        predefiCodeComponent.createByList(vo);
        
        Assert.assertEquals(beforeSize, predefiCodeComponent.findByType(type).size());
    }
    
    @Test()
    public void createPredefineCode(){
        String type = "APPROVAL_FORM_TYPE";
        List<PredefinedCodeVO> codes1 = predefiCodeComponent.findByType(type);
        int beforeSize = codes1.size();

        PredefinedCodeVO vo = new PredefinedCodeVO();
        vo.setType("APPROVAL_FORM_TYPE");
        PredefinedCodeVO vo1 = new PredefinedCodeVO();
        vo1.setCode("TEST_APPROVAL_FORM_1");
        vo1.setValue("TEST_APPROVAL_FORM_1");
        PredefinedCodeVO vo2 = new PredefinedCodeVO();
        vo2.setCode("TEST_APPROVAL_FORM_1");
        vo2.setValue("TEST_APPROVAL_FORM_1");
        codes1.clear();
        codes1.add(vo1);
        codes1.add(vo2);
        vo.setPredefinedCodeList(codes1);

        predefiCodeComponent.createByList(vo);
        
        Assert.assertEquals(beforeSize + 2, predefiCodeComponent.findByType(type).size());
    }

    @Test
    public void updatePredefineCode(){
        String type = "test002";
        List<PredefinedCodeVO> codes1 = predefiCodeComponent.findByType(type);
        int beforeSize = codes1.size();

        PredefinedCodeVO vo = new PredefinedCodeVO();
        vo.setType("test002");
        vo.setPredefinedCodeList(codes1);
        
        PredefinedCodeVO vo1 = new PredefinedCodeVO();
        vo1.setCode("TEST_APPROVAL_1");
        vo1.setValue("TEST_APPROVAL_1");
        
        PredefinedCodeVO vo2 = new PredefinedCodeVO();
        vo2.setCode("TEST_APPROVAL_1");
        vo2.setValue("TEST_APPROVAL_1");
        vo.getPredefinedCodeList().add(vo1);
        vo.getPredefinedCodeList().add(vo2);

        predefiCodeComponent.updateByList(vo);
        
        Assert.assertEquals(beforeSize + 2, predefiCodeComponent.findByType(type).size());
    }
    
    @Test
    @Rollback(true)
    public void removePredefinCode() {
        List<Long> ids = new ArrayList<Long>();
        ids.add(1L);
        predefiCodeComponent.removePredefinedCodeByIds(ids);
    }

    @Test
    @Rollback(true)
    public void deleteByType() {
        String type = "APPROVAL_FORM_TYPE";
        List<PredefinedCodeVO> codes1 = predefiCodeComponent.findByType(type);
        Assert.assertNotSame(0, codes1.size());

        predefiCodeComponent.deleteCodeByType(type);

        List<PredefinedCodeVO> codes2 = predefiCodeComponent.findByType(type);
        Assert.assertEquals(0, codes2.size());
    }
    
}
