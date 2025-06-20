-- Script para criação da base de dados inicial.
CREATE DATABASE IF NOT EXISTS sgca;
use sgca;

-- Criação da table de cursos
CREATE TABLE
    IF NOT EXISTS curso (
        idCurso INTEGER PRIMARY key auto_increment,
        nome VARCHAR(20) NOT NULL UNIQUE,
        CONSTRAINT chk_curso_nome_length CHECK (LENGTH (nome) >= 3),
        cargaHoraria INTEGER NOT NULL,
        CONSTRAINT chk_curso_carga_horaria_minima CHECK (cargaHoraria >= 20),
        limiteAlunos INTEGER NOT NULL,
        CONSTRAINT chk_curso_limite_alunos_minimo CHECK (limiteAlunos >= 1),
        ativo TINYINT DEFAULT 1,
		CONSTRAINT chk_curso_ativo_valido CHECK (ativo IN (0, 1))
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
        CONSTRAINT chk_aluno_cpf_length CHECK (LENGTH (cpf) = 11),
        CONSTRAINT chk_alunos_nome_length CHECK (LENGTH (nome) >= 3),
        CONSTRAINT chk_aluno_email_valido CHECK (
            email REGEXP '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$'
        ) -- validação simples de email com regex
    );