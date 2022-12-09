import java.rmi.*;
import java.util.Scanner;

public class RestauranteClient {
  public static void main(String args[]) {
    int opcao;
    try {
      String serverURL = "rmi://" + args[0] + "/RestauranteServer";
      RestauranteInterface restauranteInterface = (RestauranteInterface) Naming.lookup(serverURL);
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

            if (restauranteInterface.Reservarmesa(dia, hora, qntpessoas) == 1) {
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

            restauranteInterface.Cancelarmesa(dia, hora);
            break;
          }
          case 3: {
            System.out.println("Introduza o dia (dd/mm/aaaa): ");
            Scanner diaS = new Scanner(System.in);
            String dia = diaS.nextLine();

            System.out.println(restauranteInterface.Listarmesas(dia));
            break;
          }

        }
      } while (opcao != 0);
    } catch (Exception e) {
      System.out.println("Exception: " + e);
    }
  }
}