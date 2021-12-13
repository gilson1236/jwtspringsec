package com.project.jwtspringsec.login;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletException;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
@Order(1)
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{

        HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpServletResponse httpResponse = (HttpServletResponse)response;
        
        if(!httpRequest.getServletPath().startsWith("/api")){
            //recurso estático
            chain.doFilter(request, response);
            return;
        }

        if(httpRequest.getServletPath().startsWith("/api/login")){
            //usuário tentando se logar
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = httpRequest.getSession(false);
        if(session == null || session.getAttribute("idUsuarioLogado") == null){
            //chamada sem autenticação
            httpResponse.sendError(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        //chamada autenticada
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig){

    }

    @Override
    public void destroy(){

    }
}
