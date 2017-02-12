package org.test.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Jersey exception handler.
 */
@Provider
public class BaseExceptionHandler implements ExceptionMapper<Exception> {

    @Context
    private HttpServletRequest request;

    @Context
    private ServletContext servletContext;

    /**
     * Handle Jersey exceptions.
     * 
     * @param Exception object.
     * @return Response object.
     */
    public Response toResponse(Exception exception) {
        Status statusCode = Status.INTERNAL_SERVER_ERROR;
        String message = exception.getMessage();
        return Response.ok(message, MediaType.TEXT_PLAIN).status(statusCode).build();
    }
}
