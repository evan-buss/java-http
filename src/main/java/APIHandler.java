import com.google.gson.Gson;
import json.Details;
import json.Members;
import messages.Response;

import java.sql.*;

public class APIHandler {
    public static String test(String path, Response response) {

        //TODO: Handle different routes and methods (CRUD)
        try (Connection conn = ConnectDB()) {
            Gson gson = new Gson();
            Members members = new Members();

            // Create statement
            Statement statement = null;

            statement = conn.createStatement();
            // Execute statement from query
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");

            while (resultSet.next()) {
                System.out.println(resultSet.toString());
                members.addMember(
                        new Details(
                                resultSet.getString("name"),
                                resultSet.getString("role"),
                                resultSet.getInt("age")));
            }

            response.setType("HTTP/1.1 200 OK");
            response.setContentType(Response.ContentType.JSON);
            return gson.toJson(members);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static Connection ConnectDB() throws SQLException {
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
}
