package com.example.administrador.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Compras extends AppCompatActivity {
    private ListView lstCompras;
    List<CardItem> items;
    private String urlApiREST = "https://cybertrom.000webhostapp.com/api/carro/listar";
    private ProgressDialog loading = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compras);

        loading = new ProgressDialog(this);
        loading.setCancelable(true);
        loading.setMessage("Cargando...");
        lstCompras = (ListView)findViewById(R.id.lstDatosc);
        listarCompras();

    }
    public void listarCompras(){
        TareaWSListarCompras tarea = new TareaWSListarCompras();
        tarea.execute();
    }
    public int getCount() {
        return items.size();
    }

    public Object getItem(int position) {
        return items.get(position);
    }


    public long getItemId(int position) {
        return position;
    }
    private class TareaWSListarCompras extends AsyncTask<String,Integer,Boolean> {

        private List<CardItem> listaCompras;
        protected void onPreExecute() {
            super.onPreExecute();
            loading.show();
        }

        protected Boolean doInBackground(String... params) {
            String urlApiFinal = "https://cybertrom.000webhostapp.com/api/carro/listar" ;
            boolean resul = true;

            try {
                RestConnection restConnection = new RestConnection(urlApiFinal, "GET");
                JSONObject parentObject = restConnection.getData();
                JSONArray parentArray = parentObject.getJSONArray("carros");
                listaCompras = new ArrayList<CardItem>();
               // listaCompras.add(new CardItem(0,"Listar todo"));

                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    int id = finalObject.getInt("id");
                    String nombre = finalObject.getString("nombre");
                    int cantidad = finalObject.getInt("cantidad");
                    double precio = finalObject.getDouble("precio");
                    double total = finalObject.getDouble("total");
                    /*System.out.println(nombre);
                    System.out.println("-----------");
                    System.out.println(cantidad);
                    System.out.println("-----------");
                    System.out.println(precio);
                    System.out.println("-----------");
                    System.out.println(total);
                    System.out.println("-----------");*/
                    System.out.println(nombre);
                    listaCompras.add(new CardItem(id, nombre, cantidad,precio,total));
                    //System.out.println("hello"+listaCompras.get(0));

                }

            } catch (Exception ex) {
                Log.e("ServicioRest", "Error!", ex);
                resul = false;
            }
            System.out.println("conexion:"+resul);
            return resul;
        }
        protected void onPostExecute(Boolean result) {
            if (result) {
                loading.dismiss();

                System.out.println("asdadasdasdasd");

              CarroListViewAdapter adaptador = new CarroListViewAdapter(Compras.this, listaCompras);
              CarroListViewAdapter adaptador2=new CarroListViewAdapter(Compras.this,listaCompras);
               lstCompras.setAdapter(adaptador2);
                // System.out.println("adthap"+adaptador);
                // lstCompras.setAdapter(adaptador);
            }
            else {
                System.out.println("mierda no me sale");
            }
        }
    }
}
