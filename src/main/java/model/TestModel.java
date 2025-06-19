package model;

import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TestModel {

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            DateTimeFormatter fmt1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            System.out.print("id: ");
            int id = sc.nextInt();
            System.out.print("id curso: ");
            int idCurso = sc.nextInt();
            sc.nextLine();
            System.out.print("nome: ");
            String nome = sc.nextLine();
            System.out.print("cpf: ");
            String cpf = sc.nextLine();
            System.out.print("telefone: ");
            String telefone = sc.nextLine();
            System.out.print("e-mail: ");
            String email = sc.nextLine();
            System.out.print("data de nascimento: ");
            String data = sc.nextLine();
            LocalDate dataNascimento = LocalDate.parse(data, fmt1);

            Aluno a = new Aluno(id, idCurso, nome, cpf, telefone, email, dataNascimento);
            System.out.println(a.toString());
        }
    }
}
