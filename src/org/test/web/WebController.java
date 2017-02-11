package org.test.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebController {

    @RequestMapping({ "/" })
    public String root() {
//        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
//
//        HelloWorld obj = (HelloWorld) context.getBean("helloWorld");
//
//        obj.getMessage();
        return "index.html";
    }

    @RequestMapping({ "/input" })
    public String home() {
        return "page/home.jsp";
    }
    
    /***
     * @return
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "pages/index.html";
    }

    @RequestMapping(value = "/react_demo/**", method = RequestMethod.GET)
    public String react() {
        return "react_demo/index.html";
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
        return "service_test.html";
    }

    /***
     * @param username
     * @param password
     * @return
     */
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
