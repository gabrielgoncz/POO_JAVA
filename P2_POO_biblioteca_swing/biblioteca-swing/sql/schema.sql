DROP DATABASE IF EXISTS biblioteca;
CREATE DATABASE biblioteca CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE biblioteca;

-- Herança: pessoa -> leitor / funcionario (estratégia: tabela por subclasse)
CREATE TABLE pessoa (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome        VARCHAR(120) NOT NULL,
    cpf         VARCHAR(14) NOT NULL UNIQUE,
    email       VARCHAR(120),
    telefone    VARCHAR(20),
    tipo        ENUM('LEITOR','FUNCIONARIO') NOT NULL
) ENGINE=InnoDB;

CREATE TABLE leitor (
    pessoa_id      BIGINT PRIMARY KEY,
    matricula      VARCHAR(20) NOT NULL UNIQUE,
    data_cadastro  DATE NOT NULL,
    ativo          BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT fk_leitor_pessoa FOREIGN KEY (pessoa_id) REFERENCES pessoa(id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE funcionario (
    pessoa_id      BIGINT PRIMARY KEY,
    matricula      VARCHAR(20) NOT NULL UNIQUE,
    cargo          VARCHAR(60) NOT NULL,
    salario        DECIMAL(10,2) NOT NULL,
    data_admissao  DATE NOT NULL,
    CONSTRAINT fk_func_pessoa FOREIGN KEY (pessoa_id) REFERENCES pessoa(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Obra (livro/revista) e suas Cópias (agregação: Obra possui várias Cópias)
CREATE TABLE obra (
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo    VARCHAR(200) NOT NULL,
    autor     VARCHAR(200) NOT NULL,
    editora   VARCHAR(120),
    ano       INT,
    isbn      VARCHAR(20) UNIQUE,
    categoria VARCHAR(60)
) ENGINE=InnoDB;

CREATE TABLE copia (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    obra_id         BIGINT NOT NULL,
    codigo_tombo    VARCHAR(30) NOT NULL UNIQUE,
    estado          ENUM('DISPONIVEL','EMPRESTADA','RESERVADA','DANIFICADA') NOT NULL DEFAULT 'DISPONIVEL',
    CONSTRAINT fk_copia_obra FOREIGN KEY (obra_id) REFERENCES obra(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Associações: Empréstimo conecta Leitor + Cópia + Funcionário
CREATE TABLE emprestimo (
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    leitor_id           BIGINT NOT NULL,
    copia_id            BIGINT NOT NULL,
    funcionario_id      BIGINT NOT NULL,
    data_emprestimo     DATE NOT NULL,
    data_prevista       DATE NOT NULL,
    data_devolucao      DATE,
    multa               DECIMAL(10,2) NOT NULL DEFAULT 0,
    status              ENUM('ATIVO','DEVOLVIDO','ATRASADO') NOT NULL DEFAULT 'ATIVO',
    CONSTRAINT fk_emp_leitor FOREIGN KEY (leitor_id) REFERENCES leitor(pessoa_id),
    CONSTRAINT fk_emp_copia  FOREIGN KEY (copia_id) REFERENCES copia(id),
    CONSTRAINT fk_emp_func   FOREIGN KEY (funcionario_id) REFERENCES funcionario(pessoa_id)
) ENGINE=InnoDB;

CREATE TABLE reserva (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    leitor_id       BIGINT NOT NULL,
    obra_id         BIGINT NOT NULL,
    data_reserva    DATE NOT NULL,
    data_validade   DATE NOT NULL,
    status          ENUM('ATIVA','ATENDIDA','CANCELADA','EXPIRADA') NOT NULL DEFAULT 'ATIVA',
    CONSTRAINT fk_res_leitor FOREIGN KEY (leitor_id) REFERENCES leitor(pessoa_id),
    CONSTRAINT fk_res_obra   FOREIGN KEY (obra_id) REFERENCES obra(id)
) ENGINE=InnoDB;

-- Dados de exemplo
INSERT INTO pessoa(nome,cpf,email,telefone,tipo) VALUES
 ('Ana Souza','111.111.111-11','ana@mail.com','11999990001','LEITOR'),
 ('Bruno Lima','222.222.222-22','bruno@mail.com','11999990002','LEITOR'),
 ('Carla Dias','333.333.333-33','carla@mail.com','11999990003','FUNCIONARIO');

INSERT INTO leitor(pessoa_id,matricula,data_cadastro,ativo) VALUES
 (1,'L0001',CURDATE(),TRUE),
 (2,'L0002',CURDATE(),TRUE);

INSERT INTO funcionario(pessoa_id,matricula,cargo,salario,data_admissao) VALUES
 (3,'F0001','Bibliotecária',4500.00,CURDATE());

INSERT INTO obra(titulo,autor,editora,ano,isbn,categoria) VALUES
 ('Dom Casmurro','Machado de Assis','Globo',1899,'978-85-000-0001-1','Romance'),
 ('Clean Code','Robert C. Martin','Prentice Hall',2008,'978-01-321-3508-5','Tecnologia');

INSERT INTO copia(obra_id,codigo_tombo,estado) VALUES
 (1,'T0001','DISPONIVEL'),
 (1,'T0002','DISPONIVEL'),
 (2,'T0003','DISPONIVEL');
