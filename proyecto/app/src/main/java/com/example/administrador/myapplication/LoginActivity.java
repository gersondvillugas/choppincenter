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

public class LoginActivity extends AppCompatActivity {
    EditText email,clave;
    private String urlApiREST = "http://cybertrom.000webhostapp.com/api/usuario/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email=(EditText)findViewById(R.id.etUsuario);
        clave=(EditText)findViewById(R.id.etClave);
    }
    public void LoginUser(View view) {
        TareaWSLogin tarea = new TareaWSLogin();
        tarea.execute(
                email.getText().toString(),
                clave.getText().toString()
        );
    }

    public void NewUser(View view) {
        Intent i = new Intent(this,RegistroActivity.class);
        startActivity(i);
    }

    private class TareaWSLogin extends AsyncTask<String,Integer,Boolean> {

        private String[] usuario = new String[4];

        protected Boolean doInBackground(String... params) {

            HttpURLConnection connection = null;
            DataOutputStream printout;
            BufferedReader reader = null;
            boolean result = true;

            try {

                URL url = new URL(urlApiREST+"login");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setUseCaches(false);
                connection.connect();

                //Construimos el objeto producto en formato JSON (jsonParam)
                JSONObject datos  = new JSONObject();
                datos.put("email", params[0]);
                datos.put("clave", params[1]);

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
                }else{
                    usuario[0] = idusuario;
                    usuario[1] = parentObject.getString("apellido");
                    usuario[2] = parentObject.getString("nombre");
                    usuario[3] = parentObject.getString("email");
                }

            } catch(Exception ex) {
                Log.e("ServicioRest","Error!", ex);
                result = false;
            }

            return result;
        }

        protected void onPostExecute(Boolean result) {
            if (result) {
                Toast.makeText(getApplicationContext(),"Has ingresado correctamente",Toast.LENGTH_LONG).show();
                Intent i= new Intent(getApplicationContext(),PrincipalActivity.class);
                startActivity(i);

            }else{
                Toast.makeText(getApplicationContext(),"Usuario o contraseña incorrecto",Toast.LENGTH_LONG).show();
            }
        }
    }

}

