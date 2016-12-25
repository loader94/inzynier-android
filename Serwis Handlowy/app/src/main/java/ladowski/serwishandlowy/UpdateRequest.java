package ladowski.serwishandlowy;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Paweu on 2016-12-18.
 */
public class UpdateRequest extends StringRequest {

    private static final String UpdRequestURL = "http://" + LoginActivity.ip + ":8080/inz/updatemsg_android.php";
    private Map<String,String> params;

    public UpdateRequest(String id, Response.Listener<String> listener)
    {
        super(Request.Method.POST, UpdRequestURL, listener, null);
        params = new HashMap<>();
        params.put("id", id);
    }
    @Override
    public Map<String,String> getParams()
    {
        return params;
    }
}
