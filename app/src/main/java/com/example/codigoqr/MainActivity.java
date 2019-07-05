package com.example.codigoqr;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ActivityChooserView;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity
{
    private final int MY_PERMISSIONS_REQUEST_CAMERA = 123;

    private Spinner spEvento;

    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    SurfaceView cameraView;
    TextView qrResult;
    String respuesta2;
    String respuesta;
    String respuesta3;
    String seleccion;

    private AsyncHttpClient cliente;
    private AsyncHttpClient cliente2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spEvento = (Spinner) findViewById(R.id.spEvento);
        cliente = new AsyncHttpClient();
        cliente2 = new AsyncHttpClient();


        llenarSpinner();

        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT > 22) {
                if (shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA))
                    Toast.makeText(getApplicationContext(), "Esta aplicación necesita acceder a la cámara para funcionar", Toast.LENGTH_SHORT).show();
                requestPermissions(new String[]{android.Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
            }
        }

        //Texto con el resultado del qr
        qrResult = (TextView) findViewById(R.id.resultado_qr);
        //Vista de la cámara
        cameraView = (SurfaceView) findViewById(R.id.camera_view);

        //Creama el lector de qr
        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();

        //Creama la camara
        cameraSource = new CameraSource
                .Builder(this, barcodeDetector)
                .build();

        // Prepara el lector de qr
        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                //Verifica si el usuario ha dado permiso para la camara
                if (ContextCompat.checkSelfPermission(getBaseContext(),  android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    try {
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException ie) {
                        Log.e("CAMERA SOURCE", ie.getMessage());
                    }
                }
            }
            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) { }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        //Establece la función al escanear un código
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() { }

            @Override
            public void receiveDetections(Detector.Detections detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();

                if (barcodes.size() != 0) {
                   //Establece el valor del qr en el textview
                    qrResult.post(new Runnable() {
                        public void run() {

                            respuesta2 = barcodes.valueAt(0).displayValue.toString();
                            //Toast.makeText(getApplicationContext(), "lo que se manda al php"+respuesta2, Toast.LENGTH_LONG).show();
                            elhash h = new elhash();
                            h.setHash1(respuesta2);
                            comprarhash(h);
                        }
                    });

                    //Cierra el detector de códigos
                    barcodeDetector.release();


                }
            }
        });
    }

    private void comprarhash (elhash h)
    {
        String url = "https://administracionbd-era.000webhostapp.com/compara.php?";
        final String para = "hash1="+ h.getHash1();
        seleccion = spEvento.getSelectedItem().toString();
        cliente.post(url+para, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody)
            {
                if(statusCode == 200) {

                    respuesta = new String(responseBody);
                    JSONArray N = null;
                    try {
                        N = new JSONArray(respuesta);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        qrResult.setText(N.getJSONObject(0).getString("nombre"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //Toast.makeText(getApplicationContext(), "Lo que esta en el Spinner: "+seleccion, Toast.LENGTH_LONG).show();
                    String sele2 = seleccion.replace( ' ','_');
                    Evento2 eve = new Evento2();
                    eve.setNombre_evento(sele2);
                    conseguirideve(eve);
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error)
            {
                Toast.makeText(getApplicationContext(), "NO CONECTO para el hash", Toast.LENGTH_LONG).show();
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
            spEvento.setAdapter(a);
        }catch (Exception e)
        {

        }
    }
    private void conseguirideve (Evento2 eve)
    {
        String url = "https://administracionbd-era.000webhostapp.com/idevento.php?";
        final String para = "Nombre_evento="+ eve.getNombre_evento();
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
                    JSONArray R = null;
                    try {
                        R = new JSONArray(respuesta);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //Toast.makeText(getApplicationContext(), "id del evento= "+respuesta3, Toast.LENGTH_LONG).show();
                    //Toast.makeText(getApplicationContext(), "id del usuario= "+respuesta, Toast.LENGTH_LONG).show();

                    asistencia asis = new asistencia();
                    try {
                        asis.setElidevento(R3.getJSONObject(0).getString("id_evento"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        asis.setElidusuario(R.getJSONObject(0).getString("id_usuario"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    llenarasistencia(asis);
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error)
            {
                Toast.makeText(getApplicationContext(), "NO CONECTO para el evento", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void llenarasistencia (asistencia ins)
    {

        String url = "https://administracionbd-era.000webhostapp.com/insert.php?";
        String para ="id_evento="+ins.getElidevento()+"&id_usuario="+ins.getElidusuario();
        //Toast.makeText(getApplicationContext(), "el insert"+para, Toast.LENGTH_LONG).show();
        cliente.post(url+para, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody)
            {
                if(statusCode == 200) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable()
                    {
                        public void run() {
                            //código que se ejecuta tras el "delay"
                            Intent intent = new Intent(MainActivity.this,pantallainicio.class);
                            startActivityForResult(intent, 0);
                        }
                    }, 2000);
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