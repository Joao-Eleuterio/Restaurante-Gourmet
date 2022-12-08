import java.rmi.*;
import java.rmi.server.*;

// this is the class with remote methods

public class RestauranteServerImpl extends UnicastRemoteObject implements RestauranteInterface {

	public RestauranteServerImpl() throws RemoteException {
	}

	public int Reservarmesa(String d1, String d2, int d3) throws RemoteException {
		// validar os parametros ex: a data tar certa com os "/" e se a data é
    // realistica tipo para não ser 20/20/10
    // Queres fazer com que so consiga reservar uma vez por cada horario? tipo so
    // uma vez no dia 10/10/2022 de dia e no maximo só uma vez a noite
    try {
		int horario;
		int[][] lista = new int[25][2];
  
		//Mudar o horario para int  dia=0, noite=1 se nao da invalido e da return
		_horario = _horario.toLowerCase();
		if (_horario.equals("dia")) {
		  horario = 0;
		} else if (_horario.equals("noite")) {
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
		FileReader fr = new FileReader("reserva.txt");
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

	public double Cancelarmesa(String dia, String _horario) throws RemoteException {
		return 0;
	}

	public String Listarmesas(String d1) throws RemoteException {
		return null;
	}
}
