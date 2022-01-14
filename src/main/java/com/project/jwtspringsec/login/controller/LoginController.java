package com.project.jwtspringsec.login.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.project.jwtspringsec.login.exception.CredenciaisInvalidasException;
import com.project.jwtspringsec.login.dto.CredenciaisDTO;
import com.project.jwtspringsec.usuario.model.Usuario;
import com.project.jwtspringsec.usuario.repository.UsuarioRepository;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

//@RestController
//@RequestMapping("/api/login")
class LoginController {

    private UsuarioRepository usuarioRepository;

    LoginController(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping
    void login(@RequestBody CredenciaisDTO credenciais, HttpServletResponse response){
        Optional<Usuario> talvezUsuario = this.usuarioRepository
                .findByLogin(credenciais.getUsuario());
        if(talvezUsuario.isEmpty()){
            throw new CredenciaisInvalidasException();
        }
        String senhaCriptografada = criptografar(credenciais.getSenha());
        Usuario usuario = talvezUsuario.get();
        if(!usuario.getSenha().equals(senhaCriptografada)){
            throw new CredenciaisInvalidasException();
        }
        String jwt = JWT.create()
                .withClaim("idUsuarioLogado", usuario.getId())
                .sign(Algorithm.HMAC256("algosecretoaqui"));

        Cookie cookie = new Cookie("token", jwt);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60 * 30); //expira em 30 minutos
        response.addCookie(cookie);
    }

    private String criptografar(String senha) {
        return DigestUtils.sha1Hex(senha + "algoaqui");
    }
}
