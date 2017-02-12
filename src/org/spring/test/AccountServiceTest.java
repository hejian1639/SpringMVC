package org.spring.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.spring.data.Account;
import org.spring.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = { "classpath:root-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class AccountServiceTest {

	@Autowired
	private AccountService accountService;
	
	@Test
	public void testGetAdsById(){
		Account account = new Account();
		account.setUsername("john");
		account.setEmail("hejian@sina.com");
        accountService.insertAccount(account);
        Account account2 = accountService.getAccount("john");
        Assert.assertEquals("email not equal", account.getEmail(), account2.getEmail());
//        Assert.assertEquals("email equal", "hello", account2.getEmail());
	}

}
