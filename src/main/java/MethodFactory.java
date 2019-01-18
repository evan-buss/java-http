import messages.Request;
import messages.Response;
import request_methods.*;

class MethodFactory {

  static Method getMethod(Request request, Response response) {
    switch (request.getMethod()) {
      case "GET":
        return new Get(request, response);
      case "POST":
        return new Post(request, response);
      case "PUT":
        return new Put(request, response);
      case "DELETE":
        return new Delete(request, response);
      default:
        response.setResponse(Response.ResponseCode.ERROR);
        response.removeField("content-type");
        return null;
    }
  }
}
