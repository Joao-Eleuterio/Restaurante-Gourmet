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
  */
  do{System.out.println("LOGIN");
  System.out.println("1 - faça login");
  System.out.println("2 - faça registo");
  System.out.println("0 - sair");
  Scanner read = new Scanner(System.in);
  int opcao = read.nextInt();System.out.println("\n");
  read.close();
  switch(opcao){
    case 1: if(true){
      do{
      System.out.println("--------------------------------------");
      System.out.println("| 1- Reservar uma mesa               |");
      System.out.println("| 2- Cancelar uma mesa               |");
      System.out.println("| 3- Listar mesas                    |");
      System.out.println("| 0- Sair                            |");
      System.out.println("--------------------------------------");
      System.out.print("Escolha a opção:");
      
      Scanner read1 = new Scanner(System.in);
      opcao = read1.nextInt();System.out.println("\n");
                System.out.println("Introduza o dia (dd/mm/aaaa): ");
              Scanner diaS = new Scanner(System.in);
              String dia = diaS.nextLine();
      read1.close();
      diaS.close();
      switch(opcao){
      case 1:{
              System.out.println("Introduza de dia/noite: ");
              Scanner horaS = new Scanner(System.in); 
              String hora = horaS.nextLine();
              System.out.println("Quantas pessoas: "); 
              Scanner qntpessoasS= new Scanner(System.in);
              int qntpessoas = qntpessoasS.nextInt();
              if(addServerIntf.Reservarmesa(dia, hora,qntpessoas)==1){
              System.out.println("Conseguiu reservar mesa para "+qntpessoas+" no dia "+dia);
              }else{
              System.out.println("Não conseguiu reservar!");
              }
              qntpessoasS.close();
              horaS.close();
              break;
              }
      case 2:{addServerIntf.Cancelarmesa(dia,"a");break;}
      case 3:{ System.out.println(addServerIntf.Listarmesas(dia)); break;}
      case 4:{ System.out.println("Introduza um email: ");
              String email = new Scanner(System.in).nextLine();
              System.out.println("Introduza uma password: "); 
              String password = new Scanner(System.in).nextLine(); 
        addServerIntf.RegistarUtilizador(email,password);break;}
      
      }
  }while(opcao!=0);break;
    }
    case 2: {addServerIntf.RegistarUtilizador("","");break;}
    case 0: System.exit(0);
  }
  
  }while(true);
    }
    catch(Exception e) {
      System.out.println("Exception: " + e);
    }
  }
}
