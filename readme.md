# Sistema de Gestão de Cursos e Alunos
Projeto de conclusão da disciplina de Linguagem de Programação.

## Enunciado do Projeto – Sistema de Gestão de Cursos e Alunos 

## Contextualização: 
Uma instituição de ensino precisa de um sistema para gerenciar seus cursos e os alunos matriculados. Cada 
curso pode ter vários alunos, enquanto cada aluno está matriculado em apenas um curso. O sistema deverá 
garantir o controle de cadastros, respeitar as regras de negócio e assegurar a integridade dos dados. 

## Objetivo do Sistema: 
Desenvolver um sistema em Java com interface gráfica e persistência dos dados em MySQL e também em 
arquivo texto, utilizando programação orientada a objetos. 

## Requisitos

Para rodar a aplicação SGCA no seu computador, você precisará dos seguintes itens:

Pré-requisitos de Software
- Java Development Kit (JDK): Versão 21 ou superior.
- Apache Maven: Versão 3.x ou superior.
- MySQL Server: Uma instância do servidor MySQL rodando.

## Configurações

Edite o arquivo src\main\resources\db.properties com as configurações de login do banco de dados para sua máquina local.
Execute o arquivo [DatabaseConector](src/main/java/factory/DatabaseConnector.java) para criar criar o banco de dados e as tabelas iniciais.

## Manual de uso:

1. Tela Inicial: Ao iniciar a aplicação, você verá a tela principal com a visualização dos dados em formato de tabela.
1. Novo Curso: Clique no botão "Novo Curso" para adicionar um novo curso. Preencha os campos como nome, limite de alunos e carga horária.
1. Novo Aluno: Após cadastrar um curso, você pode adicionar alunos. Clique em "Novo Aluno" e uma nova janela se abrirá para preencher os dados do estudante.
1. Editar: Para alterar os dados de um curso ou aluno existente, selecione o item na tabela e clique no botão "Editar".
1. Excluir: Selecione um item na tabela e clique em "Excluir". Uma confirmação será solicitada antes da remoção definitiva.
1. Exportação: O botão "Exportação" permite gerar relatórios em formato CSV. Uma janela se abrirá para que você escolha onde salvar o arquivo.
1. Filtros: Utilize os filtros disponíveis para visualizar apenas alunos ativos, inativos ou todos eles, facilitando a navegação pelos dados.

## Melhorias futuras
1. Implementação de sistema de busca.
1. Criação automática do banco de dados e tabelas iniciais.
1. Otimização de performance.
1. Exportação de relatórios em outros formatos.