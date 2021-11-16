package edu.sjtu.party.web.controller.resolver;

import edu.sjtu.party.web.controller.annotation.CurrentUser;
import edu.sjtu.party.web.controller.interceptor.SecurityInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;

/**
 * Created by gwang on 19/7/28.
 */
public class SessionRelatedAttributeArgumentResolver implements HandlerMethodArgumentResolver {

    private static Logger log = LoggerFactory.getLogger(SessionRelatedAttributeArgumentResolver.class);

    @Override
    public boolean supportsParameter(MethodParameter param) {

        for (Annotation paramAnn : param.getParameterAnnotations()) {
            if (paramAnn instanceof CurrentUser) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter param, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        Annotation[] paramAnns = param.getParameterAnnotations();

        Class paramType = param.getParameterType();

        for (Annotation paramAnn : paramAnns) {
            if (paramAnn instanceof CurrentUser) {
                HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
                String jAccount = SecurityInterceptor.getLoginUserID(request);
                CurrentUser ann = (CurrentUser) paramAnn;
                if (ann.required() && jAccount == null) {
                    return WebArgumentResolver.UNRESOLVED;
                }
//
                return null;
            }
        }

        return WebArgumentResolver.UNRESOLVED;
    }
}
