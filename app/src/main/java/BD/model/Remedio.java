package BD.model;

/**
 * Created by Ricardo on 23-06-2015.
 */
public class Remedio {
    int id;
    String nombre;
    String comentario;


    //constructores
    public Remedio() {
    }

    public Remedio(int id, String nombre, String comentario) {
        this.id = id;
        this.nombre = nombre;
        this.comentario = comentario;

    }

    public Remedio(String nombre, String comentario) {
        this.nombre = nombre;
        this.comentario = comentario;
    }


    //getter and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
