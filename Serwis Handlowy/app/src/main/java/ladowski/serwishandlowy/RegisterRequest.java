package ladowski.serwishandlowy;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Paweu on 2016-11-29.
 */
public class RegisterRequest extends StringRequest{
    private static final String RegRequestURL = "http://" + RegisterActivity.ip + ":8080/inz/register_android.php";
    private Map<String,String> params;

    public RegisterRequest(String login, String pass1, String pass2, String email, String phone, Response.Listener<String> listener)
    {
        super(Request.Method.POST, RegRequestURL, listener, null);
        params = new HashMap<>();
        params.put("login", login);
        params.put("pass1", pass1);
        params.put("pass2", pass2);
        params.put("email", email);
        params.put("phone", phone);
    }
    @Override
    public Map<String,String> getParams()
    {
        return params;
    }
}
