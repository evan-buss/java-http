package messages;

import java.util.HashMap;
import java.util.Map;

public class Request extends Message {

  public Request() {
  }

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
  public String getURI() {
    return getType().split(" ")[1];
  }

  // Returns the requested HTTP method
  public String getMethod() {
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
   * @return Map<String       ,       String> of the queries. Empty if no queries parsed...
   */

  // TODO: Queries use "&" syntax for chained????
  public Map<String, String> getQueries() {
    Map<String, String> queries = new HashMap<>();
    String rawQ = getURI().substring(getPath().length());
    // Check if the url has a query, if not return the empty list...
    if (rawQ.contains("?")) {
      String[] splitQ = rawQ.substring(1)
          .replace("&", " ")
          .trim()
          .split(" ");

      for (String q : splitQ) {
        String[] parts = q.split("=");
        parts[1] = parts[1].replace("%20", " ");
        queries.put(parts[0], parts[1]);
      }
    }
    return queries;
  }

  // Print the fields formatted line by line
  private String printFields() {
    StringBuilder sb = new StringBuilder();
    for (Map.Entry<String, String> entry : this.getFields().entrySet()) {
      sb.append("\t").append(entry.getKey()).append(" ").append(entry.getValue()).append("\n");
    }
    return sb.toString();
  }
}
