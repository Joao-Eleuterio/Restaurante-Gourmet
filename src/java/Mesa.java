import java.util.ArrayList;

public class Mesa {
    private int id;
    private ArrayList<Reserva> reservas = new ArrayList<Reserva>();

    public Mesa(int id) {
        this.id = id;
    }
}