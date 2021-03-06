import org.sql2o.*;
import java.util.List;

public class Doctor {
  private String name;
  private String specialty;
  private int id;

  public Doctor (String name, String specialty){
  this.name = name;
  this.specialty = specialty;
  }

  public String getName(){
    return name;
  }
  public String getSpecialty(){
    return specialty;
  }
  public int getId(){
    return id;
  }

  public void save(){
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO doctors (name, specialty) VALUES (:name, :specialty)";
      this.id = (int) con.createQuery(sql, true).addParameter("name", this.name).addParameter("specialty", this.specialty).executeUpdate().getKey();
    }
  }
  public static List<Doctor> all(){
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT id, name, specialty FROM doctors";
      return con.createQuery(sql).executeAndFetch(Doctor.class);
    }
  }
  @Override
  public boolean equals(Object otherDoctor) {
    if (!(otherDoctor instanceof Doctor)) {
      return false;
    } else {
      Doctor newDoctor = (Doctor) otherDoctor;
      return this.getName().equals(newDoctor.getName()) &&
      this.getSpecialty().equals(newDoctor.getSpecialty()) && this.getId() == newDoctor.getId();
    }
  }

  public static Doctor find(int id){
      try(Connection con = DB.sql2o.open()) {
        String sql = "SELECT * FROM doctors WHERE id = :id";
        Doctor doctor =  con.createQuery(sql).addParameter("id", id).executeAndFetchFirst(Doctor.class);
        return doctor;
      }
  }


















}
