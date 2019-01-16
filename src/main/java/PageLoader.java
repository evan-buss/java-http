// This class will load html pages from the Static page html resources folder

import messages.Response;

import java.io.*;
import java.util.zip.GZIPOutputStream;

class PageLoader {
  PageLoader() {}

  /**
   * Attempts to load the file via the path given
   *
   * @return Returns the loaded content as a string
   */
  public static String load(String path, Response response) {
    if (path.equals("/")) {
      path = "/home";
    }

    StringBuilder sb = new StringBuilder();
    try (BufferedReader reader = new BufferedReader(new FileReader("html" + path + ".html"))) {
      String line;
      while ((line = reader.readLine()) != null) {
        sb.append(line);
      }
      response.setType("HTTP/1.1 200 OK");
      return sb.toString();
    } catch (FileNotFoundException e) {
      System.err.println("Could not find that file. Loading 404 file...");
      response.setType("HTTP/1.1 404 Not Found");
      return load("/404", response);
    } catch (IOException e) {
      System.out.println("Error reading html line");
      e.printStackTrace();
    }
    return "";
  }

  /**
   * Reads the file from path, compresses the data, and returns a byte array of the contents
   *
   * @param path String denoting the path of the file to load
   * @param response Response object to be modified
   * @return Returns byte array on successful compression, returns null on error
   */
  public static byte[] loadGZIP(String path, Response response) {
    try {
      //  First load the file content into a string
      String content = load(path, response);
      byte[] contentBytes = content.getBytes();
      ByteArrayOutputStream byteStream = new ByteArrayOutputStream(contentBytes.length);
      // Compress stream
      GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteStream);
      gzipOutputStream.write(contentBytes, 0, contentBytes.length);
      gzipOutputStream.close();
      response.addField("content-encoding", "gzip");
      return byteStream.toByteArray();
    } catch (IOException e) {
      System.err.println("Error GZIPPING data");
      e.printStackTrace();
    }
    return null;
  }
}
