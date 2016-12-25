package ladowski.serwishandlowy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Paweu on 2016-11-28.
 */
public class LoginRequest extends StringRequest
{
    private static final String LoginRequestUrl = "http://" + LoginActivity.ip + ":8080/inz/login_android.php";
    private Map<String,String> params;

    public LoginRequest(String login, String pass, Response.Listener<String> listener)
    {
        super(Request.Method.POST, LoginRequestUrl, listener, null);
        params = new HashMap<>();
        params.put("login", login);
        params.put("passw", pass);
    }
    @Override
    public Map<String,String> getParams()
    {
        return params;
    }
}
