package org.swinglife.controller;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/***
 * 
 * @author swinglife
 * 
 */
@Controller
public class HomeController {
    Map<Integer, JsonObject> objectMap = new ConcurrentHashMap<Integer, JsonObject>(); 

    @RequestMapping({ "/" })
    public String home() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");

        HelloWorld obj = (HelloWorld) context.getBean("helloWorld");

        obj.getMessage();
        return "page/home.jsp";
    }

    /***
     * @return
     */
    @MyRequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "pages/index.html";
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
            ModelAndView mav = new ModelAndView("succ.jsp");
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

    
    @RequestMapping(value = "/object/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject getObject(@PathVariable final int id) {
        System.out.println("getObject " + this);
        JsonObject object = objectMap.get(id);
        return object;
    }

    @RequestMapping(value = "/urlTest/**", method = RequestMethod.GET)
    @ResponseBody
    public String urlTest(HttpServletRequest request) {
        System.out.println("getRequestURL = " + request.getRequestURL());
        System.out.println("getRequestURI = " + request.getRequestURI());
        return "ok";
    }

    @RequestMapping(value = "/bars/{numericId:[\\d]+}")
    @ResponseBody
    public String getBarsBySimplePathWithPathVariable(@PathVariable final long numericId) {
        return "Get a specific Bar with id=" + numericId;
    }
    
    @RequestMapping(value = "/bars/{str:[\\s/]+}")
    @ResponseBody
    public String getBarsBySimplePathWithPathVariable(@PathVariable final String str) {
        return "Get a specific Bar with id=" + str;
    }

    @RequestMapping(value = "/object", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public void postObject(@RequestBody JsonObject json) {
        System.out.println("postObject " + this);

        objectMap.put(json.getId(), json);
    }
    

    @RequestMapping(value = "/object", method = RequestMethod.PUT, consumes = "application/json")
    @ResponseBody
    public void putObject(@RequestBody JsonObject json) {
        System.out.println("putObject " + this);
        objectMap.put(json.getId(), json);
    }

    @RequestMapping(value = "/object/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteObject(@PathVariable final int id) {
        System.out.println("postObject " + this);
        objectMap.remove(id);
    }
    
    
    @RequestMapping(value = "/service", method = RequestMethod.GET)
    @ResponseBody
    public String service() {
        return "spring web service";
    }

    @RequestMapping(value = "/pets/{petId}", method = RequestMethod.GET)
    public void findPet(@PathVariable String petId) {
    }

    @RequestMapping(value = "/departments")
    public String findDepatment(@RequestParam("departmentId") String departmentId) {

        System.out.println("Find department with ID: " + departmentId);
        return "someResult";

    }

    @RequestMapping(value = "/{textualPart:[a-z-]+}.{numericPart:[\\d]+}")
    public String regularExpression(@PathVariable String textualPart, @PathVariable String numericPart) {

        System.out.println("Textual part: " + textualPart + ", numeric part: " + numericPart);
        return "someResult";
    }

    static public class JsonObject implements Serializable {
        private static final long serialVersionUID = -6526203853383136386L;
        private Integer id;
        private String name;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public Integer getId() {
            return id;
        }
        public void setId(Integer id) {
            this.id = id;
        }

    }

    @RequestMapping(value = "/spring_post_service", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public JsonObject springPostService(@RequestBody JsonObject json) {
        return json;
    }

    @RequestMapping(value = "/spring_post_string_service", method = RequestMethod.POST)
    @ResponseBody
    public String springPostService(@RequestBody String json) {
        return json;
    }

    @RequestMapping(value = "/spring_get_boolean_service")
    @ResponseBody
    public Boolean springPostService() {
        return true;
    }

    @RequestMapping(value = "/convert", produces = { "application/x-wisely" }, method = RequestMethod.POST)
    @ResponseBody
    public Person convert(@RequestBody Person person) {
        return person;
    }

    @RequestMapping(value = "/throwException", method = RequestMethod.GET)
    @ResponseBody
    public void throwException() {
        throw new RuntimeException();
    }

    @RequestMapping(value = "/throwNullPointerException", method = RequestMethod.GET)
    @ResponseBody
    public void throwNullPointerException() {
        throw new NullPointerException();
    }

    @ExceptionHandler(NullPointerException.class)
    public String defaultErrorHandler(HttpServletRequest req, Exception exception) {

        return "defaultErrorPage.jsp";
    }

}
