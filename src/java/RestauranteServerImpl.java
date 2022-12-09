import java.rmi.*;
import java.rmi.server.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.regex.*;
import java.io.*;

// this is the class with remote methods

public class RestauranteServerImpl extends UnicastRemoteObject implements RestauranteInterface {
	int idUtilizador;
	String nomeUtilizador;
	String emailUtilizador;

	public RestauranteServerImpl() throws RemoteException {
	}

	public int Reservarmesa(String dia, String horarioParam, int qntpessoas) throws RemoteException {
	// validar os parametros ex: a data tar certa com os "/" e se a data é
    // realistica tipo para não ser 20/20/10
    // Queres fazer com que so consiga reservar uma vez por cada horario? tipo so
    // uma vez no dia 10/10/2022 de dia e no maximo só uma vez a noite
    try {
		int horario;
		int[][] lista = new int[25][2];
  
		//Mudar o horario para int  dia=0, noite=1 se nao da invalido e da return
		horarioParam = horarioParam.toLowerCase();
		if (horarioParam.equals("dia")) {
		  horario = 0;
		} else if (horarioParam.equals("noite")) {
		  horario = 1;
		} else {
		  System.out.println("Horario invalido");
		  return -1;
		}
		//Validar Email
		if (!confirmarData(dia)) {
		  System.out.println("Data invalida!");
		  return -1;
		} else {
		  System.out.println("Data valida!");
		}
  
		int i = 0;
		FileReader fr = new FileReader("reservas.txt");
		BufferedReader reader = new BufferedReader(fr);
		String st = null;
		//Percorrer ficheiro e guardar num array as ocupadas no certo dia
		while ((st = reader.readLine()) != null) {
		  System.out.println(st);
		  String[] st_split = st.split("@");
		  if (st_split[1].equals(dia)) {
			lista[Integer.parseInt(st_split[2])][horario] = 1;
		  }
		  //Se ja existir da mesma pessoa no mesmo dia e no mesmo horario dá return
		  if (Integer.parseInt(st_split[0]) == idUtilizador && st_split[1].equals(dia)
			  && Integer.parseInt(st_split[2]) == horario) {
			System.out.println("Já existe uma marcação da mesma pessoa!");
			return -2;
		  }
		}
		//Marcar mesas 
		System.out.println("Vou marcar para " + qntpessoas + " pessoas");
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
		try (FileWriter fw = new FileWriter("reservas.txt", true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter out = new PrintWriter(bw)) {
			  //Escrever no ficheiro id+data+horario+mesa
		  out.println(idUtilizador + "@" + data + "@" + horario + "@" + mesa);
	
		} catch (IOException e) {
		  System.out.println(e);
		}
	}

	private boolean confirmarData(String data) {
		//Validade data
	
		//Formato da data
		DateTimeFormatter dataForm = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		//Validar a data que o utilizador escreveu
		LocalDate dataVer = LocalDate.parse(data, dataForm);
		//Data de hoje
		LocalDate hoje = LocalDate.now();
	
		// 0 == hoje 1 == amanha ou depois -1 == ontem ou antes
		//Só pode reservar hoje e depois 
		return dataVer.compareTo(hoje) >= 0;
	}

	public double Cancelarmesa(String dia, String _horario) throws RemoteException {
		// Ver se existe a reserva | ver se é o id correspondente
		try {
		  boolean foiCancelada = false;
		  File inputFile = new File("reservas.txt");
		  File tempFile = new File("myTempFile.txt");
	
		  BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		  BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
	
		  //mudar a string para int dia=0, noite=1 e nao for ta errado e retorn 0
		  int horario;
		  if (_horario.equals("dia")) {
			horario = 0;
		  } else if (_horario.equals("noite")) {
			horario = 1;
		  } else {
			return 0;
		  }
	
		  // Se conseguir cancelar return 1 se nao return 0
	
		  String currentLine;
		  // Percorrer o ficheiro e ver  se corresponde ao utilizador e aos dados(dia,horario)
		  while ((currentLine = reader.readLine()) != null) {
			String[] current = currentLine.split("@");
			if (Integer.parseInt(current[0]) == idUtilizador && current[1].equals(dia)
				&& Integer.parseInt(current[2]) == horario) {
			  foiCancelada = true;
			  continue;
			}
			writer.write(currentLine + System.getProperty("line.separator"));
		  }
		  //mudar de nome do ficheiro
		  
		  reader.close();
		  writer.close();
		  if (foiCancelada) {
			System.out.println("Mesa cancelada!");
			return 1;
		  } else {
			System.out.println("Não existe esta reserva");
			return -1;
		  }
		} catch (Exception c) {
		  System.out.println(c);
		  return 0;
		}
	
	}
	
	public String Listarmesas(String dia) throws RemoteException {
	
		String lista_mesas = "";
		try {
		  FileReader fr = new FileReader("reservas.txt");
		  BufferedReader reader = new BufferedReader(fr);
		  String st = null;
	
		  int[][] lista = new int[25][2];
		  // Ler o ficheiro e guardar todas as reservas do dia  num array
		  while ((st = reader.readLine()) != null) {
			System.out.println(st);
	
			String[] st_split = st.split("@");
	
			if (st_split[1].equals(dia)) {
			  lista[Integer.parseInt(st_split[3])][Integer.parseInt(st_split[2])] = 1;
			}
		  }
		  //Print de todas as mesas de dia
		  System.out.println("Mesas livre de dia");
		  for (int i = 0; i < 25; i++) {
			if (lista[i][0] == 0) {
			  lista_mesas += "mesa " + (i + 1) + "\n";
			}
		  }
		  //Print de todas as mesas de noite
		  System.out.println("Mesas livre de noite");
		  for (int i = 0; i < lista.length; i++) {
			if (lista[i][1] == 0) {
			  lista_mesas += "mesa " + (i + 1) + "\n";
			}
		  }
	
		} catch (Exception c) {
		  System.out.println(c);
		}
		return lista_mesas;
	}

	public boolean RegistarUtilizador(String nome, String email, String password) throws RemoteException {
		// regex do mail que tem de ter @ e tal
		try {
		  String ultimo = "";
		  FileReader fr = new FileReader("utilizador.txt");
		  BufferedReader reader = new BufferedReader(fr);
		  String line = "";
	
		  //Percorrer o ficheiro para saber o ultimo utilizador e saber se o email já está a ser usado ou não
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
		  // VALIDAR EMAIL 
		  String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
		  Pattern pattern = Pattern.compile(regex);
		  Matcher matcher = pattern.matcher(email);
		  System.out.println(email + " : " + matcher.matches());
		  if(!matcher.matches()){
		   System.out.println("Email é invalido");
		   return false;
		  }
	
		  //  ID o id para o novo utilizador
		  int idUltimoUtilizador = 1;
		  if (!ultimo.equals("")) {
			idUltimoUtilizador = (Integer.parseInt(ultimo.split("|")[0]) + 1);
		  }
	
		  //Escrever no ficheiro
		  FileWriter fw = new FileWriter("utilizador.txt", true);
		  BufferedWriter bw = new BufferedWriter(fw);
		  PrintWriter out = new PrintWriter(bw);
	
		  out.println(idUltimoUtilizador + "|" + nome + "|" + email + "|" + password);
	
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
		  //Ler o ficheiro e ver se existe o email com a password certa
		  while ((st = reader.readLine()) != null) {
			String[] st_split = st.split("\\|");
	
			if (st_split != null && st_split[2].equals(email) && st_split[3].equals(password)) {
			  idUtilizador = Integer.parseInt(st_split[0]);
			  nomeUtilizador = st_split[1];
			  emailUtilizador = st_split[2];
			  return true;
			}
		  }
		} catch (IOException c) {
		  c.printStackTrace();
		}
		return false;
	  }
}
