package com.base.common.exception;

/**
 * @author : AnhNT
 * @since : 10/11/2021, Wed
 */
public class Exception extends java.lang.Exception {
    private String resourceName;
    private String fieldName;
    private Object fieldValue;

    public Exception(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }
}
