package org.spring.test;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import mockit.Expectations;
import mockit.Injectable;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.spring.controller.MyBatisServiceController;
import org.spring.data.Account;
import org.spring.service.AccountService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = { "classpath:root-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class AccountResourceTest {

	private MyBatisServiceController accountController;
	
	@Injectable
	private AccountService accountService;
	
    @Before
    public void setup() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
    	accountController = new MyBatisServiceController();
        Map<String, Object> memMap = new HashMap<String, Object>() {
            {
                put("accountService", accountService);
            }
        };
        for (Entry<String, Object> e : memMap.entrySet()) {
            Field f = MyBatisServiceController.class.getDeclaredField(e.getKey());
            f.setAccessible(true);
            f.set(accountController, e.getValue());
        }
    }
	
	@Test
	public void test(){
    	new Expectations() {
            {
            	accountService.getAccount(anyString);
            	Account account = new Account();
            	account.setEmail("hejian@sina.com");
            	result = account;
            }
        };

        Account account = accountController.getAccount("john");
        Assert.assertEquals("email not equal", "hejian@sina.com", account.getEmail());
	}

}
