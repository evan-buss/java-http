package request_methods;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Method {
  String data;

  // All classes will have access to the DB via this connection
  Connection ConnectDB() throws SQLException {
    String dbURL = "jdbc:postgresql://localhost:5432/evan";
    String user = "evan";
    String password = "2842";
    try {
      Class.forName("org.postgresql.Driver");
      return DriverManager.getConnection(dbURL, user, password);
    } catch (Exception ex) {
      ex.printStackTrace();
      throw new SQLException();
    }
  }

  // Every method has to return a response.
  public String getResponse() {
    return "DEFAULT";
  }
}
