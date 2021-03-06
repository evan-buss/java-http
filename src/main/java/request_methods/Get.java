package request_methods;

import api.Members;
import messages.Request;
import messages.Response;

import java.sql.Connection;
import java.sql.SQLException;

public class Get extends Method {

  /**
   * Get reads the API request URL and calls the appropriate API handler class
   *
   * @param request  The request object containing the request details
   * @param response The response object being built
   */
  public Get(Request request, Response response) {
    try (Connection conn = ConnectDB()) {
      switch (request.getPath().substring(4)) {
        case "/members":
          data = Members.get(request, response, conn);
          break;
        default:
          data = "";
          response.setResponse(Response.ResponseCode.ERROR);
      }
    } catch (SQLException ex) {
      System.err.println("GET: Error connecting to DB");
      ex.printStackTrace();
    }
  }
}
