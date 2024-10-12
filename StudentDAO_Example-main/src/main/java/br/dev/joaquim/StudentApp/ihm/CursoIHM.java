package br.dev.joaquim.StudentApp.ihm;

import java.util.Scanner;

import br.dev.joaquim.StudentApp.dao.CursoDAO;
import br.dev.joaquim.StudentApp.entities.Curso;

public class CursoIHM {

    private CursoDAO cursoDAO;

    public CursoIHM(CursoDAO cursoDAO) {
        this.cursoDAO = cursoDAO;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        int option = -1;

        while (option != 0) {
            System.out.println("=== Gerenciamento de Cursos ===");
            System.out.println("1. Adicionar Curso");
            System.out.println("2. Visualizar Todos os Cursos");
            System.out.println("3. Atualizar Curso");
            System.out.println("4. Deletar Curso");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            option = scanner.nextInt();
            scanner.nextLine(); 

            switch (option) {
                case 1:
                    addCurso(scanner);
                    break;
                case 2:
                    viewAllCursos();
                    break;
                case 3:
                    updateCurso(scanner);
                    break;
                case 4:
                    deleteCurso(scanner);
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void addCurso(Scanner scanner) {
        System.out.print("Digite o nome do curso: ");
        String nome = scanner.nextLine();
        Curso curso = new Curso();
        curso.setNome(nome);

        cursoDAO.create(curso);
        System.out.println("Curso adicionado com sucesso.");
    }

    private void viewAllCursos() {
        System.out.println("=== Lista de Cursos ===");
        for (Curso curso : cursoDAO.findAll()) {
            System.out.println(curso);
        }
    }

    private void updateCurso(Scanner scanner) {
        System.out.print("Digite o ID do curso a ser atualizado: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir nova linha

        Curso curso = cursoDAO.findById(id);
        if (curso == null) {
            System.out.println("Curso não encontrado.");
            return;
        }

        System.out.print("Digite o novo nome do curso: ");
        String nome = scanner.nextLine();

        curso.setNome(nome);
        cursoDAO.update(curso);
        System.out.println("Curso atualizado com sucesso.");
    }

    private void deleteCurso(Scanner scanner) {
        System.out.print("Digite o ID do curso a ser deletado: ");
        int id = scanner.nextInt();
        cursoDAO.delete(id);
        System.out.println("Curso deletado com sucesso.");
    }
}
