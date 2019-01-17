package messages;

import java.util.HashMap;
import java.util.Map;

public class Message {
    private String type;
    private Map<String, String> fields = new HashMap<>();
    private String body = "";

    String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, String> getFields() {
        return fields;
    }

    public String getField(String key) {
        return fields.get(key);
    }

    public void removeField(String key) {
        fields.remove(key);
    }

    public void addField(String key, String value) {
        fields.put(key, value);
    }

    public String getBody() {
        return body;
    }

    /**
     * Sets the response's body. Automatically updates the content-length header parameter to match
     * the new body length. Overwrites the existing body
     *
     * @param body The new body value
     */
    public void setBody(String body) {
        this.body = body;
        addField("content-length", Integer.toString(body.length()));
    }
}
