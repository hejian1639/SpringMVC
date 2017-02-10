package org.swinglife.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.swinglife.domain.Account;

@Controller
@RequestMapping("/mybatis_service")
public class MyBatisServiceController {
	
	@Resource
	private AccountService accountService;

	@RequestMapping(value = "/account/{username}", method = RequestMethod.GET)
	@ResponseBody
	public Account getObject(@PathVariable final String username) {
		return accountService.getAccount(username);
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public Exception handleError(HttpServletRequest req, Exception exception)
			throws Exception {

		return exception;
	}

}
