package com.ojavali.doisrponto.apontamentos;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


@Getter
@Setter


@Entity
@Table(name = "apontamentos")
public class Apontamentos implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String categoria;
    private String data_hora_inicio;
    private String data_hora_fim;
    private String justificativa;
    private int usuarioMatricula;
    private int centroResultadosId;
    private Boolean aprovado;
    private Integer avaliador;
    private String resposta;

    public String getTotalHoras() {
        try {
            DateTimeFormatter formatador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            LocalDateTime inicio = LocalDateTime.parse(this.getData_hora_inicio(), formatador);
            LocalDateTime fim = LocalDateTime.parse(this.getData_hora_fim(), formatador);

            Duration duracao = Duration.between(inicio, fim);

            long horas = duracao.toHours();
            long minutos = duracao.toMinutes() - (horas * 60);

            return String.format("%d:%02d", horas, minutos);
        } catch(DateTimeParseException e) {
            return "ERRO";
        }
    }
    
    public LocalDateTime getInicio() {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(this.getData_hora_inicio(), formatador);
    }
    
    public LocalDateTime getFim() {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(this.getData_hora_fim(), formatador);
    }
}
