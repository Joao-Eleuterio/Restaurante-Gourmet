import java.rmi.*;

public class RestauranteClient  {
	public static void main(String args[]) {
		try {
			String url = "rmi://" + args[0] + "/AddServer";
			System.out.println("looking up " + url);
			RestauranteInterface restauranteInterface = (RestauranteInterface)Naming.lookup(url);
		} catch(Exception e) {
			System.out.println("RestauranteClient exception: " + e);
		}
	}
}
