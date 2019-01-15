package messages;

import java.util.HashMap;
import java.util.Map;

public class Request extends Message {

  public Request() {}

  @Override
  public String toString() {
    return "Request:\n"
        + "*****************\n"
        + "Type:\n\t"
        + getType()
        + "\nFields:\n"
        + printFields()
        + "Body:\n\t"
        + getBody();
  }

  // Returns full address
  private String getURI() {
    return getType().split(" ")[1];
  }

  // Returns the requested HTTP method
  String getMethod() {
    return getType().split(" ")[0];
  }

  // Returns the parsed path
  // EX) /members?name="Evan" -> /members
  public String getPath() {
    String uri = getURI();
    if (uri.contains("?")) {
      return uri.substring(0, uri.indexOf("?"));
    }
    return uri;
  }

  /**
   * getQueries parses the URI string for query key-value pairs and returns them as a Map
   *
   * @return Map<String,String> of the queries or NULL if there are no queries
   */

  // TODO: Queries use "&" syntax for chained????
  public Map<String, String> getQueries() {
    Map<String, String> queries = new HashMap<>();
    String rawQ = getURI().substring(getPath().length());
    // Check if the url has a query, if not return the empty list...
    if (rawQ.length() > 0) {
      String[] splitQ = rawQ.replace("?", " ").trim().split(" ");
      for (String q : splitQ) {
        String[] parts = q.split("=");
        queries.put(parts[0], parts[1]);
      }
    }
    return queries;
  }

  // Print the fields formatted line by line
  private String printFields() {
    StringBuilder sb = new StringBuilder();
    for (Map.Entry<String, String> entry : this.getFields().entrySet()) {
      // sb.append("\t" + entry.getKey() + ":  " + entry.getValue() + "\n");
      sb.append("\t").append(entry.getKey()).append(entry.getValue()).append("\n");
    }
    return sb.toString();
  }
}
