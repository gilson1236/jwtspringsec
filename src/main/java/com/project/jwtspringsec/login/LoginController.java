package com.project.jwtspringsec.login;

import com.project.jwtspringsec.model.Usuario;
import com.project.jwtspringsec.repositories.UsuarioRepository;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@RestController
@RequestMapping("/api/login")
class LoginController {

    private UsuarioRepository usuarioRepository;

    LoginController(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping
    void login(@RequestBody CredenciaisDTO credenciais, HttpSession session){
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
        session.setAttribute("idUsuarioLogado", usuario.getId());
    }

    private String criptografar(String senha) {
        return DigestUtils.sha1Hex(senha + "algoaqui");
    }
}
