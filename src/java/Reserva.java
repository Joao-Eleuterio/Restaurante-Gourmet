import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Reserva {
    private String data;
    private Horario horario;
    private int nrPessoas;
    private int idMesa;
    private int idUtilizador;

    public Reserva(String data, Horario horario, int nrPessoas, int idUtilizador) {
        this.data = data;
        this.horario = horario;
        this.nrPessoas = nrPessoas;
        this.idUtilizador=idUtilizador;
    }

    public Reserva(String data, Horario horario, int nrPessoas, int idMesa,int idUtilizador) {
        this.data = data;
        this.horario = horario;
        this.nrPessoas = nrPessoas;
        this.idMesa = idMesa;
        this.idUtilizador=idUtilizador;
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

    public int getIdUtilizador() {
        return this.idUtilizador;}
    
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
		File tempFile = new File("database","myTempFile.txt");
	
		BufferedReader reader = new BufferedReader(new FileReader(RestauranteServer.reservasDB));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        
        String currentLine;
        // Percorrer o ficheiro e ver  se corresponde ao utilizador e aos dados(dia,horario)
        while ((currentLine = reader.readLine()) != null) {
          String[] current = currentLine.split(" : ");

          if ( current[0].equals(this.getData()) &&
            current[1].equals(this.getHorario().toString()) && 
            Integer.parseInt(current[2]) == getNrPessoas() && 
            Integer.parseInt(current[3]) == mesaAlocada() && 
            Integer.parseInt(current[4]) == getIdUtilizador()){
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
            reservasList.add(new Reserva(dataStrings[0], horario, Integer.parseInt(dataStrings[2]), Integer.parseInt(dataStrings[3]),Integer.parseInt(dataStrings[4])));
        }

        scanner.close();
        return reservasList;
    }

    @Override
    public String toString() {
        return data + " : " + horario.toString() + " : " + nrPessoas + " : " + idMesa + " : " + idUtilizador;
    }
}
