import java.rmi.*;
import java.rmi.server.*;

// this is the class with remote methods

public class RestauranteServerImpl extends UnicastRemoteObject implements RestauranteInterface {
	int idUtilizador;
	String nomeUtilizador;
	String emailUtilizador;

	public RestauranteServerImpl() throws RemoteException {
	}

	public String reservarMesa(String data, String horario, int nrPessoas) throws RemoteException {
		Horario horarioConverted = Horario.convertToHorario(horario);
		if(horarioConverted == null) {
			return "Horario inválido, por favor insira almoço/jantar";
		}

		Reserva reserva = new Reserva(data, horarioConverted, nrPessoas);

		for(int i = 0; i < RestauranteServer.mesasList.size(); i++) {
			String msg = reserva.alocarMesa(RestauranteServer.mesasList.get(i));
			if(msg != "Problema de capacidade") {
				return msg;
			}
		}
		return "Não foi possivel realizar reserva para o horário escolhido!";
	}

	public String cancelarMesa(String data, String horario, int idMesa) throws Exception {
		Horario horarioConverted = Horario.convertToHorario(horario);
		if(horarioConverted == null) {
			return "Horario inválido, por favor insira almoço/jantar";
		}

		for(int i = 0; i < RestauranteServer.mesasList.size(); i++) {
			for(int j = 0; j < RestauranteServer.mesasList.get(i).getReservas().size(); j++) {
				if(RestauranteServer.mesasList.get(i).getReservas().get(j).getData() == data &&
				RestauranteServer.mesasList.get(i).getReservas().get(j).getHorario() == horarioConverted &&
				RestauranteServer.mesasList.get(i).getReservas().get(j).mesaAlocada() == idMesa) {
					return RestauranteServer.mesasList.get(i).apagarReserva(RestauranteServer.mesasList.get(i).getReservas().get(j));
				}
			}
		}

		return "Não foi possivel cancelar a sua reserva!";
	}

	public String listarMesas(String data) throws RemoteException { 
		String lista = "";

		for(int i = 0; i < RestauranteServer.mesasList.size(); i++) {
			if(RestauranteServer.mesasList.get(i).estaDisponivel(data, Horario.ALMOCO)) {
				lista += "A mesa " + RestauranteServer.mesasList.get(i).getNome() +
					" encontra-se disponivel ao almoço.\n"; 
			}

			if(RestauranteServer.mesasList.get(i).estaDisponivel(data, Horario.JANTAR)) {
				lista += "A mesa " + RestauranteServer.mesasList.get(i).getNome() +
					" encontra-se disponivel ao jantar.\n"; 
			}
		}
		return lista;
	}
}
