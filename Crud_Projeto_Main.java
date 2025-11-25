import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Crud_Projeto_Main {

    private static Scanner scanner = new Scanner(System.in);
    private static String loggedUser = null; // Armazena o e-mail do usuário logado
    private static ArrayList<String> tasks = new ArrayList<>();

    public static void main(String[] args) {
        int choice;

        do {
            displayLoginMenu();
            choice = getUserChoice();

            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (choice != 3);
    }

    // ----------- ÁREA DE LOGIN E CADASTRO ------------- //

    private static void displayLoginMenu() {
        System.out.println("\n--- Sistema de Login ---");
        System.out.println("1. Registrar novo usuário");
        System.out.println("2. Fazer login");
        System.out.println("3. Sair");
    }

    private static void registerUser() {
        scanner.nextLine();
        System.out.print("Digite seu e-mail: ");
        String email = scanner.nextLine();

        System.out.print("Digite uma senha: ");
        String senha = scanner.nextLine();

        try (FileWriter fw = new FileWriter("users.txt", true)) {
            fw.write(email + ";" + senha + "\n");
            System.out.println("Usuário cadastrado com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar usuário.");
        }
    }

    private static void login() {
        scanner.nextLine();
        System.out.print("E-mail: ");
        String email = scanner.nextLine();

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");
                if (data[0].equals(email) && data[1].equals(senha)) {
                    found = true;
                    loggedUser = email;
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo de usuários.");
        }

        if (found) {
            System.out.println("Login realizado com sucesso!");
            loadTasks();
            mainMenu();
        } else {
            System.out.println("E-mail ou senha incorretos.");
        }
    }

    // ----------- ÁREA PRINCIPAL DO SISTEMA (CRUD) ------------- //

    private static void mainMenu() {
        int choice;
        do {
            System.out.println("\n--- ToDo List App (" + loggedUser + ") ---");
            System.out.println("1. Adicionar Produto");
            System.out.println("2. Remover Produto");
            System.out.println("3. Exibir Produtos");
            System.out.println("4. Logout");

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
                    saveTasks();
                    System.out.println("Logout realizado.");
                    loggedUser = null;
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (choice != 4);
    }

    // ----------- FUNÇÕES DO CRUD ------------- //

    private static int getUserChoice() {
        System.out.print("Escolha uma opção: ");
        return scanner.nextInt();
    }

    private static void addTask() {
        scanner.nextLine();
        System.out.print("Nome do produto: ");
        String product = scanner.nextLine();

        System.out.print("Quantidade: ");
        String quantity = scanner.nextLine();

        tasks.add(product + " - Qtd: " + quantity);
        System.out.println("Produto adicionado!");
    }

    private static void removeTask() {
        if (tasks.isEmpty()) {
            System.out.println("Lista vazia.");
            return;
        }

        displayTasks();
        System.out.print("Número do item para remover: ");
        int index = scanner.nextInt();

        if (index >= 1 && index <= tasks.size()) {
            String removed = tasks.remove(index - 1);
            System.out.println("Removido: " + removed);
        } else {
            System.out.println("Índice inválido.");
        }
    }

    private static void displayTasks() {
        if (tasks.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }

        System.out.println("\n--- LISTA DE PRODUTOS ---");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

    // ----------- BANCO DE DADOS (ARQUIVOS) ------------- //

    private static void loadTasks() {
        tasks.clear();
        String filename = "tasks_" + loggedUser + ".txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                tasks.add(line);
            }
        } catch (IOException e) {
            // Arquivo ainda não existe → ok
        }
    }

    private static void saveTasks() {
        String filename = "tasks_" + loggedUser + ".txt";

        try (FileWriter fw = new FileWriter(filename)) {
            for (String t : tasks) {
                fw.write(t + "\n");
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar produtos.");
        }
    }
}
