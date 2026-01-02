package model;

public class Repartidor {

    private Integer id;
    private String nombre;
    private String email;
    private String telefono;

    public Repartidor(){}
    public Repartidor(Integer id, String name, String email,String phoneNumber){
        this.id= id;
        this.nombre = name;
        this.email= email;
        this.telefono = phoneNumber;
    }

    public Integer getId(){
        return this.id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Repartidor{" +
                "id=" + id +
                ", name='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + telefono + '\'' +
                '}';
    }
}
