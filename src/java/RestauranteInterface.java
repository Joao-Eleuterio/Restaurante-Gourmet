import java.rmi.*;

public interface RestauranteInterface extends Remote {
	String reservarMesa(String data, String horario, int nrPessoas) throws RemoteException;
	String cancelarMesa(String data, String horario, int idMesa) throws Exception;
	String listarMesas(String data) throws RemoteException;
}
