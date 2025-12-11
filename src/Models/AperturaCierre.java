package Models;

public class AperturaCierre {
    private int id;
    private String fecha_apertura;
    private String fecha_cierre;
    private double monto_inicial;
    private double monto_final;
    private int total_ventas;
    private int id_usuario;
    private String nom_usuario;
    
    public AperturaCierre(){
        
    }

    public AperturaCierre(int id, String fecha_apertura, String fecha_cierre, double monto_inicial, double monto_final, int total_ventas, int id_usuario, String nom_usuario) {
        this.id = id;
        this.fecha_apertura = fecha_apertura;
        this.fecha_cierre = fecha_cierre;
        this.monto_inicial = monto_inicial;
        this.monto_final = monto_final;
        this.total_ventas = total_ventas;
        this.id_usuario = id_usuario;
        this.nom_usuario = nom_usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha_apertura() {
        return fecha_apertura;
    }

    public void setFecha_apertura(String fecha_apertura) {
        this.fecha_apertura = fecha_apertura;
    }

    public String getFecha_cierre() {
        return fecha_cierre;
    }

    public void setFecha_cierre(String fecha_cierre) {
        this.fecha_cierre = fecha_cierre;
    }

    public double getMonto_inicial() {
        return monto_inicial;
    }

    public void setMonto_inicial(double monto_inicial) {
        this.monto_inicial = monto_inicial;
    }

    public double getMonto_final() {
        return monto_final;
    }

    public void setMonto_final(double monto_final) {
        this.monto_final = monto_final;
    }

    public int getTotal_ventas() {
        return total_ventas;
    }

    public void setTotal_ventas(int total_ventas) {
        this.total_ventas = total_ventas;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNom_usuario() {
        return nom_usuario;
    }

    public void setNom_usuario(String nom_usuario) {
        this.nom_usuario = nom_usuario;
    }
}
