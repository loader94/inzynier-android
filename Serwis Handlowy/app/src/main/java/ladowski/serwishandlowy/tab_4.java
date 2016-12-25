package ladowski.serwishandlowy;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class tab_4 extends Fragment {


    public tab_4() {
        // Required empty public constructor
    }
    static List listid = new ArrayList();
    static List listsender = new ArrayList();
    static List listreceiver = new ArrayList();
    static List listtitle = new ArrayList();
    static List listmsg = new ArrayList();
    static List listdata = new ArrayList();
    private static ArrayList<MsgItem> lista_wiadomosci = new ArrayList<MsgItem>();
    private MsgItemAdapter lista_wiadomosci_adapter;
    ListView listView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab_4, container, false);
        Button nieprzeczytane = (Button) view.findViewById(R.id.bNieprzeczytane);
        Button przeczytane = (Button) view.findViewById(R.id.bPrzeczytane);
        Button wyslane = (Button) view.findViewById(R.id.bWyslane);
        Button nowa = (Button) view.findViewById(R.id.bNowa);
        listView = (ListView) view.findViewById(R.id.lvMsgs);
        lista_wiadomosci_adapter = new MsgItemAdapter(getActivity().getApplicationContext(),R.layout.msgitem, lista_wiadomosci);
        listView.setAdapter(lista_wiadomosci_adapter);
        napelnijliste("nieprzeczytane");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View view2, int pos, long id){
                final Dialog dial = new Dialog(getActivity(),android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                dial.setContentView(R.layout.msgdetails);
                dial.setTitle("Szczegoly wiadomości");
                TextView tvod = (TextView) dial.findViewById(R.id.tvOd);
                TextView tvdo = (TextView) dial.findViewById(R.id.tvDo);
                TextView tvdata = (TextView) dial.findViewById(R.id.tvData);
                TextView tvtemat = (TextView) dial.findViewById(R.id.tvTemat);
                TextView tvmsg = (TextView) dial.findViewById(R.id.tvMsg);;;
                Button bodp = (Button) dial.findViewById(R.id.bOdp);
                Button bpowr = (Button) dial.findViewById(R.id.bPowrot);

                final int pozycja = pos;

                final String odkogo = listsender.get(pos).toString();
                final String dokogo = listreceiver.get(pos).toString();
                final String data = listdata.get(pos).toString();
                final String temat = listtitle.get(pos).toString();
                final String msg_tmp = listmsg.get(pos).toString();
                final String msg = msg_tmp.replace("<br />","\n");
                final String id_msg = listid.get(pos).toString();
                tvod.setText("Od: " + odkogo);
                tvdo.setText("Do: " + dokogo);
                tvdata.setText(data);
                tvtemat.setText("Temat: " + temat);
                tvmsg.setText(msg);
                //update
                if(!odkogo.equals(LoginActivity.login_name))
                {
                    Response.Listener<String> rl = new Response.Listener<String>(){
                        @Override
                        public void onResponse(String response){
                            try {

                                JSONObject jsonresp = new JSONObject(response);
                                boolean success = jsonresp.getBoolean("success");
                                if(success){


                                }
                                else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setMessage("Problem z odczytem")
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    UpdateRequest ur = new UpdateRequest(id_msg,rl);
                    RequestQueue queue = Volley.newRequestQueue(getContext());
                    queue.add(ur);
                }

                bpowr.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        dial.dismiss();
                    }
                });
                bodp.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        final Dialog sendmessage = new Dialog(getActivity(),android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                        sendmessage.setContentView(R.layout.newmsg);
                        sendmessage.setTitle("Nowa wiadomość");
                        final EditText nazwausera = (EditText) sendmessage.findViewById(R.id.etDokogo);
                        final EditText temat = (EditText) sendmessage.findViewById(R.id.etTemat);
                        final EditText msg = (EditText) sendmessage.findViewById(R.id.etMsg);
                        Button send = (Button) sendmessage.findViewById(R.id.bWyslij);
                        Button powrot2 = (Button) sendmessage.findViewById(R.id.bPowrot);

                        if(odkogo.equals(LoginActivity.login_name))
                            nazwausera.setText(dokogo);
                        else nazwausera.setText(odkogo);
                        send.setOnClickListener(new View.OnClickListener(){
                            public void onClick(View v) {
                                final String nazwausera1 = nazwausera.getText().toString();
                                final String temat1 = temat.getText().toString();
                                final String msg1 = msg.getText().toString();
                                if(nazwausera1=="" || nazwausera1==null || nazwausera1.isEmpty() ||
                                        temat1=="" || temat1==null || temat1.isEmpty() ||
                                        msg1=="" || msg1==null || msg1.isEmpty())
                                {
                                    Toast.makeText(getContext(), "Uzupełnij wszystkie pola",
                                            Toast.LENGTH_LONG).show();
                                    return;
                                }
                                else
                                {
                                    final String sender = LoginActivity.login_name;
                                    Response.Listener<String> rlmsg = new Response.Listener<String>(){
                                        @Override
                                        public void onResponse(String response){
                                            try {
                                                Log.i("lol",response);
                                                JSONObject jsonresp = new JSONObject(response);
                                                boolean success = jsonresp.getBoolean("success");
                                                if(success){
                                                    Toast.makeText(getContext(), "Wiadomość wysłana",
                                                            Toast.LENGTH_LONG).show();
                                                    sendmessage.dismiss();
                                                }
                                                else {
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                                    builder.setMessage("Nie istnieje taki użytkownik")
                                                            .setNegativeButton("Retry", null)
                                                            .create()
                                                            .show();
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    };

                                    MessageSendRequest msr = new MessageSendRequest(nazwausera1,sender,temat1,msg1,rlmsg);
                                    RequestQueue queue = Volley.newRequestQueue(getContext());
                                    queue.add(msr);
                                }
                            }
                        });
                        powrot2.setOnClickListener(new View.OnClickListener(){
                            public void onClick(View v) {
                                sendmessage.dismiss();
                            }
                        });
                        sendmessage.show();
                    }
                });
                dial.show();
            }
        });

        nieprzeczytane.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                napelnijliste("nieprzeczytane");
                InputMethodManager imgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

                imgr.hideSoftInputFromWindow(getView().getWindowToken(),0);
            }
        });
        przeczytane.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                napelnijliste("przeczytane");
                InputMethodManager imgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

                imgr.hideSoftInputFromWindow(getView().getWindowToken(),0);
            }
        });
        wyslane.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                napelnijliste("wyslane");
                InputMethodManager imgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

                imgr.hideSoftInputFromWindow(getView().getWindowToken(),0);
            }
        });

        nowa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog sendmessage = new Dialog(getActivity(),android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                sendmessage.setContentView(R.layout.newmsg);
                sendmessage.setTitle("Nowa wiadomość");
                final EditText nazwausera = (EditText) sendmessage.findViewById(R.id.etDokogo);
                final EditText temat = (EditText) sendmessage.findViewById(R.id.etTemat);
                final EditText msg = (EditText) sendmessage.findViewById(R.id.etMsg);
                Button send = (Button) sendmessage.findViewById(R.id.bWyslij);
                Button powrot2 = (Button) sendmessage.findViewById(R.id.bPowrot);
                send.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v) {
                        final String nazwausera1 = nazwausera.getText().toString();
                        final String temat1 = temat.getText().toString();
                        final String msg1 = msg.getText().toString();
                        if(nazwausera1=="" || nazwausera1==null || nazwausera1.isEmpty() ||
                                temat1=="" || temat1==null || temat1.isEmpty() ||
                                msg1=="" || msg1==null || msg1.isEmpty())
                        {
                            Toast.makeText(getContext(), "Uzupełnij wszystkie pola",
                                    Toast.LENGTH_LONG).show();
                            return;
                        }
                        else
                        {
                            final String sender = LoginActivity.login_name;
                            Response.Listener<String> rlmsg = new Response.Listener<String>(){
                                @Override
                                public void onResponse(String response){
                                    try {
                                        Log.i("lol",response);
                                        JSONObject jsonresp = new JSONObject(response);
                                        boolean success = jsonresp.getBoolean("success");
                                        if(success){
                                            Toast.makeText(getContext(), "Wiadomość wysłana",
                                                    Toast.LENGTH_LONG).show();
                                           sendmessage.dismiss();
                                        }
                                        else {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                            builder.setMessage("Nie istnieje taki użytkownik")
                                                    .setNegativeButton("Retry", null)
                                                    .create()
                                                    .show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            };

                            MessageSendRequest msr = new MessageSendRequest(nazwausera1,sender,temat1,msg1,rlmsg);
                            RequestQueue queue = Volley.newRequestQueue(getContext());
                            queue.add(msr);
                        }
                    }
                });
                powrot2.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v) {
                        sendmessage.dismiss();
                    }
                });
                sendmessage.show();
            }
        });

        return view;
    }
    void napelnijliste(String type)
    {
        listid.clear();
        listsender.clear();
        listreceiver.clear();
        listtitle.clear();
        listmsg.clear();
        listdata.clear();
        lista_wiadomosci.clear();

        Response.Listener<String> rl = new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                try {
                    Log.i("tag", response);
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray result = jsonObject.getJSONArray("result");
                    for(int x=0;x<result.length();x++) {
                        JSONObject collegeData = result.getJSONObject(x);
                        lista_wiadomosci.add(new MsgItem(collegeData.getString("sender_name"), collegeData.getString("receiver_name"),
                                collegeData.getString("send_time"), collegeData.getString("title")));

                        listid.add(collegeData.getString("id_msg"));
                        listsender.add(collegeData.getString("sender_name"));
                        listreceiver.add(collegeData.getString("receiver_name"));
                        listtitle.add(collegeData.getString("title"));
                        listmsg.add(collegeData.getString("msg"));
                        listdata.add(collegeData.getString("send_time"));
                    }
                    InputMethodManager imgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    imgr.hideSoftInputFromWindow(getView().getWindowToken(),0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        };
        MessagesGetRequest mgr = new MessagesGetRequest(LoginActivity.login_name,type,rl);


        RequestQueue queue = Volley.newRequestQueue(this.getContext());
        queue.add(mgr);
    }
    public void onDestroy()
    {
        super.onDestroy();
        listid.clear();
        listsender.clear();
        listreceiver.clear();
        listtitle.clear();
        listmsg.clear();
        listdata.clear();
        lista_wiadomosci.clear();
        listView.setAdapter(null);
    }
}
