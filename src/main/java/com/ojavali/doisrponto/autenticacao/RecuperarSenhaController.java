package com.ojavali.doisrponto.autenticacao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ojavali.doisrponto.usuarios.User;
import com.ojavali.doisrponto.usuarios.UserRepository;

@RestController
public class RecuperarSenhaController {

    @Autowired
    private UserRepository userRepository;


    // Atualizar Método abaixo para enviar email ao invés de retornar detalhes
    // para o template de email e token para recuperação
    @GetMapping("/recover-password")
    public ResponseEntity<RecuperarSenhaEmailDetalhes> getRecoverPasswordEmailDetails(@RequestParam String email) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        String recoveryToken = generateRecoveryToken();
        user.setRecoverPasswordToken(recoveryToken);
        userRepository.save(user);

        RecuperarSenhaEmailDetalhes emailDetails = new RecuperarSenhaEmailDetalhes();
        emailDetails.setNome(user.getNome());
        emailDetails.setEmail(user.getEmail());
        emailDetails.setDataEHora(getCurrentDatetime());
        emailDetails.setToken(recoveryToken);

        return ResponseEntity.ok(emailDetails);
    }

    @PostMapping("/update-password")
    public ResponseEntity<String> updatePassword(@RequestParam String email, @RequestParam String token, @RequestBody NovaSenha novaSenha) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        if (!user.getRecoverPasswordToken().equals(token)) {
            return ResponseEntity.badRequest().body("Token Inválido!");
        }

        user.setSenha(novaSenha.getNovaSenha());
        user.setRecoverPasswordToken(null);
        userRepository.save(user);

        return ResponseEntity.ok("Senha atualizada com sucesso!");
    }

    private String generateRecoveryToken() {
        return UUID.randomUUID().toString();
    }

    private String getCurrentDatetime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
        return sdf.format(new Date());
    }
}