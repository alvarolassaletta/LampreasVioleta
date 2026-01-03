package model;

public class Comercial {

    private Integer id;
    private String nombre;
    private String email;
    private String telefono;

    public Comercial() {
    }

    public Comercial(Integer id, String name, String email, String telefono) {
        this.id = id;
        this.nombre = name;
        this.email = email;
        this.telefono= telefono;
    }


    public Integer getId() {
        return this.id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getNombre() {
        return this.nombre;
    }
    public void setName() {
        this.nombre = nombre;
    }

    public String getEmail() {
        return this.email;
    }
    public void setEmail() {
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
        return "Comercial{" +
                "id=" + id +
                ", name='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}
