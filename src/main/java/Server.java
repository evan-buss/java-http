import com.google.gson.Gson;
import json.Members;
import messages.Request;
import messages.Response;

import java.io.*;
import java.net.Socket;

public class Server implements Runnable {

  private Socket connection;
  private Request request;
  private Response response;

  Server(Socket connection) {
    this.connection = connection;
  }

  @Override
  public void run() {
    parseRequest();
    process();
    sendResponse();
  }

  private void parseRequest() {
    StringBuilder sb = new StringBuilder();
    String line;
    request = new Request();

    try {
      BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

      request.setType(br.readLine());
      //  Loop until the end of the header
      while (!(line = br.readLine()).equals("")) {
        String[] data = line.split(": ");
        // Add each field as a key value pair to the request object
        request.addField(data[0], data[1]);
      }
      // Request has a body, parse that too.
      if (request.getFields().containsKey("Content-Length")) {
        int remaining = Integer.parseInt(request.getFields().get("Content-Length"));
        char[] buf = new char[100];
        while (remaining > 0) {
          remaining -= br.read(buf);
          sb.append(buf);
        }
        // Set the request object body to the received data
        request.setBody(sb.toString());
      }
    } catch (IOException e) {
      System.err.println("Error parsing data.");
      e.printStackTrace();
    }

    System.out.println(
        Thread.currentThread().getName() + " - Request " + "Received:\n\n" + request.toString());
  }

  // Build the response object based depending on the request data
  private void process() {
    response = new Response();

    Gson gson = new Gson();
    // Do different shit depending on the path.
    // May create a router class
    if (request.getPath().length() > 1 && request.getPath().substring(0, 4).equals("/api")) {
      // FIXME: Create an api class?
      Members members = new Members();
      response.setType("HTTP/1.1 200 OK");
      String out = gson.toJson(members);
      System.out.println(out);
      response.setBody(out);
    } else {
      System.out.println("ELSE: " + request.getPath());
      // If not an API call, attempt to load the static file...
      response.setContentType(Response.ContentType.HTML);
      PageLoader loader = new PageLoader(request.getPath(), response);
    }
  }

  // Send the response object and close the connection
  private void sendResponse() {
    try (PrintWriter pw = new PrintWriter(connection.getOutputStream(), true)) {
      // Write the response to the tcp socket, close the connection
      response.print(pw);
    } catch (Exception e) {
      System.err.println("Output Writer:");
      e.printStackTrace();
    }
    System.out.println("\n\nResponse Sent:\n" + response.toString());
  }
}
