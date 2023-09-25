package com.ojavali.doisrponto.centro_de_resultado;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "centro_de_resultados")
public class CentroDeResultado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nome_projeto;
    private String nome_cliente;
}
