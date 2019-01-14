import com.google.gson.Gson;
import json.Name;
import messages.Request;
import messages.Response;

import java.io.*;
import java.net.Socket;

public class Server implements Runnable {

  private Socket connection;
  private Request request;
  private Response response;

  public Server(Socket connection) {
    this.connection = connection;
  }

  @Override
  public void run() {
    parseRequest();
    sendResponse();
  }

  private void parseRequest() {
    StringBuilder sb = new StringBuilder();
    BufferedReader br = null;
    request = new Request();
    try {
      br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
    } catch (IOException e) {
      System.err.println("Error creating stream reader: ");
      e.printStackTrace();
    }

    try {
      String line;
      line = br.readLine();
      request.setType(line);
      while (!(line = br.readLine()).equals("")) {

        //  Loop until the end of the header
        String[] data = line.split(": ");
        request.addField(data[0], data[1]);
      }
      // If there is a body... parse that too.
      if (request.getFields().containsKey("Content-Length")) {
        int remaining = Integer.parseInt(request.getFields().get("Content-Length"));
        while (remaining > 0) {
          char[] buf = new char[100];
          remaining -= br.read(buf);
          sb.append(buf);
        }
        request.setBody(sb.toString());
      }
    } catch (IOException e) {
      System.err.println("Error parsing data.");
      e.printStackTrace();
    }

    System.out.println("Request Received:\n");
    System.out.println(request.toString());
  }

  private void sendResponse() {
    String query = request.getURI().substring(1);

    Name name = new Name(query);

    Gson gson = new Gson();
    String json = gson.toJson(name);

    response = new Response();
    response.setLength(Integer.toString(json.length()));
    response.setBody(json);
    response.setType("HTTP/1.1 200 OK");

    try {
      // Write the response to the tcp socket, close the connection
      PrintWriter pw = new PrintWriter(connection.getOutputStream(), true);
      response.print(pw);
      connection.close();
    } catch (Exception e) {
      System.err.println("Output Writer:");
      e.printStackTrace();
    }

    System.out.println("\n\nResponse Sent:\n");
    System.out.println(response.toString());
  }
}
