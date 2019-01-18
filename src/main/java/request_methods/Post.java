package request_methods;

import api.Members;
import messages.Request;
import messages.Response;

import java.sql.Connection;
import java.sql.SQLException;

public class Post extends Method{


  public Post(Request request, Response response) {
    try (Connection conn = ConnectDB()) {
      switch (request.getPath().substring(4)) {
        case "/members":
          data = Members.post(request, response, conn);
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
