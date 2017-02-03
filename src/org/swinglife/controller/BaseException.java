package org.swinglife.controller;

import javax.ws.rs.core.Response.Status;

/**
 * A simple custom exception class to carry extra information.
 */
public class BaseException extends RuntimeException {
    /**
     * 
     */
    private static final long serialVersionUID = 4433617793242871340L;

    private Status code;
    private Object[] values;

    public BaseException(String message, Throwable cause, Status code, Object[] values) {
        super(message, cause);
        this.code = code;
        this.values = values;
    }

    public Status getCode() {
        return code;
    }

    public void setCode(Status code) {
        this.code = code;
    }

    public Object[] getValues() {
        return values;
    }

    public void setValues(Object[] values) {
        this.values = values;
    }
}
