package BD.model;

/**
 * Created by Ricardo on 23-06-2015.
 */
public class Farmacia {
    int id;
    String posicion;//posicion latitud longitud
    String direccion; //ejemplo av pajaritos 20190
    String nombre;

    //constructores
    public Farmacia(){
    }

    public Farmacia(String posicion, String direccion, String nombre){
        this.direccion=direccion;
        this.nombre=nombre;
        this.posicion= posicion;
    }
    public Farmacia(int id, String posicion, String direccion, String nombre){
        this.id=id;
        this.posicion=posicion;
        this.direccion=direccion;
        this.nombre=nombre;
    }

    //getter

    public int getId() {
        return id;
    }

    public String getPosicion() {
        return posicion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }


    //setter
    public void setId(int id) {
        this.id = id;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }



    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
