package org.spring.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebController {

	@RequestMapping({ "/" })
	public String root() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		HelloWorld obj = (HelloWorld) context.getBean("helloWorld");
		obj.getMessage();
		context.close();
		
		return "index.html";
	}

	@RequestMapping({ "/restful_test" })
	public String restfulTest() {
		HelloWorld obj = (HelloWorld) SpringBeanUtils.getBeanByName("helloWorld");
		obj.getMessage();
		return "page/restful.html";
	}

	@RequestMapping({ "/service_test" })
	public String serviceTest() {
		return "page/service_test.html";
	}

	@RequestMapping({ "/redirect" })
	public String redirect() {
		return "redirect.jsp";
	}
	
	@RequestMapping({ "/websocket_test" })
	public String websocketTest() {
		return "echo.html";
	}
	
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public ModelAndView login(String username, String password) {
		if (this.checkParams(new String[] { username, password })) {
			ModelAndView mav = new ModelAndView("page/succ.jsp");
			mav.addObject("username", username);
			mav.addObject("password", password);
			return mav;
		}
		return new ModelAndView("home");
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		return "pages/index.html";
	}

	@RequestMapping(value = "/react_demo/**", method = RequestMethod.GET)
	public String react() {
		return "react_demo/index.html";
	}

	@RequestMapping(value = "/react_third_party", method = RequestMethod.GET)
	public String reactThirdParty() {
		return "react_third_party/index.html";
	}

	/***
	 * @return
	 */
	@RequestMapping(value = "/date", method = RequestMethod.GET)
	public String date() {
		return "page/Date.jsp";
	}

	@RequestMapping("other")
	public String other() {
		return "page/other.jsp";
	}

	@RequestMapping("service test")
	public String service_test() {
		return "page/service_test.html";
	}

	/***
	 * @param params
	 * @return
	 */
	private boolean checkParams(String[] params) {
		for (String param : params) {
			if (param == "" || param == null || param.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@RequestMapping(value = "/throw_exception", method = RequestMethod.GET)
	public String throwException() {
		return "page/exception.html";
	}

	@RequestMapping(value = "/throwNullPointerException", method = RequestMethod.GET)
	public void throwNullPointerException() {
		throw new NullPointerException();
	}

	@ExceptionHandler(NullPointerException.class)
	public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception exception) {

		ModelAndView mav = new ModelAndView();
		mav.addObject("exception", exception);
		mav.addObject("url", req.getRequestURL());
		mav.addObject("timestamp", new Date().toString());
		mav.addObject("status", 500);

		mav.setViewName("support.jsp");
		return mav;
	}

}
