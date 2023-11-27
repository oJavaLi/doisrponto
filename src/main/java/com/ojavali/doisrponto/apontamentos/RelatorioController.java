package com.ojavali.doisrponto.apontamentos;

import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ojavali.doisrponto.usuarios.User;
import com.ojavali.doisrponto.usuarios.UserRepository;


@RestController
public class RelatorioController {

    @Autowired
    private UserRepository userRepository; 

    @Autowired
    private ApontamentosRepository apontamentosRepository; 
    
    @GetMapping("/api/relatorio/{matricula}")
    public ResponseEntity<Object> gerarRelatorio(@PathVariable(value = "matricula") Integer matricula) {
        Optional<User> userOptional = userRepository.findById(matricula);
        
        if (!userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
        
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<String[]> relatorio = new ArrayList<>();
        
        List<Apontamentos> apontamentos = apontamentosRepository.findByUsuarioMatriculaAndAprovado(matricula, true);

        relatorio.add(new String[] {
            "CR", 
            "Categoria", 
            "Data Hora Inicio", 
            "Data Hora Fim", 
            "Verba", 
            "Multiplicador",
            "Horas Calculadas",
            "Porcentagem Adicional",
            "Justificativa"
        });
        
        for(Apontamentos a: apontamentos) {
            String verba = "";
            double multiplicador = 1;
            double porcentagem = 0;
            double horasCalculadas = 0;
            
            if (a.getCategoria().equals("1")) { // Sobreaviso
                verba = "3016";
                multiplicador = 1;
                porcentagem = 30;
                Duration duracao = Duration.between(a.getInicio(), a.getFim());
                horasCalculadas = (duracao.getSeconds()/3600.0) * multiplicador;
                
                for(Apontamentos b: apontamentos) {
                    if(b.getId() == a.getId()) {
                        continue;
                    }
                    if(b.getInicio().isBefore(a.getFim()) && b.getFim().isAfter(a.getInicio())) {
                        Duration duracaoHoraExtra = Duration.between(b.getInicio(), b.getFim());
                        double horasExtras = (duracaoHoraExtra.getSeconds()/3600.0) * multiplicador;
                        horasCalculadas -= horasExtras;
                    }
                }
                
                relatorio.add(new String[] {
                    Integer.toString(a.getCentroResultadosId()),
                    a.getCategoria(),
                    a.getData_hora_inicio(),
                    a.getData_hora_fim(),
                    verba,
                    String.format("%.4f", multiplicador),
                    String.format("%.2f", horasCalculadas),
                    String.format("%.2f", porcentagem),
                    a.getJustificativa()
                });
            } else {
                if(
                    (a.getInicio().getHour() >= 6 && a.getInicio().getHour() < 22)
                    && (a.getFim().getHour() < 22 || (a.getFim().getHour() == 22 && a.getFim().getMinute() == 0))
                ){
                    Duration duracao = Duration.between(a.getInicio(), a.getFim());
                    
                    if(duracao.getSeconds() > 2*3600) {
                        verba = "1601";
                        multiplicador = 1;
                        porcentagem = 75;
                        horasCalculadas = 2;
                        
                        relatorio.add(new String[] {
                            Integer.toString(a.getCentroResultadosId()),
                            a.getCategoria(),
                            a.getData_hora_inicio(),
                            formatador.format(a.getInicio().plusHours(2)),
                            verba,
                            String.format("%.4f", multiplicador),
                            String.format("%.2f", horasCalculadas),
                            String.format("%.2f", porcentagem),
                            a.getJustificativa()
                        });
                        
                        Duration duracao2 = Duration.between(a.getInicio().plusHours(2), a.getFim());
                        verba = "1602";
                        multiplicador = 1;
                        porcentagem = 100;
                        horasCalculadas = (duracao2.getSeconds()/3600.0) * multiplicador;
                        
                        relatorio.add(new String[] {
                            Integer.toString(a.getCentroResultadosId()),
                            a.getCategoria(),
                            formatador.format(a.getInicio().plusHours(2)),
                            a.getData_hora_fim(),
                            verba,
                            String.format("%.4f", multiplicador),
                            String.format("%.2f", horasCalculadas),
                            String.format("%.2f", porcentagem),
                            a.getJustificativa()
                        });
                    } else {
                        verba = "1601";
                        multiplicador = 1;
                        porcentagem = 75;
                        horasCalculadas = (duracao.getSeconds()/3600.0) * multiplicador;
                        relatorio.add(new String[] {
                            Integer.toString(a.getCentroResultadosId()),
                            a.getCategoria(),
                            a.getData_hora_inicio(),
                            a.getData_hora_fim(),
                            verba,
                            String.format("%.4f", multiplicador),
                            String.format("%.2f", horasCalculadas),
                            String.format("%.2f", porcentagem),
                            a.getJustificativa()
                        });
                    }
                } else if (
                    (a.getInicio().getHour() == 22 || a.getInicio().getHour() == 23 || (a.getInicio().getHour() >= 0 && a.getInicio().getHour() <= 6))
                    && (a.getFim().getHour() < 6 || (a.getFim().getHour() == 6 && a.getFim().getMinute() == 0))
                ) {
                    Duration duracao = Duration.between(a.getInicio(), a.getFim());
                    
                    if(duracao.getSeconds() > 2*3600) {
                        // 2 Primeiras Horas Extras Noturnas
                        verba = "3000";
                        multiplicador = 1.1429;
                        porcentagem = 75;
                        horasCalculadas = 2*multiplicador;
                        
                        relatorio.add(new String[] {
                            Integer.toString(a.getCentroResultadosId()),
                            a.getCategoria(),
                            a.getData_hora_inicio(),
                            formatador.format(a.getInicio().plusHours(2)),
                            verba,
                            String.format("%.4f", multiplicador),
                            String.format("%.2f", horasCalculadas),
                            String.format("%.2f", porcentagem),
                            a.getJustificativa()
                        });
                        
                        // Demais Horas
                        Duration duracao2 = Duration.between(a.getInicio().plusHours(2), a.getFim());
                        verba = "3001";
                        multiplicador = 1.1429;
                        porcentagem = 100;
                        horasCalculadas = (duracao2.getSeconds()/3600.0) * multiplicador;
                        
                        relatorio.add(new String[] {
                            Integer.toString(a.getCentroResultadosId()),
                            a.getCategoria(),
                            formatador.format(a.getInicio().plusHours(2)),
                            a.getData_hora_fim(),
                            verba,
                            String.format("%.4f", multiplicador),
                            String.format("%.2f", horasCalculadas),
                            String.format("%.2f", porcentagem),
                            a.getJustificativa()
                        });
                    } else {
                        verba = "3000";
                        multiplicador = 1.1429;
                        porcentagem = 75;
                        horasCalculadas = (duracao.getSeconds()/3600.0) * multiplicador;
                        relatorio.add(new String[] {
                            Integer.toString(a.getCentroResultadosId()),
                            a.getCategoria(),
                            a.getData_hora_inicio(),
                            a.getData_hora_fim(),
                            verba,
                            String.format("%.4f", multiplicador),
                            String.format("%.2f", horasCalculadas),
                            String.format("%.2f", porcentagem),
                            a.getJustificativa()
                        });
                    }
                    
                    verba = "1809";
                    multiplicador = 1.1429;
                    porcentagem = 30;
                    horasCalculadas = (duracao.getSeconds()/3600.0) * multiplicador;
                    relatorio.add(new String[] {
                        Integer.toString(a.getCentroResultadosId()),
                        a.getCategoria(),
                        a.getData_hora_inicio(),
                        a.getData_hora_fim(),
                        verba,
                        String.format("%.4f", multiplicador),
                        String.format("%.2f", horasCalculadas),
                        String.format("%.2f", porcentagem),
                        a.getJustificativa()
                    });
                }
            }
        }

        
        return new ResponseEntity<Object>(relatorio, null, HttpStatus.OK);
    }
}