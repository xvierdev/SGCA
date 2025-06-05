package model;

import java.util.Scanner;
import java.time.LocalDate;

public class TestModel {

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("id: ");
            int id = sc.nextInt();
            sc.nextLine();
            System.out.print("nome: ");
            String nome = sc.nextLine();
            System.out.print("cpf: ");
            String cpf = sc.nextLine();
            System.out.print("telefone: ");
            int telefone = sc.nextInt();
            sc.nextLine();
            System.out.print("e-mail: ");
            String email = sc.nextLine();
            System.out.print("data de nascimento: ");
            String[] data = sc.nextLine().split("/");
            int day = Integer.parseInt(data[0]);
            int month = Integer.parseInt(data[1]);
            int year = Integer.parseInt(data[2]);
            LocalDate dataNascimento = LocalDate.of(year, month, day);

            Aluno a = new Aluno(id, nome, cpf, telefone, email, dataNascimento);
            System.out.println(a.toString());
        }
    }
}
