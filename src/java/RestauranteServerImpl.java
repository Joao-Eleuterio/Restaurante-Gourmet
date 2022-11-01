import java.rmi.*;
import java.rmi.server.*;

// this is the class with remote methods

public class RestauranteServerImpl extends UnicastRemoteObject implements RestauranteInterface {

	public RestauranteServerImpl() throws RemoteException {
	}

	public int Reservarmesa(String d1, String d2, int d3) throws RemoteException {
		return 0;
	}

	public double Cancelarmesa(String dia, String _horario) throws RemoteException {
		return 0;
	}

	public String Listarmesas(String d1) throws RemoteException {
		return null;
	}
}
