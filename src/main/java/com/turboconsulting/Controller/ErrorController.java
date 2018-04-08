package com.turboconsulting.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller("error")
public class ErrorController {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        ModelAndView m = new ModelAndView();
        m.addObject("errorMessage", ex.getMessage());
        m.addObject("status", response.getStatus());
        m.addObject("url", request.getRequestURL());
        m.setViewName("error");
        return m;
    }
}
