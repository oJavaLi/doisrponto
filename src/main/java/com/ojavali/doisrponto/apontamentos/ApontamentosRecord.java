package com.ojavali.doisrponto.apontamentos;

public record ApontamentosRecord(String categoria, String data_hora_inicio, String data_hora_fim, 
String justificativa,int usuarioMatricula, int centroResultadosId, boolean aprovado, int avaliador, String resposta) {

}
