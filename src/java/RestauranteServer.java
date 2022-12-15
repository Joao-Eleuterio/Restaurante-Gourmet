import java.rmi.*;
import java.util.ArrayList;
import java.io.*;

public class RestauranteServer {
	public static ArrayList<Reserva> reservasList = new ArrayList<>();
	public static ArrayList<Mesa> mesasList = new ArrayList<>();
	public static File reservasDB = new File("./database/reservas.txt");
	public static File mesasDB = new File("./database/mesas.txt");
	
	public static void main(String args[]) {
		try {
			reservasList.clear();
			mesasList.clear();
			RestauranteServerImpl restauranteServerImpl = new RestauranteServerImpl();
			Naming.rebind("RestauranteServer", restauranteServerImpl);

			/* Criar a base de dados de reservas assim que o servidor abrir, se
			esta não existir */
      		if (reservasDB.createNewFile()) {
        		System.out.println("File created: " + reservasDB.getName());
      		} else {
				reservasList = Reserva.loadDB(reservasDB);
				System.out.println("Database loaded: "+ reservasDB.getName());	
      		}

			/* Criar a base de dados de reservas assim que o servidor abrir, se
			esta não existir */
      		if (mesasDB.createNewFile()) {
        		System.out.println("File created: " + mesasDB.getName());
      		} else {
				mesasList = Mesa.loadDB(mesasDB);
				System.out.println("Database loaded: "+ mesasDB.getName());
      		}
		} catch (Exception e) {
			System.out.println("Server failed: " + e.getMessage());
			return;
		}
	}
}