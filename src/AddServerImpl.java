import java.rmi.*;
import java.rmi.server.*;

import java.io.*;

public class AddServerImpl extends UnicastRemoteObject
  implements AddServerIntf {
  private boolean firstin=true;
  public AddServerImpl() throws RemoteException {
  }
  public int Reservarmesa(String dia ,String _horario, int qntpessoas) throws RemoteException{
    //validar os parametros ex: a data tar certa com os "/" e se a data é realistica tipo para não ser 20/20/10 
    try{
    int horario;
    int[][] lista = new int[25][2];
    if(_horario.equals("dia")){
      horario=0;
    }else{horario=1;}
     
       int i=0;
       FileReader fr = new FileReader("reservas.txt");
       BufferedReader reader = new BufferedReader(fr);
       String st = null;
       
      
        while ((st = reader.readLine()) != null){
        System.out.println(st);
            String[] st_split = st.split("@");
            if(st_split[0].equals(dia)){
                lista[Integer.parseInt(st_split[2])][horario]=1;
            }
        }
    
    for(i=0;i<25;i++){
      System.out.print(lista[i][0]);
    }
    for(i=0;i<25;i++){
      if(qntpessoas<=2){
        if(lista[i][horario]==0){
            EscreveReserva((i),horario,dia);return 1;
        }
      }
      if(qntpessoas<=4 && i>10){
        if(lista[i][horario]==0){
          EscreveReserva((i),horario,dia);return 1;
        }
      }
      if(qntpessoas<=8 && i>15){
        if(lista[i][horario]==0){
          EscreveReserva((i),horario,dia);return 1;
        } 
      }
      if(qntpessoas<=12 && i>20){
        if(lista[i][horario]==0){
          EscreveReserva((i),horario,dia);return 1;
        } 
      }
    }
    }catch(Exception c){
    System.out.println(c);
    }
    return 0;
  }
  private void EscreveReserva(int mesa,int horario,String data)throws RemoteException{
      try(FileWriter fw = new FileWriter("reservas.txt", true);
    BufferedWriter bw = new BufferedWriter(fw);
    PrintWriter out = new PrintWriter(bw))
{
  if(firstin){
    out.println();
    firstin=false;
  }else{
    out.println(data+"@"+horario+"@"+mesa);
  
}
} catch (IOException e) {
    System.out.println(e);
}
  }
  public double Cancelarmesa(String dia, String _horario) throws RemoteException{
    //Ver se existe a reserva        | ver se é o id correspondente
    try{
      File inputFile = new File("reservas.txt");
      File tempFile = new File("myTempFile.txt");

      BufferedReader reader = new BufferedReader(new FileReader(inputFile));
      BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

      //texto que irá remover       v
      String lineToRemove = "10/10/2022@0@12";
      String currentLine;

      while((currentLine = reader.readLine()) != null) {
      // trim newline when comparing with lineToRemove
      String trimmedLine = currentLine.trim();
      if(trimmedLine.equals(lineToRemove)) continue;
      writer.write(currentLine + System.getProperty("line.separator"));
      }
      writer.close(); 
      reader.close(); 
      boolean successful = tempFile.renameTo(inputFile);
      System.out.println("Mesa cancelada!");
    }catch(Exception c){
      System.out.println(c);
    }
    return 0;
  }
  public String Listarmesas(String dia) throws RemoteException{

  String lista_mesas="";
  try{
      FileReader fr = new FileReader("reservas.txt");
       BufferedReader reader = new BufferedReader(fr);
       String st = null;
       
        int[][] lista = new int[25][2];
        while ((st = reader.readLine()) != null){
        System.out.println(st);
            String[] st_split = st.split("@");
            if(st_split[0].equals(dia)){
              lista[Integer.parseInt(st_split[2])][Integer.parseInt(st_split[1])]=1;
            }
        }
        
        for(int i=0;i<25;i++){
            if(lista[i][0]==0)
            {
              lista_mesas+= "mesa "+(i+1)+": livre de dia\n";
            }
            if(lista[i][1]==0){
              lista_mesas+= "mesa "+(i+1)+": livre de noite\n";
            }
        }
        }catch(Exception c){
        System.out.println(c);
        }
      return lista_mesas;}
  public double RegistarUtilizador(double d1, double d2) throws RemoteException{
    //registar o utilizador que dados vamos pedir? 
    return 0.0;}

}


//TODO
//Perguntar ao stor em listar mesas se são todas ou então temos de perguntar se é ao almoço ou ao jantar
