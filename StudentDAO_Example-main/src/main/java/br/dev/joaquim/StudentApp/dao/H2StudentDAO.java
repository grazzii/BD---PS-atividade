package br.dev.joaquim.StudentApp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.dev.joaquim.StudentApp.entities.Student;

public class H2StudentDAO implements StudentDAO {

  private Connection connection;
  private String url = "jdbc:h2:file:~/data/students;";
  private String user = "root";
  private String password = "root";

  public H2StudentDAO() {
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
      String sql = "CREATE TABLE IF NOT EXISTS students(" +
          "ra INT, name VARCHAR(256), PRIMARY KEY (ra));";
      PreparedStatement stmt = connection.prepareStatement(sql);
      stmt.execute();
    } catch (SQLException ex) {
      System.out.println("Problema ao criar a tabela");
      ex.printStackTrace();
    } catch (NullPointerException ex) {
      System.out.println("Problema ao criar a tabela (sem conexao)");
    }
  }

  @Override
  public boolean create(Student student) {
    try {
      String sql = "INSERT INTO students VALUES(?,?)";
      PreparedStatement stmt = connection.prepareStatement(sql);
      stmt.setInt(1, student.getRa());
      stmt.setString(2, student.getName());
      stmt.executeUpdate();

      return true;

    } catch (SQLException ex) {
      System.out.println("Problema ao criar estudante");
      ex.printStackTrace();
    } catch (NullPointerException ex) {
      System.out.println("Problema ao criar estudante (sem conexao)");
    }

    return false;
  }

  @Override
  public List<Student> findAll() {
    try {
      String sql = "SELECT * FROM students";
      PreparedStatement stmt = connection.prepareStatement(sql);
      ResultSet rs = stmt.executeQuery();

      List<Student> students = new ArrayList<>();

      while (rs.next()) {
        int ra = rs.getInt("ra");
        String name = rs.getString("name");
        Student student = new Student(ra, name);
        students.add(student);
      }

      return students;

    } catch (SQLException ex) {
      System.out.println("Problema ao buscar estudantes");
      ex.printStackTrace();
    } catch (NullPointerException ex) {
      System.out.println("Problema ao buscar estudantes (sem conexao)");
    }

    return new ArrayList<>();
  }

  @Override
  public Student findByRa(int ra) {
    try {
      String sql = "SELECT * FROM students WHERE ra = ?";
      PreparedStatement stmt = connection.prepareStatement(sql);
      stmt.setInt(1, ra);
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        String name = rs.getString("name");
        return new Student(ra, name);
      }
    } catch (SQLException ex) {
      System.out.println("Problema ao buscar estudante pelo RA");
      ex.printStackTrace();
    } catch (NullPointerException ex) {
      System.out.println("Problema ao buscar estudante pelo RA (sem conexao)");
    }

    return null;
  }

  @Override
  public boolean update(Student student) {
    try {
      String sql = "UPDATE students SET name = ? WHERE ra = ?";
      PreparedStatement stmt = connection.prepareStatement(sql);
      stmt.setString(1, student.getName());
      stmt.setInt(2, student.getRa());
      stmt.executeUpdate();

      return true;

    } catch (SQLException ex) {
      System.out.println("Problema ao atualizar estudante");
      ex.printStackTrace();
    } catch (NullPointerException ex) {
      System.out.println("Problema ao atualizar estudante (sem conexao)");
    }

    return false;
  }

  @Override
  public boolean delete(int ra) {
    try {
      String sql = "DELETE FROM students WHERE ra = ?";
      PreparedStatement stmt = connection.prepareStatement(sql);
      stmt.setInt(1, ra);
      stmt.executeUpdate();

      return true;

    } catch (SQLException ex) {
      System.out.println("Problema ao apagar estudante");
      ex.printStackTrace();
    } catch (NullPointerException ex) {
      System.out.println("Problema ao apagar estudante (sem conexao)");
    }

    return false;
  }
}
