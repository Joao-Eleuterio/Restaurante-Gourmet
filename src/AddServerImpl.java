import java.rmi.*;
import java.rmi.server.*;
import java.io.*;
import java.io.File;
import java.io.IOException;

public class AddServerImpl extends UnicastRemoteObject
    implements AddServerIntf {
  private boolean firstin = true;

  public AddServerImpl() throws RemoteException {
  }

  public int Reservarmesa(String dia, String _horario, int qntpessoas) throws RemoteException {
    // validar os parametros ex: a data tar certa com os "/" e se a data é
    // realistica tipo para não ser 20/20/10
    try {
      int horario;
      int[][] lista = new int[25][2];
      if (_horario.equals("dia")) {
        horario = 0;
      } else {
        horario = 1;
      }

      int i = 0;
      FileReader fr = new FileReader("reserva.txt");
      BufferedReader reader = new BufferedReader(fr);
      String st = null;

      while ((st = reader.readLine()) != null) {
        System.out.println(st);
        String[] st_split = st.split("@");
        if (st_split[0].equals(dia)) {
          lista[Integer.parseInt(st_split[2])][horario] = 1;
        }
      }
      reader.close();
      for (i = 0; i < 25; i++) {
        System.out.print(lista[i][0]);
      }
      for (i = 0; i < 25; i++) {
        if (qntpessoas <= 2) {
          if (lista[i][horario] == 0) {
            EscreveReserva((i), horario, dia);
            return 1;
          }
        }
        if (qntpessoas <= 4 && i > 10) {
          if (lista[i][horario] == 0) {
            EscreveReserva((i), horario, dia);
            return 1;
          }
        }
        if (qntpessoas <= 8 && i > 15) {
          if (lista[i][horario] == 0) {
            EscreveReserva((i), horario, dia);
            return 1;
          }
        }
        if (qntpessoas <= 12 && i > 20) {
          if (lista[i][horario] == 0) {
            EscreveReserva((i), horario, dia);
            return 1;
          }
        }
      }

    } catch (Exception c) {
      System.out.println(c);
    }

    return 0;
  }

  private void EscreveReserva(int mesa, int horario, String data) throws RemoteException {
    try (FileWriter fw = new FileWriter("reserva.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter out = new PrintWriter(bw)) {
      if (firstin) {
        out.println();
        firstin = false;
      } else {
        out.println(data + "@" + horario + "@" + mesa);

      }
    } catch (IOException e) {
      System.out.println(e);
    }
  }

  public double Cancelarmesa(String dia, String _horario) throws RemoteException {
    // Ver se existe a reserva | ver se é o id correspondente
    try {
      File inputFile = new File("reserva.txt");
      File tempFile = new File("myTempFile.txt");

      BufferedReader reader = new BufferedReader(new FileReader(inputFile));
      BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

      // Se conseguir cancelar return 1 se nao return 0
      // Ver se existe reserva do id da pessoa no dia e no horario

      // texto que irá remover v
      String lineToRemove = "10/10/2022@0@12";
      String currentLine;

      while ((currentLine = reader.readLine()) != null) {
        // trim newline when comparing with lineToRemove
        String trimmedLine = currentLine.trim();
        if (trimmedLine.equals(lineToRemove))
          continue;
        writer.write(currentLine + System.getProperty("line.separator"));
      }
      writer.close();
      reader.close();
      System.out.println("Mesa cancelada!");
      return 1;
    } catch (Exception c) {
      System.out.println(c);
      return 0;
    }

  }

  public String Listarmesas(String dia) throws RemoteException {

    String lista_mesas = "";
    try {
      FileReader fr = new FileReader("reserva.txt");
      BufferedReader reader = new BufferedReader(fr);
      String st = null;

      int[][] lista = new int[25][2];
      while ((st = reader.readLine()) != null) {
        System.out.println(st);
        String[] st_split = st.split("@");
        if (st_split[0].equals(dia)) {
          lista[Integer.parseInt(st_split[2])][Integer.parseInt(st_split[1])] = 1;
        }
      }

      for (int i = 0; i < 25; i++) {
        if (lista[i][0] == 0) {
          lista_mesas += "mesa " + (i + 1) + ": livre de dia\n";
        }
        if (lista[i][1] == 0) {
          lista_mesas += "mesa " + (i + 1) + ": livre de noite\n";
        }
      }
      reader.close();
    } catch (Exception c) {
      System.out.println(c);
    }
    return lista_mesas;
  }

  public boolean RegistarUtilizador(String nome, String email, String password) throws RemoteException {
    //regex do mail que tem de ter @ e tal
    try {
      String ultimo = "";
      FileReader fr = new FileReader("utilizador.txt");
      BufferedReader reader = new BufferedReader(fr);
      String line = "";

      while (line != null) {
        line = reader.readLine();
        if (line != null) {
          ultimo = line;
          String[] dados = line.split("|");
          if (dados[2] == email) {
            return false;
          }

        }
      }

      int idUltimoUtilizador = 1;
      if (!ultimo.equals("")) {
        idUltimoUtilizador = (Integer.parseInt(ultimo.split("|")[0] + 1));
      }

      FileWriter fw = new FileWriter("utilizador.txt", true);
      BufferedWriter bw = new BufferedWriter(fw);
      PrintWriter out = new PrintWriter(bw);

      out.println(idUltimoUtilizador + "|" + nome + "|" + email + "|" + password);

      out.close();
      reader.close();
      return true;
    } catch (IOException c) {
      c.printStackTrace();
    }

    return false;
  }

  public boolean login(String email, String password) throws RemoteException {
    try {
      FileReader fr = new FileReader("utilizador.txt");
      BufferedReader reader = new BufferedReader(fr);
      String st = null;
System.out.println(email+"    "+ password);

      while ((st = reader.readLine()) != null) {
        String[] st_split = st.split("\\|");
        for(int i=0;i<st_split.length;i++){System.out.println(st_split[i]);}
        System.out.println(st_split[2]+"   "+ st_split[3]);
        if (st_split != null && st_split[2].equals(email) && st_split[3].equals(password)) {
          return true;
        }
      }
    } catch (IOException c) {
      c.printStackTrace();
    }
    return false;
  }
}
// TODO
// Perguntar ao stor em listar mesas se são todas ou então temos de perguntar se
// é ao almoço ou ao jantar
