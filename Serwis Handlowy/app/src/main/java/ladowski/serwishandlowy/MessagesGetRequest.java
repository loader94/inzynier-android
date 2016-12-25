package ladowski.serwishandlowy;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Paweu on 2016-12-18.
 */
public class MessagesGetRequest extends StringRequest{
    private static final String MessagesGetUrl = "http://" + LoginActivity.ip + ":8080/inz/messagerequest_android.php";
    private Map<String,String> params;

    public MessagesGetRequest(String username, String type, Response.Listener<String> listener)
    {
        super(Request.Method.POST, MessagesGetUrl, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("type", type);
    }
    @Override
    public Map<String,String> getParams()
    {
        return params;
    }
}
