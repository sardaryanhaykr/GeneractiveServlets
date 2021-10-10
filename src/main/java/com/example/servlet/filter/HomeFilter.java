package com.example.servlet.filter;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//@WebFilter(filterName = "HomeFilter",value = "/*")
//public class HomeFilter extends HttpFilter {
//    @Override
//    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
//
//        HttpSession session=request.getSession(false);
//        boolean isLoggedIn = (session != null && session.getAttribute("User") != null);
//
//        String loginURI =request.getContextPath() + "/login";
//
//        boolean isLoginRequest = request.getRequestURI().equals(loginURI);
//
//        if (isLoggedIn && isLoginRequest ) {
//
//            response.setStatus(200);
//
//        } else if (!isLoggedIn && isLoginRequest) {
//            //response.sendError(HttpServletResponse.SC_BAD_REQUEST);
//            response.setStatus(200);
//            chain.doFilter(request, response);
//
//        } else {
//            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access is denied");
//        }
//
//    }
//}
