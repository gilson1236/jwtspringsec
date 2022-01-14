package com.project.jwtspringsec.login.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletException;
import javax.servlet.FilterConfig;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//@Component
//@Order(1)
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

        /*HttpSession session = httpRequest.getSession(false);
        if(session == null || session.getAttribute("idUsuarioLogado") == null){
            //chamada sem autenticação
            httpResponse.sendError(HttpStatus.UNAUTHORIZED.value());
            return;
        }*/

        Cookie token = WebUtils.getCookie(httpRequest, "token");
        if(token == null){
            httpResponse.sendError(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        try {
            String jwt = token.getValue();
            //apenas para fins educacionais é inserido aqui a chave. Em produção jamais faça isso
            //Em produção coloque a chave no properties ou na variavel de ambiente
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256("algosecretoaqui"))
                    .build()
                    .verify(jwt);

            Integer idUsuarioLogado = decodedJWT.getClaim("idUsuarioLogado").asInt();
            httpRequest.setAttribute("idUsuarioLogado", idUsuarioLogado);

            //chamada autenticada
            chain.doFilter(request, response);
        } catch(JWTVerificationException ex) {
            httpResponse.sendError(HttpStatus.UNAUTHORIZED.value());
            return;
        }

    }

    @Override
    public void init(FilterConfig filterConfig){

    }

    @Override
    public void destroy(){

    }
}
