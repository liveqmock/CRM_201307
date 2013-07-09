/**
 * 
 */
package com.oasis.tmsv5.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.oasis.tmsv5.common.vo.security.AccountVO;
import com.oasis.tmsv5.service.account.AccountComponent;
import com.oasis.tmsv5.util.tools.EncodeUtil;

/**
 * <p>
 *
 * @date 2013-7-1 下午04:19:00
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "TestAccountComponent-context.xml" })
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)
@Transactional
public class TestAccountComponent {
    
    @Autowired
    private AccountComponent accountComponent;
    
    /**
     * <p> 
     * 测试同名的用户是否可能被创建
     */
    @Test(expected = Exception.class)
    @Rollback(true) 
    public void createAccount(){
        AccountVO vo = new AccountVO();  
        vo.setLoginName("admin");
        vo.setPassword(EncodeUtil.MD5Encode("123456"));
        accountComponent.createAccount(vo);
    }
    
    @Test
    public void test(){
        
    }
}
