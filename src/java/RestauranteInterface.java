import java.rmi.*;

public interface RestauranteInterface extends Remote {
	int Reservarmesa(String d1, String d2,int d3) throws RemoteException;
	double Cancelarmesa(String dia, String _horario) throws RemoteException;
	String Listarmesas(String d1) throws RemoteException;
	boolean RegistarUtilizador(String d0, String d1, String d2) throws RemoteException;
	boolean login(String d1,String d2) throws RemoteException;
}
