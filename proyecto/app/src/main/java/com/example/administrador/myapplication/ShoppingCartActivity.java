package com.example.administrador.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ShoppingCartActivity extends AppCompatActivity {
    private TextView cantidad, precio, total;
    private TextView can, tot,nom,pre;
    private String urlApiREST = "http://cybertrom.000webhostapp.com/api/carro/";
    //https://cybertrom.000webhostapp.com/api/carro/registro
    private ImageView im;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        nom=(TextView)findViewById(R.id.tvNombre);
        can = (TextView) findViewById(R.id.tvCantidad);

        tot = (TextView) findViewById(R.id.tvTotal);
        pre = (TextView) findViewById(R.id.tvPrecio);

        recibirdatos();
    }

    private void recibirdatos() {
        Bundle extras = getIntent().getExtras();
        String z=extras.getString("v3");
        String a = extras.getString("v1");
        String b = extras.getString("v2");
        //int  b=extras.getInt("v2");
        cantidad = (TextView) findViewById(R.id.tvCantidad);
        precio = (TextView) findViewById(R.id.tvPrecio);
        nom=(TextView) findViewById(R.id.tvNombre);
        total = (TextView) findViewById(R.id.tvTotal);
        int a1 = Integer.parseInt(a);
        double b1 = Double.parseDouble((b));
        double c1 = a1 * b1;
        String c = "" + c1;
        total.setText(c);
        cantidad.setText(a);
        nom.setText(z);
        precio.setText(b);
    }

    public void RegistroCarro(View view) {
        TareaWSRegistro tarea = new TareaWSRegistro();
        tarea.execute(
                nom.getText().toString(),

                can.getText().toString(),
                pre.getText().toString(),
                tot.getText().toString()
                /*email.getText().toString(),
                clave.getText().toString()*/
        );
    }

    private class TareaWSRegistro extends AsyncTask<String, Integer, Boolean> {

        protected Boolean doInBackground(String... params) {

            HttpURLConnection connection = null;
            DataOutputStream printout;
            BufferedReader reader = null;
            boolean result = true;

            try {

                URL url = new URL(urlApiREST + "registro");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setUseCaches(false);
                connection.connect();

                //Construimos el objeto carro en formato JSON (jsonParam)
                JSONObject datos = new JSONObject();
                datos.put("nombre", params[0]);

                datos.put("cantidad", params[1]);
                datos.put("precio", params[2]);
                datos.put("total", params[3]);

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
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String finalJson = buffer.toString();
                JSONObject parentObject = new JSONObject(finalJson);
                String idusuario = parentObject.getString("id");
                if (idusuario.equals("0")) {
                    result = false;
                    System.out.println("esta  mal");
                }

            } catch (Exception ex) {
                Log.e("ServicioRest", "Error!", ex);
                result = false;
            }

            return result;
        }
        protected void onPostExecute(Boolean result) {
            if (result) {
                Toast.makeText(getApplicationContext(),"Comprado correctamente",Toast.LENGTH_LONG).show();
            //    Intent i= new Intent(getApplicationContext(),LoginActivity.class);
              ///  startActivity(i);
            }else{
                Toast.makeText(getApplicationContext(),"Error al realizar compra",Toast.LENGTH_LONG).show();
            }
        }

    }



}
