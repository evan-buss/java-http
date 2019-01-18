package api.json.members;

import java.util.ArrayList;
import java.util.List;

public class MembersJson {
  @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
  private final List<Details> members = new ArrayList<>();

  public MembersJson() {
  }

  public void addMember(Details details) {
    this.members.add(details);
  }
}
