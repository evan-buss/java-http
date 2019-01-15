package messages;

import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

public class Response extends Message{
  public enum ContentType {
    JSON, HTML
  }

  public Response() {
    this.addField("date", new Date().toString());
    setContentType(ContentType.JSON); // Default content-type is json
    this.addField("server", "Buss-Soft HTTP");
    this.addField("connection", "close");
  }

  public void setContentType(ContentType content) {
    switch (content) {
      case JSON:
        this.addField("content-type", "application/json");
        break;
      case HTML:
        this.addField("content-type", "text/html");
        break;
    }
  }


  public void print(PrintWriter pw) {
    pw.println(this.getType());
    for (Map.Entry<String, String> entry : this.getFields().entrySet()) {
      pw.println(entry.getKey() + ": " + entry.getValue());
    }
    pw.println("");
    pw.println(this.getBody());
  }

  @Override
  public String toString() {
  //  Loop through the fields object
    StringBuilder sb = new StringBuilder();
    sb.append(this.getType()).append("\n");
    for (Map.Entry<String, String> entry : this.getFields().entrySet()) {
      sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
    }
    sb.append("\n");
    sb.append(this.getBody());
    return sb.toString();
  }

}
