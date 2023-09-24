package com.ojavali.doisrponto.autenticacao;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

import com.ojavali.doisrponto.usuarios.User;
import com.ojavali.doisrponto.usuarios.UserRepository;

public class Autenticacao {
    public static boolean valida(String sessionId, UserRepository userRepository) {
        int matricula = extractMatriculaFromSessionId(sessionId);
        Optional<User> user = userRepository.findById(matricula);
        return user.isPresent();
    }

    public static int extractMatriculaFromSessionId(String sessionId) {
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(sessionId);
            String decodedString = new String(decodedBytes, StandardCharsets.UTF_8);

            if (decodedString.startsWith("2RPONTO")) {
                return Integer.parseInt(decodedString.substring("2RPONTO".length()));
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
