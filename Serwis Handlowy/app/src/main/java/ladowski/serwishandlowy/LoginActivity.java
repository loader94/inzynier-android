package ladowski.serwishandlowy;

import android.content.Intent;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private boolean convertToBoolean(String value) {
        boolean returnValue = false;
        if ("1".equalsIgnoreCase(value) || "yes".equalsIgnoreCase(value) ||
                "true".equalsIgnoreCase(value) || "on".equalsIgnoreCase(value))
            returnValue = true;
        return returnValue;
    }

    public static String ip;
    public static String login_name;
    EditText etIP;
    EditText etLogin;
    EditText etPassword;
    Button bLogin;
    TextView mtRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        etIP = (EditText) findViewById(R.id.etIP);
        etLogin = (EditText) findViewById(R.id.etLogin);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bLogin = (Button) findViewById(R.id.bLogin);
        mtRegister = (TextView) findViewById(R.id.mtRegister);

        mtRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View w){
                Intent regintent = new Intent(LoginActivity.this,RegisterActivity.class);
                LoginActivity.this.startActivity(regintent);
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ip = etIP.getText().toString();
                login_name = etLogin.getText().toString();
                final String login = etLogin.getText().toString();
                final String pass = etPassword.getText().toString();
                if(ip=="" || ip==null || ip.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Podaj adres bazy",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                Response.Listener<String> rl = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        try {

                            JSONObject jsonresp = new JSONObject(response);
                            boolean success = jsonresp.getBoolean("success");
                            if(success){
                                String is_admin, is_mode, is_banned, name, email, phone;
                                int id_user = jsonresp.getInt("id_user");
                                is_admin = jsonresp.getString("is_admin");
                                is_mode = jsonresp.getString("is_mode");
                                is_banned = jsonresp.getString("is_banned");
                                name = jsonresp.getString("name");
                                email = jsonresp.getString("email");
                                phone = jsonresp.getString("phone");
                                //String ban=jsonresp.getString("is_banned");
                                boolean admin, mode, banned;
                                admin = convertToBoolean(is_admin);
                                mode = convertToBoolean(is_mode);
                                banned = convertToBoolean(is_banned);

                                Intent serviceIntent = new Intent(LoginActivity.this, ServiceActivity.class);
                                serviceIntent.putExtra("id_user", id_user);
                                serviceIntent.putExtra("admin", admin);
                                serviceIntent.putExtra("mode", mode);
                                serviceIntent.putExtra("banned", banned);
                                serviceIntent.putExtra("name", name);
                                serviceIntent.putExtra("email", email);
                                serviceIntent.putExtra("phone", phone);
                                if(is_banned=="1")
                                {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                    builder.setMessage("Jesteś zbanowany")
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();
                                }
                                else {
                                    LoginActivity.this.startActivity(serviceIntent);
                                }
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Logowanie nieudane")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest lr = new LoginRequest(login,pass,rl);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(lr);
                //Intent serviceIntent = new Intent(LoginActivity.this, ServiceActivity.class);
                //LoginActivity.this.startActivity(serviceIntent);
            }
        });

    }

}
