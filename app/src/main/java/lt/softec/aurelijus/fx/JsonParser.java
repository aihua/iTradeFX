package lt.softec.aurelijus.fx;


import org.json.JSONException;
import org.json.JSONObject;

public class JsonParser {

    private String jsonString;

    public JsonParser (String string) {
        jsonString = string;
    }

    public String buyAmount () throws JSONException {
    JSONObject mainObject = new JSONObject(jsonString);
    String buyAmount = mainObject.getString("amount");
    return buyAmount;}
}