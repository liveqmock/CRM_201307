/**
 * 
 */
package oasis.spiderman;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * <p>
 * 
 * @author liyue
 * @date 2013-6-4 下午01:47:18
 * @version v1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "TestMybatisAnnotation-context.xml" })
public class TestMybatisAnnotation {

    // @Autowired
    // private ProcessDefinitionMapper mapper;


    @Test
    public void test1() {
    }

    @Test
    public void testInsert() {

    }
}
