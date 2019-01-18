/*
 * This class handles the loading of member data.
 */

package api;

import api.json.members.Details;
import api.json.members.MembersJson;
import com.google.gson.Gson;
import messages.Request;
import messages.Response;

import java.sql.*;
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

  public static String post(Request req, Response res, Connection conn) {
    res.setResponse(Response.ResponseCode.ERROR); // The default type is not found

    //Insert the request body object into the database
    String query = "INSERT INTO users (name, age, role, created_on) "
        + "VALUES ( ?, ?, ?, current_timestamp) ON CONFLICT (name) DO NOTHING";
    Details detail = req.getBodyDetailsObject();
    try (PreparedStatement statement = conn.prepareStatement(query)) {
      statement.setString(1, detail.getName());
      statement.setInt(2, detail.getAge());
      statement.setString(3, detail.getRole());

      statement.executeUpdate();
      res.setContentType(Response.ContentType.TEXT);
      res.setResponse(Response.ResponseCode.SUCCESS);
      return "Inserted new member successfully.";
    } catch (SQLException e) {
      System.err.println("Error creating ");
      e.printStackTrace();
    }
    // Return success or error
    return "";
  }

  public static String get(Request req, Response res, Connection conn) {
    res.setResponse(Response.ResponseCode.ERROR); // The default type is not found
    Gson gson = new Gson();
    String qString = "";
    ResultSet rs = null;
    MembersJson membersJSON = new MembersJson();


    // Determine what to do based on the queries
    if (req.getQueries().size() == 0) {
      System.out.println("Queries are null!");
      qString = "SELECT * FROM users";
      rs = query(qString, conn);
    } else {
      Map<String, String> queryMap = req.getQueries();
      if (queryMap.containsKey("age") && queryMap.containsKey("name")) {
        qString =
            "SELECT * FROM users WHERE age = '" + queryMap.get("age") + "' " +
                "AND name = '" + queryMap.get("name") + "'";
      } else if (queryMap.containsKey("name")) {
        qString = "SELECT * FROM users WHERE name = '" + queryMap.get("name") +
            "'";
      } else if (queryMap.containsKey("age")) {
        qString =
            "SELECT * FROM users WHERE age= '" + queryMap.get("age") + "'";
      } else if (queryMap.containsKey("role")) {
        qString =
            "SELECT * FROM users WHERE role = '" + queryMap.get("role") + "'";
      }
      if (!qString.equals("")) {
        rs = query(qString, conn);
      }
    }

    // If ResultSet was not set, return error.
    if (rs == null) {
      System.out.println("ResultSet is null");
      return "";
    }

    try {
      // Check if the loop runs, if not the query was successful, but didn't
      // return any results.
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
        res.setResponse(Response.ResponseCode.SUCCESS);
        return gson.toJson(membersJSON);
      } else {
        res.setContentType(Response.ContentType.TEXT);
        res.setResponse(Response.ResponseCode.SUCCESS);
        return "No content matching query found.";
      }
    } catch (SQLException ex) {
      System.err.println("Error reading query results");
      ex.printStackTrace();
    }
    res.setResponse(Response.ResponseCode.ERROR);
    return ""; // Default error. Shouldn't get called.
  }
}
