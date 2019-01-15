package json;

import java.util.ArrayList;
import java.util.List;

public class Members {
  private List<Details> members;

  public Members() {
    members = new ArrayList<>();
    members.add(new Details("Adam", "Brother", 22));
    members.add(new Details("Evan", "Me", 21));
    members.add(new Details("Ian", "Brother", 18));
    members.add(new Details("Michael", "Father", 51));
    members.add(new Details("Kristine", "Mother", 50));
  }
}

class Details {
  private String name;
  private String role;
  private int age;

  Details(String name, String role, int age) {
    this.name = name;
    this.role = role;
    this.age = age;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }
}
