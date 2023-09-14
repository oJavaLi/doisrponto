package com.ojavali.doisrponto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Apontamentos {
    private Long id;
    private String nome;
    private String senha;
    private String email;
    private UserRole role;

}
