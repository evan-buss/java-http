// This class will load html pages from the Static page html resources folder

import messages.Response;

import java.io.*;
import java.util.zip.GZIPOutputStream;

public class PageLoader {
  private String content;

  PageLoader(String path, Response response) {
    boolean fileFound;

    // Redirect the default to homepage
    if (path.equals("/")) {
      path = "/home";
    }

    // Attempt to load the provided url path
    response.addField("content-encoding", "gzip");
    fileFound = getGZIPContent(path);
    if (fileFound) {
      response.setType("HTTP/1.1 200 OK");
    } else {
      // Send the 404 response if page not found
      response.setType("HTTP/1.1 404 Not Found");
      getGZIPContent("/404");
    }
    response.setBody(getContent());
  }

  /**
   * Attempts to load the file via the path given
   *
   * @return True if file loaded successfully, False if not
   */
  private boolean loadFileContent(String path) {
    StringBuilder sb = new StringBuilder();
    try (BufferedReader reader = new BufferedReader(new FileReader("html" + path + ".html"))) {
      String line;
      while ((line = reader.readLine()) != null) {
        // TODO: TEST IF WORKS WITHOUT NEWLINES
        // sb.append(line).append("\n");
        sb.append(line);
        System.out.println(line);
      }
      content = sb.toString();
      return true;
    } catch (IOException e) {
      System.out.println("Error reading html line");
      e.printStackTrace();
      return false;
    }
  }

  private boolean getGZIPContent(String path) {
    if (!loadFileContent(path)) {
      return false;
    }
    try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(bos) ) {
        gzip.write(content.getBytes());
        byte[] compressed = bos.toByteArray();
        content = new String(compressed);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return true;
  }

  /**
   * Get the loaded page content;
   *
   * @return String containing the page content
   */
  private String getContent() {
    return content;
  }

  @Override
  public String toString() {
    return "PageLoader{" + "content='" + content + '\'' + '}';
  }
}
