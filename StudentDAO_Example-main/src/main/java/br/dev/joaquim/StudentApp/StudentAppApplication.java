package br.dev.joaquim.StudentApp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.dev.joaquim.StudentApp.dao.H2StudentDAO;
import br.dev.joaquim.StudentApp.ihm.StudentIHM;
import br.dev.joaquim.StudentApp.dao.H2CursoDAO;
import br.dev.joaquim.StudentApp.ihm.CursoIHM;

import java.util.Scanner;

@SpringBootApplication
public class StudentAppApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(StudentAppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("-------------------------");
        System.out.println("APLICACAO INICIALIZADA");
        System.out.println("-------------------------");

        Scanner scanner = new Scanner(System.in);
        int option = -1;

        while (option != 0) {
            System.out.println("=== Menu Principal ===");
            System.out.println("1. Gerenciar Estudantes");
            System.out.println("2. Gerenciar Cursos");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            option = scanner.nextInt();
            scanner.nextLine(); 

            switch (option) {
                case 1:
                    StudentIHM studentIHM = new StudentIHM(new H2StudentDAO());
                    studentIHM.start();
                    break;
                case 2:
                    CursoIHM cursoIHM = new CursoIHM(new H2CursoDAO());
                    cursoIHM.start();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }

        scanner.close();
    }
}
