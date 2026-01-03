package dao;

import db.Db;
import model.Repartidor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepartidorDAO {

    // insertar un repartidor
    private static final String INSERT_SQL = """
            INSERT INTO repartidor (id, nombre, email, telefono)
            VALUES (?, ?, ?, ?)
            """;

    // listar todos los repartidores
    private static final String SELECT_ALL = """
            SELECT id, nombre, email, telefono
            FROM repartidor
            ORDER BY id
            """;

    // buscar repartidor por id
    private static final String SELECT_BY_ID = """
            SELECT id, nombre, email, telefono
            FROM repartidor
            WHERE id = ?
            """;

    // update un repartidor
    private static final String UPDATE_SQL= """
            UPDATE repartidor\s
            SET nombre=?, email=?,telefono=?
            WHERE id= ?
            """;

    private static final String DELETE_SQL="""
            DELETE FROM repartidor WHERE id=?""";

    private static final String SEARCH_SQL ="""
           SELECT id,nombre,email,telefono
           FROM repartidor 
           WHERE CAST(id AS TEXT) ILIKE ?
                OR nombre ILIKE ?
                OR email ILIKE ?
                OR telefono ILIKE ?
           ORDER BY id
           """;

    //Metodo para insertar un  repartidor
    public void insert (Repartidor repartidor) throws SQLException {
        try(Connection con = Db.getConnection();
            PreparedStatement ps= con.prepareStatement(INSERT_SQL)){

            ps.setInt(1,repartidor.getId());
            ps.setString(2,repartidor.getNombre());
            ps.setString(3,repartidor.getEmail());
            ps.setString(4,repartidor.getTelefono());

            ps.executeUpdate();

        }
    }

    //Método para listar los repartidores
    //Devuelve una lista con objetos repartidor

    public List<Repartidor> findAll() throws SQLException{
        List<Repartidor> out = new ArrayList<>();
        try(Connection con = Db.getConnection();
            PreparedStatement ps = con.prepareStatement(SELECT_ALL);
            ResultSet rs = ps.executeQuery()){

            while(rs.next()){
                out.add(mapRow(rs));
            }
            return out;
        }
    }

    //Método para buscar repartidores según un filtro específico
    public List <Repartidor> search (String filtro)throws SQLException{

        String pattern = "%" + filtro +"%";

        try(Connection con= Db.getConnection();
            PreparedStatement ps = con.prepareStatement(SEARCH_SQL)){
            ps.setString(1,pattern);
            ps.setString(2,pattern);
            ps.setString(3,pattern);
            ps.setString(4,pattern);

            List <Repartidor> out = new ArrayList<>();

            try(ResultSet rs= ps.executeQuery()){
                while(rs.next()){
                    out.add(mapRow(rs));
                }
            }
            return out;
        }

    }

    //Método para buscar un repartidor por su id
    public Repartidor findById(Integer id) throws   SQLException{

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

    //método para actualizar el repartidor
    //devuelve el número de filas afectadas
    public int update(Repartidor repartidor)throws SQLException{
        try(Connection con = Db.getConnection();
            PreparedStatement ps = con.prepareStatement(UPDATE_SQL)){

            ps.setString(1,repartidor.getNombre());
            ps.setString(2,repartidor.getEmail());
            ps.setString(3,repartidor.getTelefono());
            ps.setInt(4,repartidor.getId());

            return ps.executeUpdate();
        }
    }

    //Método para eliminar un repartidor
    //Devuelve el número de filas afectadas
    public int delete(int id) throws  SQLException{
        try(Connection con = Db.getConnection();
            PreparedStatement ps = con.prepareStatement(DELETE_SQL)){

            ps.setInt(1,id);
            return ps.executeUpdate();

        }
    }

    //Método para crear un objeto Repartidor
    private Repartidor mapRow(ResultSet rs) throws SQLException{
        Repartidor repartidor = new Repartidor (
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("email"),
                rs.getString("telefono")
        );
        return repartidor;
    }
}
