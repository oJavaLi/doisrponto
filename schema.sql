CREATE TABLE usuarios (
    matricula INT PRIMARY KEY,
    nome VARCHAR(255),
    senha VARCHAR(255),
    categoria VARCHAR(255)
);

CREATE TABLE centro_de_resultados (
    id SERIAL PRIMARY KEY,
    nome_projeto VARCHAR(255),
    nome_cliente VARCHAR(255)
);

CREATE TABLE status_apontamentos (
    apontamento_id INT,
    avaliador_matricula INT,-----
    aprovado BOOLEAN,
    justificativa TEXT,
    FOREIGN KEY (apontamento_id) REFERENCES apontamentos(id),
    FOREIGN KEY (avaliador_matricula) REFERENCES usuario(matricula)------
);

CREATE TABLE apontamentos (
    categoria VARCHAR(255),
    data_hora_inicio TIMESTAMP,
    data_hora_fim TIMESTAMP,
    id SERIAL PRIMARY KEY,
    justificativa TEXT,
    usuario_matricula INT,
    centro_resultados_id INT,
    FOREIGN KEY (usuario_matricula) REFERENCES usuario(matricula),
    FOREIGN KEY (centro_resultados_id) REFERENCES centro_de_resultados(id)
);
