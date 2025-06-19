-- Script para criação da base de dados inicial.
CREATE DATABASE IF NOT EXISTS sgca;
use sgca;

-- Criação da table de cursos
CREATE TABLE
    IF NOT EXISTS curso (
        idCurso INTEGER PRIMARY key auto_increment,
        nome VARCHAR(20) NOT NULL,
        CHECK (LENGTH (nome) >= 3),
        cargaHoraria INTEGER NOT NULL,
        CHECK (cargaHoraria >= 20),
        limiteAlunos INTEGER NOT NULL,
        CHECK (limiteAlunos >= 1),
        ativo TINYINT DEFAULT 1,
		CHECK (ativo IN (0, 1))
    );

-- Criação da tabela de alunos
CREATE TABLE
    IF NOT EXISTS aluno (
        idAluno INTEGER PRIMARY key auto_increment,
        idCurso INTEGER NOT NULL,
        FOREIGN KEY (idCurso) REFERENCES curso (idCurso) ON DELETE CASCADE, -- excluir todos os alunos se o curso for excluído
        nome VARCHAR(50) NOT NULL,
        cpf VARCHAR(11) unique NOT NULL,
        telefone VARCHAR(20),
        email VARCHAR(255),
        dataNascimento DATE,
        CHECK (LENGTH (cpf) = 11),
        CHECK (LENGTH (nome) >= 3),
        CHECK (
            email REGEXP '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$'
        ) -- validação simples de email com regex
    );

-- Script de teste
INSERT INTO curso VALUES (1001, "curso falso", 33, 20, 1);
INSERT INTO curso VALUES (1002, "curso verdadeiro", 123, 123, 1);
SELECT * FROM curso WHERE idCurso = 1001;
SELECT * FROM curso;