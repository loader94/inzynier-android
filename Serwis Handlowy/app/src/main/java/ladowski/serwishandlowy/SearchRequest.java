package ladowski.serwishandlowy;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Paweu on 2016-11-30.
 */
public class SearchRequest extends StringRequest {

    private Map<String,String> params;

    public SearchRequest(String url, String fraza, Response.Listener<String> listener)
    {
        super(Request.Method.POST, url, listener, null);
        params = new HashMap<>();
        params.put("search", fraza);
    }
    @Override
    public Map<String,String> getParams()
    {
        return params;
    }
}
