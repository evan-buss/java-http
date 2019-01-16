import com.google.gson.Gson;
import json.Members;
import messages.Request;
import messages.Response;

import java.io.*;
import java.net.Socket;
import java.util.zip.GZIPOutputStream;

public class Server implements Runnable {

  private Socket connection;
  private Request request;
  private Response response;
  private boolean isApiRequest;

  Server(Socket connection) {
    this.connection = connection;
  }

  /**
   * Default runnable method.
   *
   * <p>1) Receives a request
   *
   * <p>2) Processes the request data; forming a proper response
   *
   * <p>3) Sends the response and closes the connection
   */
  @Override
  public void run() {
    parseRequest();
    process();
    sendResponse();
  }

  /** Read the request from the TCP socket. Form a request object from the data */
  private void parseRequest() {
    StringBuilder sb = new StringBuilder();
    String line;
    request = new Request();

    try {
      // Buffered reader to read the request line bye line
      BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

      // The first line of the request denotes the request details
      // EX) GET /home HTTP/1.1
      request.setType(br.readLine());

      //  Loop until blank line; end of request header
      while (!(line = br.readLine()).equals("")) {
        // Split each line into it's key and value and save it in the request
        String[] data = line.split(": ");
        request.addField(data[0], data[1]);
      }

      // Check if the header contained a "content-length" attribute denoting
      // the presence of a request body
      // TODO: configure body processing when API is more fully implemented.
      if (request.getFields().containsKey("Content-Length")) {
        int remaining = Integer.parseInt(request.getFields().get("Content-Length"));
        char[] buf = new char[100];
        // Loop until all body data is read
        while (remaining > 0) {
          remaining -= br.read(buf);
          sb.append(buf);
        }

        // Set the body of the request to the received info string.
        request.setBody(sb.toString());
      }
    } catch (IOException e) {
      System.err.println("Error parsing data.");
      e.printStackTrace();
    }

    System.out.println(
        Thread.currentThread().getName() + " - Request " + "Received:\n\n" + request.toString());
  }

  /** Process evaluates the request and builds proper response */
  private void process() {
    response = new Response();

    Gson gson = new Gson();
    // Check if the request is an API call
    if (request.getPath().length() > 1 && request.getPath().substring(0, 4).equals("/api")) {
      // FIXME: Create an api class?
      isApiRequest = true;
      Members members = new Members();
      response.setType("HTTP/1.1 200 OK");
      String out = gson.toJson(members);
      System.out.println(out);
      response.setBody(out);
    } else { // Otherwise the request requires a file to be loaded
      isApiRequest = false;
      response.setContentType(Response.ContentType.HTML);
      byte[] body = PageLoader.loadGZIP(request.getPath(), response);
      if (body != null) {
        response.setByteBody(body);
      }
      String bodyStr = PageLoader.load(request.getPath(), response);
      System.out.println("UnComp: " + bodyStr.length() + " Comp: " + body.length);
    }
  }

  /**
   * sendResponse is responsible for sending the response's header and optional body. It closes the
   * tcp connection when finished.
   */
  private void sendResponse() {
    // PrintWriter bodyWriter =
    //             new PrintWriter(new GZIPOutputStream(connection.getOutputStream()), true)
    try (PrintWriter charOut = new PrintWriter(connection.getOutputStream(), true);
        DataOutputStream compressedOut = new DataOutputStream(connection.getOutputStream())) {
      // Write the response to the tcp socket, close the connection

      response.sendHeader(charOut);
      if (isApiRequest) {
        response.sendBody(charOut);
      } else {
        response.sendCompressedBody(compressedOut);
      }

    } catch (Exception e) {
      System.err.println("Output Writer:");
      e.printStackTrace();
    }
    // System.out.println("Response Sent!");
    System.out.println("\n\nResponse Sent:\n" + response.toString());
  }
}
