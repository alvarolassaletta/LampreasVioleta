package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase para contener los datos de los  repartidores.
 * Se utilizará para guardar los datos existentes en la base de datos de lso repartidores
 * La lista el getter/ setter se utilizará para la serializacion y deserialización
 * Se crea  una clase  específica para obtener solo lso datos de lso repartidores y no tener
 * que usar AppData que engloba todas las tablas
 *
 * */
public class RepartidoresData {
    private List<Repartidor> repartidores = new ArrayList<>();

    public List <Repartidor> getRepartidores(){
        return this.repartidores;
    }
    public void setRepartidores(List<Repartidor> repartidores){
        this.repartidores = repartidores;
    }
}
