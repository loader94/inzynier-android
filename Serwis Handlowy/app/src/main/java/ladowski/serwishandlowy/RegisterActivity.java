package ladowski.serwishandlowy;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    public static String ip;
    EditText etLogin, etPass1, etPass2, etEmail, etPhone, etIP;
    Button bReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etLogin = (EditText) findViewById(R.id.etLogin);
        etPass1 = (EditText) findViewById(R.id.etPass1);
        etPass2 = (EditText) findViewById(R.id.etPass2);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etIP = (EditText) findViewById(R.id.etIP);
        bReg = (Button) findViewById(R.id.bReg);

        bReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                ip = etIP.getText().toString();
                final String login = etLogin.getText().toString();
                final String pass1 = etPass1.getText().toString();
                final String pass2 = etPass2.getText().toString();
                final String email = etEmail.getText().toString();
                final String phone = etPhone.getText().toString();
                if(ip=="" || ip==null || ip.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Podaj adres bazy",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                Response.Listener<String> rl = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        try {
                            Log.i("tagconvertstr", response);
                            JSONObject jsonresp = new JSONObject(response);
                            boolean success = jsonresp.getBoolean("success");

                            if(success){
                                Toast.makeText(RegisterActivity.this, "Dodalem do bazy",
                                        Toast.LENGTH_LONG).show();
                                Intent logi = new Intent(RegisterActivity.this,LoginActivity.class);
                                RegisterActivity.this.startActivity(logi);
                            }
                            else {
                                String error = jsonresp.getString("error");
                                Toast.makeText(RegisterActivity.this, error,
                                        Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };


                RegisterRequest rr = new RegisterRequest(login,pass1,pass2,email,phone,rl);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(rr);
            }
        });
    }
}
