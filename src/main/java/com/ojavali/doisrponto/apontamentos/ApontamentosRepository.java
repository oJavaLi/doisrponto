package com.ojavali.doisrponto.apontamentos;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ApontamentosRepository extends JpaRepository<Apontamentos, Long> {
    List<Apontamentos> findByUsuarioMatricula(int usuarioMatricula);
    List<Apontamentos> findByUsuarioMatriculaAndAprovado(int usuarioMatricula, Boolean aprovado);
    List<Apontamentos> findByCentroResultadosId(int cenntroResultadoId);
}