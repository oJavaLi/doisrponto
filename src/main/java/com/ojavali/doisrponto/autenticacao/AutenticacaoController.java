package com.ojavali.doisrponto.autenticacao;

import java.util.Base64;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ojavali.doisrponto.usuarios.User;
import com.ojavali.doisrponto.usuarios.UserRepository;

@RestController
public class AutenticacaoController {
    private final UserRepository userRepository;

    public AutenticacaoController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody Credenciais credenciais) {
        Optional<User> usuarioOptional = this.userRepository.findById(credenciais.getMatricula());

        if (usuarioOptional.isPresent()) {
            User usuario = usuarioOptional.get();

            if (usuario.getSenha().equals(credenciais.getSenha())) {
                String sessionId = "2RPONTO" + usuario.getMatricula();
                return ResponseEntity.ok()
                        .body(Base64.getUrlEncoder().encodeToString(sessionId.getBytes()));
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}