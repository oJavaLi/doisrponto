package com.ojavali.doisrponto.status_apontamentos;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
// import lombok.experimental.StandardException;

@Setter
@Getter
@Entity
@Table(name = "status_apontamentos")
public class StatusApontamentos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int apontamento_id;

    private int avaliador_matricula;

    private boolean aprovado;

    private String justificativa;
}
