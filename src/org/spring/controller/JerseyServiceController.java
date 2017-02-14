package org.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/***
 * 
 * @author swinglife
 * 
 */
@Controller
@RequestMapping("/jersey")
public class JerseyServiceController {

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	@ResponseBody
	public String getObject() {
		return "JerseyServiceController";
	}


}
