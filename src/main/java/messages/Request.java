package messages;

import java.util.Map;

public class Request extends Message {

  public Request() {}

  @Override
  public String toString() {
    return "Request:\n"
        + "*****************\n"
        +"Type:\n\t"
        + getType()
        + "\nFields:\n"
        + printFields()
        + "Body:\n\t"
        + getBody();
  }

  public String getURI() {
    return getType().split(" ")[1];
  }


  // Print the fields formatted line by line
  private String printFields() {
    StringBuilder sb = new StringBuilder();
    for (Map.Entry<String, String> entry : this.getFields().entrySet()) {
      sb.append("\t" + entry.getKey() + ":  " + entry.getValue() + "\n");
    }
    return sb.toString();
  }
}
