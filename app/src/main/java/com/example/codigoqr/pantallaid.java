package com.example.codigoqr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class pantallaid extends AppCompatActivity
{
    private Spinner spEvento2;
    private AsyncHttpClient cliente;
    EditText teid;
    String tid;
    String respuesta3;
    String seleccion;
    String idevent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantallaid);

        spEvento2 = (Spinner) findViewById(R.id.spEvento2);
        cliente = new AsyncHttpClient();
        teid   = (EditText) findViewById(R.id.textid);

        llenarSpinner();

        Button btnenv = (Button) findViewById(R.id.btnenviar);
        btnenv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                tid = teid.getText().toString();
                seleccion = spEvento2.getSelectedItem().toString();
                String sele22 = seleccion.replace( ' ','_');
                String url = "https://administracionbd-era.000webhostapp.com/idevento.php?";
                final String para = "Nombre_evento="+ sele22;
                cliente.post(url+para, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody)
                    {
                        if(statusCode == 200) {

                            respuesta3 = new String(responseBody);
                            JSONArray R3 = null;
                            try {
                                R3 = new JSONArray(respuesta3);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {
                                idevent = R3.getJSONObject(0).getString("id_evento");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            llenarasistencia(idevent,tid);
                        }
                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error)
                    {
                        Toast.makeText(getApplicationContext(), "NO CONECTO para el evento", Toast.LENGTH_LONG).show();
                    }
                });
                Intent intent = new Intent(v.getContext(),pantallainicio.class);
                startActivityForResult(intent, 0);
            }
        });


    }

    private void llenarSpinner()
    {
        String url = "https://administracionbd-era.000webhostapp.com/obtenerDatos.php";
        cliente.post(url, new AsyncHttpResponseHandler()
        {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody)
            {

                if(statusCode == 200)
                {
                    cargarSpinner(new String (responseBody));
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error)
            {

            }
        });
    }
    private void cargarSpinner(String respuesta)
    {
        ArrayList<Evento> lista = new ArrayList<Evento>();
        try
        {
            JSONArray jsonSSS = new JSONArray(respuesta);

            for(int i=0; i<jsonSSS.length(); i++)
            {
                Evento ev = new Evento();
                ev.setNombre_evento(jsonSSS.getJSONObject(i).getString("nombre_evento"));
                lista.add(ev);
            }
            ArrayAdapter<Evento> a = new ArrayAdapter<Evento>(this, android.R.layout.simple_dropdown_item_1line,lista);
            spEvento2.setAdapter(a);
        }catch (Exception e)
        {

        }
    }

    private void llenarasistencia (String idev, String idus)
    {

        String url = "https://administracionbd-era.000webhostapp.com/insert.php?";
        String para ="id_evento="+idev+"&id_usuario="+idus;
        //Toast.makeText(getApplicationContext(), "el insert"+para, Toast.LENGTH_LONG).show();
        cliente.post(url+para, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody)
            {
                if(statusCode == 200) {
                    //Toast.makeText(getApplicationContext(), "Ya estuvo", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error)
            {
                Toast.makeText(getApplicationContext(), "NO CONECTO para el insertar asistencia", Toast.LENGTH_LONG).show();
            }
        });
    }
}
