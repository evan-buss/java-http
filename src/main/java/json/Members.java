package json;

import java.util.ArrayList;
import java.util.List;

public class Members {
  private List<Details> members = new ArrayList<>();

  public Members() {}

  public void addMember(Details details) {
    this.members.add(details);
  }
}
