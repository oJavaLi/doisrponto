package com.ojavali.doisrponto.usuarios;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter


public class User {
    private Long id;
    private String nome;
    private String senha;
    private String email;
    private UserRole role;

}


