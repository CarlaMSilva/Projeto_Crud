//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
    import java.util.ArrayList;
import java.util.Scanner;

public class Crud_Projeto_Main {
        private static ArrayList<String> tasks = new ArrayList<>();
        private static Scanner scanner = new Scanner(System.in);

        public static void main(String[] args) {
            int choice;

            do {
                displayMenu();
                choice = getUserChoice();

                switch (choice) {
                    case 1:
                        addTask();
                        break;
                    case 2:
                        removeTask();
                        break;
                    case 3:
                        displayTasks();
                        break;
                    case 4:
                        System.out.println("Saindo do aplicativo. Até logo!");
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } while (choice != 4);
        }

        private static void displayMenu() {
            System.out.println("\n--- ToDo List App ---");
            System.out.println("1. Adicionar Tarefa");
            System.out.println("2. Remover Tarefa");
            System.out.println("3. Exibir Tarefas");
            System.out.println("4. Sair");
        }

        private static int getUserChoice() {
            System.out.print("Escolha uma opção: ");
            return scanner.nextInt();
        }

        private static void addTask() {
            scanner.nextLine(); // Limpar o buffer do scanner
            System.out.print("Digite a tarefa a ser adicionada: ");
            String task = scanner.nextLine();
            tasks.add(task);
            System.out.println("Tarefa adicionada com sucesso!");
        }

        private static void removeTask() {
            if (tasks.isEmpty()) {
                System.out.println("A lista de tarefas está vazia.");
                return;
            }

            displayTasks();
            System.out.print("Digite o número da tarefa a ser removida: ");
            int index = scanner.nextInt();

            if (index >= 1 && index <= tasks.size()) {
                String removedTask = tasks.remove(index - 1);
                System.out.println("Tarefa removida: " + removedTask);
            } else {
                System.out.println("Índice inválido. Tente novamente.");
            }
        }

        private static void displayTasks() {
            if (tasks.isEmpty()) {
                System.out.println("A lista de tarefas está vazia.");
            } else {
                System.out.println("Lista de Tarefas:");
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println((i + 1) + ". " + tasks.get(i));
                }
            }
        }
    }
