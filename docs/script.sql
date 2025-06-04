-- Script para criação da base de dados inicial.
CREATE DATABASE IF NOT EXISTS sgca;
use sgca;

-- Criação da tabale de cursos
CREATE TABLE IF NOT EXISTS curso (
    idCurso INTEGER PRIMARY key auto_increment,
    nome VARCHAR(20) NOT NULL,
    cargaHoraria INTEGER NOT NULL,
    limiteAlunos INTEGER NOT NULL,
    ativo INTEGER,
    CHECK (cargaHoraria >= 20),
    CHECK (limiteAlunos >= 1)
);

-- Criação da tabela de alunos
CREATE TABLE IF NOT EXISTS aluno (
    idAluno INTEGER PRIMARY key auto_increment,
    nome VARCHAR(50) NOT NULL,
    cpf VARCHAR(11) unique NOT NULL,
    telefone VARCHAR(20),
    email VARCHAR(255),
    dataNascimento DATE,
    CHECK (LENGTH(cpf) = 11),
    CHECK (LENGTH(nome) >= 3),
    CHECK (email REGEXP '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$')
);