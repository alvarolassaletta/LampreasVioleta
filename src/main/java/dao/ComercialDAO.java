package dao;

import db.Db;
import model.Comercial;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComercialDAO {

    //insertar  un comercial
    // Es conveniente usar text blocks """ """ en vez de concatenacion para evitar errores de sintaxis
    // con los espacios
    private static final String  INSERT_SQL= """
            INSERT INTO comercial (id,nombre,email,telefono) 
            VALUES(?,?,?,?)""";

    //listar los comerciales
    private static final String  SELECT_ALL= """
           SELECT id,nombre,email,telefono
           FROM comercial
           ORDER BY id
           """;


    //listar los comerciales  por id
    private static final String SELECT_BY_ID= """
            SELECT id,nombre,email,telefono
            FROM  comercial
            WHERE id = ?;
            """;

    //Actualizar campos en un registro de comercial
    private static final String UPDATE_SQL= """
            UPDATE comercial 
            SET nombre=?, email=?,telefono=? 
            WHERE id= ?""";


    //Eliminar un registro de comercial
    private static final String DELETE_SQL= """ 
            DELETE FROM comercial
            WHERE id=?""";

    //Búsqueda de comerciales en función de un filtro específico
    private static final String SEARCH_SQL ="""
               SELECT id,nombre,email,telefono
               FROM comercial 
               WHERE CAST(id AS TEXT) ILIKE ?
                    OR nombre ILIKE ?
                    OR email ILIKE ?
                    OR telefono ILIKE ?
               ORDER BY id
               """;

    //Metodo para insertar un  comercial
    public void insert (Comercial comercial) throws SQLException {
        try(Connection con = Db.getConnection();
            PreparedStatement ps= con.prepareStatement(INSERT_SQL)){

            ps.setInt(1,comercial.getId());
            ps.setString(2,comercial.getNombre());
            ps.setString(3,comercial.getEmail());
            ps.setString(4,comercial.getTelefono());

            ps.executeUpdate();

        }
    }

    //Método para listar los comerciales
    //Devuelve una lista con objetos de la clase  comercial
    public List<Comercial> findAll() throws SQLException{
        List<Comercial> out = new ArrayList<>();
        try(Connection con = Db.getConnection();
            PreparedStatement ps = con.prepareStatement(SELECT_ALL);
            ResultSet rs = ps.executeQuery()){

            while(rs.next()){
                out.add(mapRow(rs));
            }
            return out;
        }
    }

    //Método para buscar comerciales según un filtro específico
    public List <Comercial> search (String filtro)throws SQLException{

        String pattern = "%" + filtro +"%";

        try(Connection con= Db.getConnection();
            PreparedStatement ps = con.prepareStatement(SEARCH_SQL)){
            ps.setString(1,pattern);
            ps.setString(2,pattern);
            ps.setString(3,pattern);
            ps.setString(4,pattern);

            List <Comercial> out = new ArrayList<>();

            try(ResultSet rs= ps.executeQuery()){
                while(rs.next()){
                    out.add(mapRow(rs));
                }
            }
            return out;
        }

    }

    //Método para buscar un comercial por su id
    public Comercial findById(Integer id) throws   SQLException{

        try(Connection con = Db.getConnection();
            PreparedStatement ps = con.prepareStatement(SELECT_BY_ID)){

            ps.setInt(1,id);

            try(ResultSet rs=  ps.executeQuery()){

                if(rs.next()){
                    return mapRow(rs);
                }
                return null;
            }
        }
    }

    //Método para actualizar el comercial
    //Devuelve el número de filas afectadas
    public int update(Comercial comercial)throws SQLException{
        try(Connection con = Db.getConnection();
            PreparedStatement ps = con.prepareStatement(UPDATE_SQL)){

            ps.setString(1,comercial.getNombre());
            ps.setString(2,comercial.getEmail());
            ps.setString(3,comercial.getTelefono());
            ps.setInt(4,comercial.getId());

            return ps.executeUpdate();
        }
    }

    //Método para eliminar un comercial
    //Devuelve el número de filas afectadas
    public int delete(int id) throws  SQLException{
        try(Connection con = Db.getConnection();
            PreparedStatement ps = con.prepareStatement(DELETE_SQL)){

            ps.setInt(1,id);
            return ps.executeUpdate();

        }
    }

    //Método para crear un objeto Comercial
    private Comercial mapRow(ResultSet rs) throws SQLException{
        Comercial comercial = new Comercial (
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("email"),
                rs.getString("telefono")
        );
        return comercial;
    }
}
