import java.rmi.*;
import java.io.*;

public class RestauranteServer {
	public static void main(String args[]) {
		try {
			RestauranteServerImpl restauranteServerImpl = new RestauranteServerImpl();
			Naming.rebind("RestauranteServer", restauranteServerImpl);

			/* Criar a base de dados de reservas assim que o servidor abrir, se
			esta não existir */
			File reservasDB = new File("reservas.txt");
      		if (reservasDB.createNewFile()) {
        		System.out.println("File created: " + reservasDB.getName());
      		} else {
        		System.out.println("File already exists.");    
      		}
			File utilizadoresDB = new File("utilizador.txt");
			if (utilizadoresDB.createNewFile()) {
				System.out.println("File created: " + utilizadoresDB.getName());
			} else {
				System.out.println("File already exists.");    
			}
		} catch (Exception e) {
			System.out.println("Server failed:" + e.getMessage());
		}
	}
}
