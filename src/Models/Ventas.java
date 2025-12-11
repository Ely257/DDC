package Models;

public class Ventas {
    private int id;
    private int id_cliente;
    private String nomCliente;
    private double total;
    private String fecha;
    
    public Ventas(){
        
    }

    public Ventas(int id, int id_cliente, String nomCliente, double total, String fecha) {
        this.id = id;
        this.id_cliente = id_cliente;
        this.nomCliente = nomCliente;
        this.total = total;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getNomCliente() {
        return nomCliente;
    }

    public void setNomCliente(String nomCliente) {
        this.nomCliente = nomCliente;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    
}
