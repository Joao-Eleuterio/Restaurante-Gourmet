import java.rmi.*;
import java.util.Scanner;

public class AddClient {
 
  public static void main(String args[]) {
    try {
      String addServerURL = "rmi://" + args[0] + "/AddServer";
      AddServerIntf addServerIntf = (AddServerIntf) Naming.lookup(addServerURL);
      do {
        System.out.println("----------------LOGIN------------------");
        System.out.println("|1 - faça login                       |");
        System.out.println("|2 - faça registo                     |");
        System.out.println("|0 - sair                             |");
        System.out.println("---------------------------------------");
        Scanner read = new Scanner(System.in);
        int opcao = read.nextInt();
        System.out.println("\n");

        switch (opcao) {
          case 1: {
            System.out.println("Introduza o seu email: ");
            Scanner emailS = new Scanner(System.in);
            String email = emailS.nextLine();

            System.out.println("Introduza a password: ");
            Scanner passwordS = new Scanner(System.in);
            String password = passwordS.nextLine();

            if (addServerIntf.login(email, password)) {

              do {
                System.out.println("--------------------------------------");
                System.out.println("| 1- Reservar uma mesa               |");
                System.out.println("| 2- Cancelar uma mesa               |");
                System.out.println("| 3- Listar mesas                    |");
                System.out.println("| 0- Sair                            |");
                System.out.println("--------------------------------------");
                System.out.print("Escolha a opção:");

                Scanner read1 = new Scanner(System.in);
                opcao = read1.nextInt();
                System.out.println("\n");

                switch (opcao) {
                  case 1: {
                    System.out.println("Introduza o dia (dd/mm/aaaa): ");
                    Scanner diaS = new Scanner(System.in);
                    String dia = diaS.nextLine();

                    System.out.println("Introduza de dia/noite: ");
                    Scanner horaS = new Scanner(System.in);
                    String hora = horaS.nextLine();

                    System.out.println("Quantas pessoas: ");
                    Scanner qntpessoasS = new Scanner(System.in);
                    int qntpessoas = qntpessoasS.nextInt();

                    if (addServerIntf.Reservarmesa(dia, hora, qntpessoas) == 1) {
                      System.out.println("Conseguiu reservar mesa para " + qntpessoas + " no dia " + dia);
                    } else {
                      System.out.println("Não conseguiu reservar!");
                    }

                    break;
                  }
                  case 2: {
                    System.out.println("Introduza o dia (dd/mm/aaaa): ");
                    Scanner diaS = new Scanner(System.in);
                    String dia = diaS.nextLine();

                    System.out.println("Introduza de dia/noite: ");
                    Scanner horaS = new Scanner(System.in);
                    String hora = horaS.nextLine();

                    addServerIntf.Cancelarmesa(dia, hora);
                    break;
                  }
                  case 3: {
                    System.out.println("Introduza o dia (dd/mm/aaaa): ");
                    Scanner diaS = new Scanner(System.in);
                    String dia = diaS.nextLine();

                    System.out.println(addServerIntf.Listarmesas(dia));
                    break;
                  }

                }
              } while (opcao != 0);
            }else{
              System.out.println("\nLogin negado!\n");
            }
            break;
          }
          case 2: {
            System.out.println("Introduza o seu email: ");
            Scanner emailS = new Scanner(System.in);
            String email = emailS.nextLine();

            System.out.println("Introduza o seu nome: ");
            Scanner nomeS = new Scanner(System.in);
            String nome = nomeS.nextLine();

            System.out.println("Introduza a password: ");
            Scanner passwordS = new Scanner(System.in);
            String password = passwordS.nextLine();

            if(addServerIntf.RegistarUtilizador(nome, email, password)){
              System.out.println("\nRegisto com sucesso!\n");
            }else{
              System.out.println("\nRegisto negado!\n");
            }
            break;
          }
          case 0:
            System.exit(0);
        }

      } while (true);
    } catch (

    Exception e) {
      System.out.println("Exception: " + e);
    }
  }
}