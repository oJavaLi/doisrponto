package com.ojavali.doisrponto.dashboard;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstatisticaApontamentosMesAtual {
    // Quantidade
    private long total;
    private long aprovados;
    private long reprovados;
    private long pendentes;

    // horas
    private double totalHoras;
    private double aprovadosHoras;
    private double reprovadosHoras;
    private double pendentesHoras;
}
