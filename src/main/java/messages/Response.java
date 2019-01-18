package messages;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

public class Response extends Message {

    private byte[] byteBody; // Byte body is used instead of the

    //TODO: Learn more about enums and use some built-in methods
    public enum ContentType {
        JSON,
        HTML,
        TEXT
    }

    public Response() {
        this.addField("date", new Date().toString());
        setContentType(ContentType.JSON); // Default content-type is api.json
        this.addField("server", "Evan Buss Java Http");
        this.addField("connection", "close");
    }

    public void setContentType(ContentType content) {
        switch (content) {
            case JSON:
                this.addField("content-type", "application/json");
                break;
            case HTML:
                this.addField("content-type", "text/html");
                break;
            case TEXT:
                this.addField("content-type", "text/plain");
                break;
        }
    }

    public byte[] getByteBody() {
        return byteBody;
    }

    public void setByteBody(byte[] byteBody) {
        this.byteBody = byteBody;
        addField("content-length", Integer.toString(byteBody.length));
    }

    public void sendHeader(PrintWriter pw) {
        pw.println(this.getType());
        for (Map.Entry<String, String> entry : this.getFields().entrySet()) {
            pw.println(entry.getKey() + ": " + entry.getValue());
        }
        pw.println("");
    }

    public void sendBody(PrintWriter pw) {
        pw.println(this.getBody());
    }

    public void sendCompressedBody(DataOutputStream dataOut) {
        try {
            dataOut.write(getByteBody());
        } catch (IOException e) {
            System.err.println("Error sending compressed body");
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getType()).append("\n");
        for (Map.Entry<String, String> entry : this.getFields().entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        sb.append("\n");
        sb.append(this.getBody());
        return sb.toString();
    }
}
