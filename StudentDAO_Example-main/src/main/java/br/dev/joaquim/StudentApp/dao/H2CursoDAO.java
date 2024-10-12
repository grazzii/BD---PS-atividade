package br.dev.joaquim.StudentApp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.dev.joaquim.StudentApp.entities.Curso;

public class H2CursoDAO implements CursoDAO {

    private Connection connection;
    private String url = "jdbc:h2:file:~/data/cursos;";
    private String user = "root";
    private String password = "root";

    public H2CursoDAO() {
        connect();
        createTableIfNotExists();
    }

    private void connect() {
        try {
            this.connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            this.connection = null;
            System.out.println("Problema ao conectar no banco de dados");
            ex.printStackTrace();
        }
    }

    private void createTableIfNotExists() {
        try {
            String sql = "CREATE TABLE IF NOT EXISTS cursos(" +
                         "id INT AUTO_INCREMENT, nome VARCHAR(256), " +
                         "PRIMARY KEY (id));";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println("Problema ao criar a tabela de cursos");
            ex.printStackTrace();
        }
    }

    @Override
    public boolean create(Curso curso) {
        try {
            String sql = "INSERT INTO cursos (nome) VALUES(?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, curso.getNome());
            stmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("Problema ao criar curso");
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Curso> findAll() {
        List<Curso> cursos = new ArrayList<>();
        try {
            String sql = "SELECT * FROM cursos";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                Curso curso = new Curso(id, nome);
                cursos.add(curso);
            }
        } catch (SQLException ex) {
            System.out.println("Problema ao buscar cursos");
            ex.printStackTrace();
        }
        return cursos;
    }

    @Override
    public Curso findById(int id) {
        try {
            String sql = "SELECT * FROM cursos WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String nome = rs.getString("nome");
                return new Curso(id, nome);
            }
        } catch (SQLException ex) {
            System.out.println("Problema ao buscar curso pelo ID");
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(Curso curso) {
        try {
            String sql = "UPDATE cursos SET nome = ? WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, curso.getNome());
            stmt.setInt(2, curso.getId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("Problema ao atualizar curso");
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        try {
            String sql = "DELETE FROM cursos WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("Problema ao apagar curso");
            ex.printStackTrace();
        }
        return false;
    }
}
