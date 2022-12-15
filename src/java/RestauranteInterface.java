import java.rmi.*;

public interface RestauranteInterface extends Remote {
	String reservarMesa(String data, String horario, int nrPessoas) throws RemoteException;
	String cancelarMesa(String data, String horario) throws Exception;
	String listarMesas(String data) throws RemoteException;
	void setUtilizador(int id, String email, String nome) throws RemoteException;
}