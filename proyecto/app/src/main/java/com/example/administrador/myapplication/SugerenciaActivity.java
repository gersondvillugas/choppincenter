package com.example.administrador.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SugerenciaActivity extends AppCompatActivity {
    EditText nom,email,tel,suge;
    private String urlApiREST = "http://cybertrom.000webhostapp.com/api/sugerencia/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sugerencia);
        nom=(EditText)findViewById(R.id.etNombre);
        email=(EditText)findViewById(R.id.etEmail);
        tel=(EditText)findViewById(R.id.etCelular);
        suge=(EditText)findViewById(R.id.etSugerencia);
    }

    public void Send(View view) {
       TareaWSRegistro tarea = new TareaWSRegistro();
        tarea.execute(
                nom.getText().toString(),
                tel.getText().toString(),
                email.getText().toString(),
                suge.getText().toString()
        );
    }

    private class TareaWSRegistro extends AsyncTask<String,Integer,Boolean> {

        protected Boolean doInBackground(String... params) {

            HttpURLConnection connection = null;
            DataOutputStream printout;
            BufferedReader reader = null;
            boolean result = true;

            try {

                URL url = new URL(urlApiREST+"registro");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setUseCaches(false);
                connection.connect();

                //Construimos el objeto producto en formato JSON (jsonParam)
                JSONObject datos  = new JSONObject();
                datos.put("nombre", params[0]);
                datos.put("telefono", params[1]);
                datos.put("email", params[2]);
                datos.put("sugerencia", params[3]);

                // Send POST output.
                byte[] outputInBytes = datos.toString().getBytes("UTF-8");
                printout = new DataOutputStream(connection.getOutputStream());
                printout.write(outputInBytes);
                printout.flush();
                printout.close();

                //GEt Post response
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line ="";
                while ((line = reader.readLine()) != null){
                    buffer.append(line);
                }

                String finalJson = buffer.toString();
                JSONObject parentObject = new JSONObject(finalJson);
                String idusuario = parentObject.getString("id");
                if(idusuario.equals("0")){
                    result = false;
                }

            } catch(Exception ex) {
                Log.e("ServicioRest","Error!", ex);
                result = false;
            }

            return result;
        }

        protected void onPostExecute(Boolean result) {
            if (result) {
                Toast.makeText(getApplicationContext(),"Sugenrecia enviada correctamente",Toast.LENGTH_LONG).show();

            }else{
                Toast.makeText(getApplicationContext(),"Error al  enviar sugerencia",Toast.LENGTH_LONG).show();
            }
        }
    }
}
