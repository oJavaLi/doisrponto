package com.ojavali.doisrponto.apontamentos;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import java.io.Serializable;


@Getter
@Setter


@Entity
@Table(name = "apontamentos")
public class Apontamentos implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private ApontamentosCategoria categoria;
    private String data_hora_inicio;
    private String data_hora_fim;
    private String justificativa;
    private int usuario_matricula;
    private int centro_resultados_id;
    

}
