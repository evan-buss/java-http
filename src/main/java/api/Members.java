/*
 * This class handles the loading of member data.
 */

package api;

import api.json.members.Details;
import api.json.members.MembersJson;
import com.google.gson.Gson;
import messages.Request;
import messages.Response;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class Members {

  public Members() {
  }

  private static ResultSet query(String query, Connection conn) {
    // Connect to the database
    if (conn != null) {
      try {
        Statement statement = conn.createStatement();
        // Execute query
        return statement.executeQuery(query);
      } catch (SQLException e) {
        System.err.println("Error creating statement.");
        e.printStackTrace();
      }
    }
    return null;
  }

  public static String get(Request req, Response res, Connection conn) {
    res.setType("HTTP/1.1 404 Not Found"); // The default type is not found
    Gson gson = new Gson();
    String qString;
    ResultSet rs = null;
    MembersJson membersJSON = new MembersJson();

    if (req.getQueries().size() == 0) {
      System.out.println("Queries are null!");
      rs = query("SELECT * FROM users", conn);
    } else {
      Map<String, String> queryMap = req.getQueries();

      if (queryMap.containsKey("age") && queryMap.containsKey("name")) {
        qString =
            "SELECT * FROM users WHERE age = '" + queryMap.get("age") + "' " +
                "AND name = '" + queryMap.get("name") + "'";
        rs = query(qString, conn);
      } else if (queryMap.containsKey("name")) {
        qString = "SELECT * FROM users WHERE name = '" + queryMap.get("name") +
            "'";
        rs = query(qString, conn);
      } else if (queryMap.containsKey("age")) {
        qString =
            "SELECT * FROM users WHERE age= '" + queryMap.get("age") + "'";
        rs = query(qString, conn);
      }
    }

    if (rs == null) {
      System.out.println("ResultSet is null");
      return "";
    }

    try {
      boolean dbResultsFound = false;
      while (rs.next()) {
        dbResultsFound = true;
        membersJSON.addMember(
            new Details(
                rs.getString("name"),
                rs.getString("role"),
                rs.getInt("age")));
      }
      if (dbResultsFound) {
        res.setType("HTTP/1.1 200 OK");
        return gson.toJson(membersJSON);
      } else {
        res.setContentType(Response.ContentType.TEXT);
        return "No content matching query found.";
      }
    } catch (SQLException ex) {
      System.err.println("Error reading query results");
      ex.printStackTrace();
    }
    return "";
  }
}
