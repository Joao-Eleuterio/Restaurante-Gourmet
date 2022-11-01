import java.rmi.*;
import java.io.*;

public class RestauranteServer {
	public static void main(String args[]) {
		if (args.length != 1) {
			System.err.println("usage: java SampleServer rmi_port");
			System.exit(1);
		}

		try {
			String url = "//localhost:" + args[0] + "/Restaurante";
			Naming.rebind(url, new RestauranteServerImpl());
			System.out.println("server " + url + " is running...");

			/* Criar a base de dados de reservas assim que o servidor abrir, se
			esta não existir */
			File reservasDB = new File("reservas.txt");
      		if (reservasDB.createNewFile()) {
        		System.out.println("File created: " + reservasDB.getName());
      		} else {
        		System.out.println("File already exists.");    
      		}
		} catch (Exception e) {
			System.out.println("Server failed:" + e.getMessage());
		}
	}
}
