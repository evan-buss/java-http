import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {
  public static void main(String[] args) {
    ServerSocket server = null;
    Socket connection;
    try {
      server = new ServerSocket(12328);
    } catch (IOException e) {
      e.printStackTrace();
    }

    // Thread pool of 10 threads to handle requests. This is the max.
    ExecutorService executorService = Executors.newFixedThreadPool(10);

    while (true) {
      try {
        connection = server.accept();
        if (connection.isConnected()) {
          //Transfer the Request to a new Server thread via the executor service
          executorService.execute(new Server(connection));
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    // executorService.shutdown();
  }
}
