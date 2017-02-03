package org.swinglife.controller;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

public class MyRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    @Override
    protected boolean isHandler(Class<?> beanType) {
        return ((AnnotationUtils.findAnnotation(beanType, Controller.class) != null)
                || (AnnotationUtils.findAnnotation(beanType, RequestMapping.class) != null)
                || (AnnotationUtils.findAnnotation(beanType, MyController.class) != null) 
                || (AnnotationUtils.findAnnotation(beanType, MyRequestMapping.class) != null));
    }

    private RequestMappingInfo createCustomRequestMappingInfo(AnnotatedElement element) {
        MyRequestMapping requestMapping = AnnotatedElementUtils.findMergedAnnotation(element, MyRequestMapping.class);
        RequestCondition<?> condition = (element instanceof Class<?> ? getCustomTypeCondition((Class<?>) element)
                : getCustomMethodCondition((Method) element));
        if (requestMapping == null) {
            return null;
        }
        
        String[] a = resolveEmbeddedValuesInPatterns(requestMapping.value());
        
        return RequestMappingInfo.paths(resolveEmbeddedValuesInPatterns(requestMapping.value()))
                .methods(requestMapping.method()).params(requestMapping.params()).headers(requestMapping.headers())
                .consumes(requestMapping.consumes()).produces(requestMapping.produces())
                .mappingName(requestMapping.name()).customCondition(condition).build();
    }

    private RequestMappingInfo createRequestMappingInfo(AnnotatedElement element) {
        RequestMapping requestMapping = AnnotatedElementUtils.findMergedAnnotation(element, RequestMapping.class);
        RequestCondition<?> condition = (element instanceof Class<?> ?
                getCustomTypeCondition((Class<?>) element) : getCustomMethodCondition((Method) element));
        return (requestMapping != null ? createRequestMappingInfo(requestMapping, condition) : null);
    }
    
    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        RequestMappingInfo info = createCustomRequestMappingInfo(method);
        if (info != null) {
            RequestMappingInfo typeInfo = createCustomRequestMappingInfo(handlerType);
            if (typeInfo != null) {
                info = typeInfo.combine(info);
            }
        } else {
            info = createRequestMappingInfo(method);
            if (info != null) {
                RequestMappingInfo typeInfo = createRequestMappingInfo(handlerType);
                if (typeInfo != null) {
                    info = typeInfo.combine(info);
                }
            }
        }
        return info;
    }

}
