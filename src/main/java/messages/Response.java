package messages;

import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

public class Response extends Message{

  public Response() {
    this.addField("date", new Date().toString());
    this.addField("content-Type", "application/json");
    this.addField("server", "Buss-Soft HTTP");
    this.addField("connection", "close");
  }

  public void setLength(String length) {
    this.addField("content-length", length);
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
    sb.append(this.getType()+"\n");
    for (Map.Entry<String, String> entry : this.getFields().entrySet()) {
      sb.append(entry.getKey() + ": " + entry.getValue() + "\n");
    }
    sb.append("\n");
    sb.append(this.getBody());
    return sb.toString();
  }

}
