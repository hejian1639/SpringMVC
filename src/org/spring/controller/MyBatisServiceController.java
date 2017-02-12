package org.spring.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.spring.data.Account;
import org.spring.service.AccountService;
import org.spring.service.AccountWithoutSpringService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/mybatis_service")
public class MyBatisServiceController {
	
	@Resource
	private AccountService accountService;
	
    @Resource
    private AccountWithoutSpringService accountWithoutSpringService;

	@RequestMapping(value = "/account/{username}", method = RequestMethod.GET)
	@ResponseBody
	public Account getAccount(@PathVariable final String username) {
        System.out.println("get account request: " + username);
		return accountService.getAccount(username);
	}

    @RequestMapping(value = "/account", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public void postAccount(@RequestBody Account account) {
        System.out.println("insert account: " + account.getUsername());

        accountService.insertAccount(account);
        
    }

    @RequestMapping(value = "/account/{username}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteAccount(@PathVariable final String username) {
        System.out.println("delete account request: " + username);

        accountService.deleteAccount(username);
        
    }
    
    @RequestMapping(value = "/account_without_transaction", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public void postAccountWithoutTransaction(@RequestBody Account account) throws Exception {
        System.out.println("insert account: " + account.getUsername());

        accountWithoutSpringService.insertAccount(account);
        
        account = accountService.getAccount(account.getUsername());
        System.out.println("get account: " + account.getUsername());
    }

	@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Data integrity violation")
	@ExceptionHandler(Exception.class)
	public void handleError(HttpServletRequest req, Exception exception) {

		exception.printStackTrace();
	}
    
}
