package org.test.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.alibaba.fastjson.JSON;

/***
 * 
 * @author swinglife
 * 
 */
//@ControllerAdvice
public class ExceptionController {

//    @ExceptionHandler(Exception.class)
    public void errorResponse(Exception exception, HttpServletResponse response) throws IOException {

        Map<String, String> errorMap = new HashMap<String, String>();

        errorMap.put("errorMessage", exception.getMessage());

        StringWriter sw = new StringWriter();

        PrintWriter pw = new PrintWriter(sw);

        exception.printStackTrace(pw);

        String stackTrace = sw.toString();

        errorMap.put("errorStackTrace", stackTrace);

        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

        response.setContentType("application/json;charset=utf8");
        PrintWriter out = response.getWriter();

        out.print(JSON.toJSONString(errorMap));

        out.close();

    }

}
