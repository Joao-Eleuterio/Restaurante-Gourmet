import java.rmi.*;
import java.util.Scanner;
public class AddClient {
  public static void main(String args[]) {
    try {
      String addServerURL = "rmi://" + args[0] + "/AddServer";
      AddServerIntf addServerIntf =
                    (AddServerIntf)Naming.lookup(addServerURL);
     /* System.out.println("The first number is: " + args[1]);
      double d1 = Double.valueOf(args[1]).doubleValue();
      System.out.println("The second number is: " + args[2]);

      double d2 = Double.valueOf(args[2]).doubleValue();
  */ int[][] lista = new int[25][2];
  do{
      System.out.println("--------------------------------------");
      System.out.println("| 1- Reservar uma mesa               |");
      System.out.println("| 2- Cancelar uma mesa               |");
      System.out.println("| 3- Listar mesas                    |");
      System.out.println("| 4- Registar outro utilizador       |");
      System.out.println("| 0- Sair                            |");
      System.out.println("--------------------------------------");
      System.out.print("Escolha a opção:");
      
      Scanner read = new Scanner(System.in);
      int opcao = read.nextInt();System.out.println("\n");
                System.out.println("Introduza o dia (dd/mm/aaaa): ");
              String dia = new Scanner(System.in).nextLine(); 
              
      switch(opcao){
      case 1:{
              System.out.println("Introduza de dia/noite: ");
              String hora = new Scanner(System.in).nextLine(); 
              System.out.println("Quantas pessoas: "); 
              int qntpessoas= new Scanner(System.in).nextInt();
             
              if(addServerIntf.Reservarmesa(dia, hora,qntpessoas)==1){
              System.out.println("Conseguiu reservar mesa para "+qntpessoas+" no dia "+dia);
              }else{
              System.out.println("Não conseguiu reservar!");
              }
              break;
              }
      case 2:{addServerIntf.Cancelarmesa(dia,"a");break;}
      case 3:{ System.out.println(addServerIntf.Listarmesas(dia)); break;}
      case 0:{System.exit(0);}
      
      }
  }while(true);
    }
    catch(Exception e) {
      System.out.println("Exception: " + e);
    }
  }
}
