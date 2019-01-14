package messages;

import java.util.HashMap;
import java.util.Map;

public class Message {
  private String type;
  private Map<String, String> fields = new HashMap<>();
  private String body;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Map<String, String> getFields() {
    return fields;
  }

  public void addField(String key, String value) {
    fields.put(key, value);
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }
}
