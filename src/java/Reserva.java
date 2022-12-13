import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Reserva {
    private String data;
    private Horario horario;
    private int nrPessoas;
    private int idMesa;
    
    public Reserva(String data, Horario horario, int nrPessoas) {
        this.data = data;
        this.horario = horario;
        this.nrPessoas = nrPessoas;
    }

    public Reserva(String data, Horario horario, int nrPessoas, int idMesa) {
        this.data = data;
        this.horario = horario;
        this.nrPessoas = nrPessoas;
        this.idMesa = idMesa;
    }

    public int mesaAlocada() {
        return this.idMesa;
    }

    public int getNrPessoas() {
        return this.nrPessoas;
    }

    public Horario getHorario() {
        return this.horario;
    }

    public String getData() {
        return this.data;
    }

    public void escreverDB() {
        RestauranteServer.reservasList.add(this);

        try (FileWriter fw = new FileWriter("./database/reservas.txt", true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter out = new PrintWriter(bw)) {
		        out.println(this.toString());
		} catch (IOException e) {
		    System.out.println(e);
		}
    }

    public void removerDB() throws Exception {
		File tempFile = new File("myTempFile.txt");
	
		BufferedReader reader = new BufferedReader(new FileReader(RestauranteServer.reservasDB));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String currentLine;
        // Percorrer o ficheiro e ver  se corresponde ao utilizador e aos dados(dia,horario)
        while ((currentLine = reader.readLine()) != null) {
          String[] current = currentLine.split(" : ");
          if (current[0] == this.data && Horario.convertToHorario(current[1]) == this.horario
              && Integer.parseInt(current[2]) == this.nrPessoas 
              && Integer.parseInt(current[3]) == this.idMesa) {
                RestauranteServer.reservasList.remove(this);
                continue;
            }
            writer.write(currentLine + System.getProperty("line.separator"));
        }
        
        tempFile.renameTo(RestauranteServer.reservasDB);
        RestauranteServer.reservasDB = tempFile;
        reader.close();
        writer.close();
    }

    public String alocarMesa(Mesa mesa) {
        if(!mesa.estaDisponivel(this.data, this.horario)) {
            return "Problema de capacidade";
        }
        
        switch(mesa.getCapacidade()){
            case 2:
                if(this.nrPessoas <= 2) {
                    idMesa = mesa.getId();
                    return mesa.adicionarReserva(this);
                }
                break;
            case 4:
                if(this.nrPessoas >= 2 && this.nrPessoas <= 4) {
                    idMesa = mesa.getId();
                    return mesa.adicionarReserva(this);
                }
                break;
            case 8:
                if(this.nrPessoas >= 4 && this.nrPessoas <= 8) {
                    idMesa = mesa.getId();
                    return mesa.adicionarReserva(this);
                }
                break;
            case 12:
                if(this.nrPessoas >= 8 && this.nrPessoas <= 12) {
                    idMesa = mesa.getId();
                    return mesa.adicionarReserva(this);
                }
                break;
        }
        return "Problema de capacidade";
    }

    public static ArrayList<Reserva> loadDB(File file) throws FileNotFoundException {
        ArrayList<Reserva> reservasList = new ArrayList<>();
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String data = scanner.nextLine();
            String[] dataStrings = data.split(" : ");
            Horario horario = Horario.convertToHorario(dataStrings[1]);        
            reservasList.add(new Reserva(dataStrings[0], horario, Integer.parseInt(dataStrings[2]), Integer.parseInt(dataStrings[3])));
        }

        scanner.close();
        return reservasList;
    }

    @Override
    public String toString() {
        return data + " : " + horario.toString() + " : " + nrPessoas + " : " + idMesa;
    }
}
