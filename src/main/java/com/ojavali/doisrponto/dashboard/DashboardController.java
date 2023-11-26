package com.ojavali.doisrponto.dashboard;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ojavali.doisrponto.apontamentos.Apontamentos;
import com.ojavali.doisrponto.apontamentos.ApontamentosRepository;

import ch.qos.logback.core.util.Duration;

@RestController
public class DashboardController {
    
    @Autowired
    private ApontamentosRepository apontamentosRepository;


    @GetMapping("/api/dashboard/estatisticaApontamentos")
    public EstatisticaApontamentosMesAtual estatisticaApontamentos() {
        LocalDateTime primeiroDiaDoMes = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime ultimoDiaDoMes = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()).atTime(23, 59, 59);

        List<Apontamentos> apontamentosDoMes = apontamentosRepository.findAll().stream()
            .filter(apontamento -> apontamento.getInicio() != null && apontamento.getInicio().isAfter(primeiroDiaDoMes) && apontamento.getInicio().isBefore(ultimoDiaDoMes))
            .collect(Collectors.toList());

        EstatisticaApontamentosMesAtual estatisticas = new EstatisticaApontamentosMesAtual();
        
        estatisticas.setTotal(apontamentosDoMes.size());
        estatisticas.setAprovados(apontamentosDoMes.stream().filter(a -> a.getAprovado() != null && a.getAprovado()).count());
        estatisticas.setReprovados(estatisticas.getTotal() - estatisticas.getAprovados());
        estatisticas.setPendentes(estatisticas.getTotal() - estatisticas.getAprovados() - estatisticas.getReprovados());
        
        estatisticas.setTotalHoras(apontamentosDoMes.stream().mapToDouble(a -> {
            return ((double) ChronoUnit.MINUTES.between(a.getInicio(), a.getFim())) / 60.0;
        }).sum());
        estatisticas.setAprovadosHoras(apontamentosDoMes.stream().mapToDouble(a -> {
            if (a.getAprovado() != null && a.getAprovado()) {
                return ((double) ChronoUnit.MINUTES.between(a.getInicio(), a.getFim())) / 60.0;
            } else {
                return 0;
            }
        }).sum());
        estatisticas.setReprovadosHoras(apontamentosDoMes.stream().mapToDouble(a -> {
            if (a.getAprovado() != null && !a.getAprovado()) {
                return ((double) ChronoUnit.MINUTES.between(a.getInicio(), a.getFim())) / 60.0;
            } else {
                return 0;
            }
        }).sum());
        estatisticas.setPendentesHoras(apontamentosDoMes.stream().mapToDouble(a -> {
            if (a.getAprovado() == null) {
                return ((double) ChronoUnit.MINUTES.between(a.getInicio(), a.getFim())) / 60.0;
            } else {
                return 0;
            }
        }).sum());

        return estatisticas;
    }
    
}
