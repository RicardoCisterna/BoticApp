package BD.model;

/**
 * Created by Ricardo on 23-06-2015.
 */
public class Comentario {
    int id;
    String comentario;
    String fechaHora;
    int precio;


    //Costructores


    public Comentario() {
    }

    public Comentario(int id, String comentario, String fechaHora) {
        this.id = id;
        this.comentario = comentario;
        this.fechaHora = fechaHora;
    }

    public Comentario(String comentario, String fechaHora) {
        this.comentario = comentario;
        this.fechaHora = fechaHora;
    }

    public Comentario(int id, int precio, String comentario, String fechaHora) {
        this.id = id;
        this.precio = precio;
        this.comentario = comentario;
        this.fechaHora = fechaHora;
    }

    public Comentario(String comentario, String fechaHora, int precio) {
        this.comentario = comentario;
        this.fechaHora = fechaHora;
        this.precio = precio;
    }
    //getter and Setter


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }
}
