package ladowski.serwishandlowy;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Paweu on 2016-12-18.
 */
public class MessageSendRequest extends StringRequest {
    private static final String MessageSendUrl = "http://" + LoginActivity.ip + ":8080/inz/sendmessage_android.php";
    private Map<String,String> params;

    public MessageSendRequest(String receiver, String sender, String title, String msg, Response.Listener<String> listener)
    {
        super(Request.Method.POST, MessageSendUrl, listener, null);
        params = new HashMap<>();
        params.put("receiver", receiver);
        params.put("sender", sender);
        params.put("title", title);
        params.put("msg",msg);
    }
    @Override
    public Map<String,String> getParams()
    {
        return params;
    }
}
