package br.dev.joaquim.StudentApp.entities;

public class Student {
  private int ra;
  private String name;

  public Student() {
  }

  public Student(int ra, String name) {
    this.ra = ra;
    this.name = name;
  }

  public int getRa() {
    return ra;
  }

  public String getName() {
    return name;
  }

  public void setRa(int ra) {
    this.ra = ra;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Student " + getName() + " [" + getRa() + "]";
  }
}
