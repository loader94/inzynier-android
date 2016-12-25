package ladowski.serwishandlowy;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Paweu on 2016-11-30.
 */
public class SimpleRequest extends StringRequest
{
    //public static String SimpleRequestUrl = "http://" + LoginActivity.ip + ":8080/inz/request_android.php";

    public SimpleRequest(String url, Response.Listener<String> listener)
    {
        super(Request.Method.GET, url, listener, null);
    }


}
