package com.ojavali.doisrponto.autenticacao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecuperarSenhaEmailDetalhes {
    private String nome;
    private int matricula;
    private String email;
    private String dataEHora;
    private String token;
}
