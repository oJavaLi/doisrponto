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

CREATE TABLE apontamentos (
    categoria VARCHAR(255),
    data_hora_inicio TIMESTAMP,
    data_hora_fim TIMESTAMP,
    id SERIAL PRIMARY KEY,
    justificativa TEXT,
    usuario_matricula INT,
    centro_resultados_id INT,
    FOREIGN KEY (usuario_matricula) REFERENCES usuarios(matricula),
    FOREIGN KEY (centro_resultados_id) REFERENCES centro_de_resultados(id)
);

CREATE TABLE status_apontamentos (
    id SERIAL PRIMARY KEY,
    apontamento_id INT,
    avaliador_matricula INT,
    aprovado BOOLEAN,
    justificativa TEXT,
    FOREIGN KEY (apontamento_id) REFERENCES apontamentos(id),
    FOREIGN KEY (avaliador_matricula) REFERENCES usuarios(matricula)
);

ALTER TABLE usuarios ADD COLUMN email VARCHAR(255);

ALTER TABLE usuarios ADD COLUMN recover_password_token VARCHAR(255);

ALTER TABLE apontamentos ADD COLUMN aprovado BOOLEAN;

ALTER TABLE apontamentos ADD COLUMN avaliador INTEGER;

ALTER TABLE apontamentos ADD COLUMN resposta TEXT;

ALTER TABLE apontamentos ADD CONSTRAINT apontamentos_avaliador_matricula_fkey FOREIGN KEY (avaliador) REFERENCES usuarios(matricula);

ALTER TABLE public.apontamentos ALTER COLUMN data_hora_inicio TYPE varchar USING data_hora_inicio::varchar;

ALTER TABLE public.apontamentos ALTER COLUMN data_hora_fim TYPE varchar USING data_hora_fim::varchar;

CREATE TABLE parametrizacao (
    id SERIAL PRIMARY KEY,
    tipo VARCHAR(255),
    verba BIGINT,
    horas DOUBLE PRECISION,
    porcentagem DOUBLE PRECISION,
    descricao VARCHAR(255)
);

INSERT INTO public.parametrizacao(tipo, verba, horas, porcentagem, descricao) VALUES('Sobreaviso', 3016, 1.0, 30.0, 'Horas apontadas em sobreaviso.');