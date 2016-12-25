package ladowski.serwishandlowy;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class tab_2 extends Fragment {
    public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        private ImageView imageView;
        private Bitmap image;

        public DownloadImageTask(ImageView imageView) {
            this.imageView = imageView;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                image = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                image = null;
            }
            return image;
        }

        @SuppressLint("NewApi")
        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                imageView.setImageBitmap(result);
            }
        }
    }
    ListView lista;
    static List listaid = new ArrayList();
    static List listanazw = new ArrayList();
    static List listasciezek = new ArrayList();
    static List listaopisow = new ArrayList();
    static List listacen = new ArrayList();
    static List listauserow = new ArrayList();
    static List listaemaili = new ArrayList();
    static List listatelefonow = new ArrayList();
    static List listakategorii = new ArrayList();
    static List listaproducentow = new ArrayList();
    private static ArrayList<Item> m_parts = new ArrayList<Item>();
    private ItemAdapter m_adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_2, container, false);
        lista = (ListView) view.findViewById(R.id.listView);
        m_adapter = new ItemAdapter(getActivity().getApplicationContext(), R.layout.showelement, m_parts);
        lista.setAdapter(m_adapter);
        final EditText search = (EditText) view.findViewById(R.id.etwyszukaj2);
        Button bwyszukaj = (Button) view.findViewById(R.id.bwyszukaj2);
        bwyszukaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputManager = (InputMethodManager)
                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                listanazw.clear();
                listacen.clear();
                listasciezek.clear();
                listaid.clear();
                listaopisow.clear();
                listauserow.clear();
                listaemaili.clear();
                listatelefonow.clear();
                listakategorii.clear();
                listaproducentow.clear();
                m_parts.clear();

                Response.Listener<String> rl2 = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        try {
                            Log.i("tag", response);
                            JSONObject jsonObject = new JSONObject(response);

                            JSONArray result = jsonObject.getJSONArray("result");
                            for(int x=0;x<result.length();x++) {
                                JSONObject collegeData = result.getJSONObject(x);
                                m_parts.add(new Item(collegeData.getString("name"), collegeData.getString("price"),
                                        "http://"+LoginActivity.ip+":8080/inz/"+collegeData.getString("photopath")));
                                Log.i("tag", "http://"+LoginActivity.ip+":8080/inz/"+collegeData.getString("photopath"));
                                listanazw.add(collegeData.getString("name"));
                                listasciezek.add("http://"+LoginActivity.ip+":8080/inz/"+collegeData.getString("photopath"));
                                listacen.add(collegeData.getString("price"));
                                listaid.add(collegeData.getString("id_prod"));
                                listaopisow.add(collegeData.getString("description"));
                                listauserow.add(collegeData.getString("user_name"));
                                listaemaili.add(collegeData.getString("email"));
                                listatelefonow.add(collegeData.getString("phone"));
                                listakategorii.add(collegeData.getString("category"));
                                listaproducentow.add(collegeData.getString("producer"));
                            }
                            InputMethodManager imgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                            imgr.hideSoftInputFromWindow(getView().getWindowToken(),0);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                };
                String url = "http://" + LoginActivity.ip + ":8080/inz/request_android.php?category=telefony";
                SearchRequest sr = new SearchRequest(url,search.getText().toString(),rl2);

                RequestQueue queue = Volley.newRequestQueue(getContext());
                queue.add(sr);
                search.requestFocus();
            }
        });


        napelnij_liste();

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view2, int pos, long id) {
                final Dialog dial = new Dialog(getActivity(),android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                dial.setContentView(R.layout.details);
                dial.setTitle("Szczegoly");
                ImageView img2 = (ImageView) dial.findViewById(R.id.imageView2);
                TextView nazwa = (TextView) dial.findViewById(R.id.tvnazwa);
                TextView cena = (TextView) dial.findViewById(R.id.tvcena);
                TextView kateg = (TextView) dial.findViewById(R.id.tvkategoria);
                TextView producent = (TextView) dial.findViewById(R.id.tvproducent);
                TextView opis = (TextView) dial.findViewById(R.id.tvopis);
                TextView user = (TextView) dial.findViewById(R.id.tvuser);
                TextView email = (TextView) dial.findViewById(R.id.tvemail);
                TextView telefon = (TextView) dial.findViewById(R.id.tvtelefon);
                Button powrot = (Button) dial.findViewById(R.id.bpowrot);
                Button sendmsg = (Button) dial.findViewById(R.id.bSendmsg);
                final int pozycja = pos;

                final String sciezka = listasciezek.get(pos).toString();
                final String nazwa_produktu = listanazw.get(pos).toString();
                final String cena_produktu = listacen.get(pos).toString();
                final String kategoria_produktu = listakategorii.get(pos).toString();
                final String producent_produktu = listaproducentow.get(pos).toString();
                final String opis_produktu_tmp = listaopisow.get(pos).toString();
                final String opis_produktu_tmp2 = opis_produktu_tmp.replace("<br>","\n");
                final String opis_produktu = opis_produktu_tmp2.replace("<br />","");
                final String nazwa_uzytkownika = listauserow.get(pos).toString();
                final String email_uzytkownika = listaemaili.get(pos).toString();
                final String telefon_uzytkownika = listatelefonow.get(pos).toString();

                new DownloadImageTask(img2).execute(sciezka);//ladowanie obrazu
                nazwa.setText(nazwa_produktu);
                cena.setText(cena_produktu);
                kateg.setText("Kategoria: "+kategoria_produktu);
                producent.setText("Producent: "+producent_produktu);
                opis.setText(opis_produktu);
                user.setText("Oferta użytkownika "+nazwa_uzytkownika);
                email.setText("Email: "+email_uzytkownika);
                telefon.setText("Telefon: "+telefon_uzytkownika);

                powrot.setOnClickListener(new View.OnClickListener(){

                    public void onClick(View v){
                        dial.dismiss();
                    }
                });
                sendmsg.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        final Dialog sendmessage = new Dialog(getActivity(),android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                        sendmessage.setContentView(R.layout.newmsg);
                        sendmessage.setTitle("Nowa wiadomość");
                        final EditText nazwausera = (EditText) sendmessage.findViewById(R.id.etDokogo);
                        final EditText temat = (EditText) sendmessage.findViewById(R.id.etTemat);
                        final EditText msg = (EditText) sendmessage.findViewById(R.id.etMsg);
                        Button send = (Button) sendmessage.findViewById(R.id.bWyslij);
                        Button powrot2 = (Button) sendmessage.findViewById(R.id.bPowrot);
                        nazwausera.setText(nazwa_uzytkownika);
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


        Log.i("tag", "znowu tworze wiew2");


        return view;
    }
    void napelnij_liste()
    {
        listanazw.clear();
        listacen.clear();
        listasciezek.clear();
        listaid.clear();
        listaopisow.clear();
        listauserow.clear();
        listaemaili.clear();
        listatelefonow.clear();
        listakategorii.clear();
        listaproducentow.clear();

        m_parts.clear();
        Response.Listener<String> rl = new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                try {
                    Log.i("tag", response);
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray result = jsonObject.getJSONArray("result");
                    for(int x=0;x<result.length();x++) {
                        JSONObject collegeData = result.getJSONObject(x);
                        m_parts.add(new Item(collegeData.getString("name"), collegeData.getString("price"),
                                "http://"+LoginActivity.ip+":8080/inz/"+collegeData.getString("photopath")));
                        Log.i("tag", "http://"+LoginActivity.ip+":8080/inz/"+collegeData.getString("photopath"));
                        listanazw.add(collegeData.getString("name"));
                        listasciezek.add("http://"+LoginActivity.ip+":8080/inz/"+collegeData.getString("photopath"));
                        listacen.add(collegeData.getString("price"));
                        listaid.add(collegeData.getString("id_prod"));
                        listaopisow.add(collegeData.getString("description"));
                        listauserow.add(collegeData.getString("user_name"));
                        listaemaili.add(collegeData.getString("email"));
                        listatelefonow.add(collegeData.getString("phone"));
                        listakategorii.add(collegeData.getString("category"));
                        listaproducentow.add(collegeData.getString("producer"));
                    }
                    InputMethodManager imgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    imgr.hideSoftInputFromWindow(getView().getWindowToken(),0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        };
        String url = "http://" + LoginActivity.ip + ":8080/inz/request_android.php?category=telefony";
        SimpleRequest sr = new SimpleRequest(url,rl);

        RequestQueue queue = Volley.newRequestQueue(this.getContext());
        queue.add(sr);
    }
    public void onDestroy(){
        super.onDestroy();
        listanazw.clear();
        listacen.clear();
        listasciezek.clear();
        listaid.clear();
        listaopisow.clear();
        listauserow.clear();
        listaemaili.clear();
        listatelefonow.clear();
        listakategorii.clear();
        listaproducentow.clear();
        m_parts.clear();
        lista.setAdapter(null);
    }
}
