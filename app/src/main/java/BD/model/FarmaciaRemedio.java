package BD.model;

public class FarmaciaRemedio {
    int id;
    long farmacia;
    long remedio;
    int precio;

    public FarmaciaRemedio() {
    }

    public FarmaciaRemedio(long farmacia, long remedio) {
        this.farmacia = farmacia;
        this.remedio = remedio;
    }

    public FarmaciaRemedio(int id, long farmacia, long remedio, int precio) {
        this.id = id;
        this.farmacia = farmacia;
        this.remedio = remedio;
        this.precio = precio;
    }

    public FarmaciaRemedio(long farmacia, long remedio, int precio) {
        this.farmacia = farmacia;
        this.remedio = remedio;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getFarmacia() {
        return farmacia;
    }

    public void setFarmacia(long farmacia) {
        this.farmacia = farmacia;
    }

    public long getRemedio() {
        return remedio;
    }

    public void setRemedio(long remedio) {
        this.remedio = remedio;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }
}