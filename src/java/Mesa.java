import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Mesa {
    private int id;
    private int capacidade;
    private String nome;
    private ArrayList<Reserva> reservas = new ArrayList<Reserva>();

    public Mesa(int id, int capacidade, String nome) {
        this.id = id;
        this.capacidade = capacidade;
        this.nome = nome;
    }

    public int getId() {
        return this.id;
    }

    public int getCapacidade() {
        return this.capacidade;
    }

    public String getNome() {
        return this.nome;
    }

    public ArrayList<Reserva> getReservas() {
        return this.reservas;
    }

    public Boolean estaDisponivel(String data, Horario horario) {
        for(int i = 0; i < reservas.size(); i++) {
            if(reservas.get(i).getHorario().toString().equals(horario.toString()) && reservas.get(i).getData().equals(data)) {
                return false;
            }
        }
        return true;
    }

    public String adicionarReserva(Reserva reserva) {
        reservas.add(reserva);
        reserva.escreverDB();
        return "Reserva para dia " + reserva.getData() + " ao " + reserva.getHorario().toString()
                + " na mesa " + reserva.mesaAlocada() + " realizada com sucesso!";
    }

    public void adicionarReservaOnLoad(Reserva reserva) {
        reservas.add(reserva);
    }

    public String apagarReserva(Reserva reserva) throws Exception {
        reservas.remove(reserva);
        reserva.removerDB();
        return "Reserva cancelada com sucesso!";
    }

    public static ArrayList<Mesa> loadDB(File file) throws FileNotFoundException {
        ArrayList<Mesa> mesasList = new ArrayList<>();
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String data = scanner.nextLine();
            String[] dataStrings = data.split(" : ");

            mesasList.add(new Mesa(Integer.parseInt(dataStrings[0]), Integer.parseInt(dataStrings[1]), dataStrings[2]));
        }

        // Preenche a lista de reservas de cada mesa
        for(int i = 0; i < mesasList.size(); i++){
            for(int j = 0; j < RestauranteServer.reservasList.size(); j++){
                if(mesasList.get(i).getId() == RestauranteServer.reservasList.get(j).mesaAlocada()){
                    mesasList.get(i).adicionarReservaOnLoad(RestauranteServer.reservasList.get(j));
                }
            }
        }

        scanner.close();
        return mesasList;
    }
}