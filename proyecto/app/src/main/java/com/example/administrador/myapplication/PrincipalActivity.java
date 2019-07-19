package com.example.administrador.myapplication;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import android.view.View;
import android.view.ViewGroup;

public class PrincipalActivity extends AppCompatActivity {
    private ListView lstProductos;
    private Spinner cmbCategoria;
    List<Producto> items;
    private String urlServer = "http://cybertrom.000webhostapp.com/";

    ImageView dardo ;
    TextView cont;
    private ProgressDialog loading = null;
  
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        loading = new ProgressDialog(this);
        loading.setCancelable(true);
        loading.setMessage("Cargando...");

        cmbCategoria = (Spinner)findViewById(R.id.cmbCategoria);
        //un ejemplo
        lstProductos = (ListView)findViewById(R.id.lstDatos);

        ///asdadsada
        dardo=(ImageView)findViewById(R.id.acessorio_dardo);
        cont=(TextView) findViewById(R.id.textView_medio);
        listarCategorias();
        listarProductos("0");

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
  /*  public void imageClick(View view) {
        //Implement image click function
        System.out.println("hola");


    }*/
    //    public View getView(int position, View convertView, ViewGroup parent) {
//        Producto item = items.get(position);
//
//        if(convertView==null)
//            convertView = inflater.inflate(R.layout.lista_productos, null);
//
//
//
//        txtCenter.setText("cantidad "+ item.getCantidad()+1);
//        return convertView;
//    }
    public void listarCategorias(){
        TareaWSListarCategorias tarea = new TareaWSListarCategorias();
        tarea.execute();
    }

    public void listarProductos(String id){
        TareaWSListarProductos tarea = new TareaWSListarProductos();
        tarea.execute(id);
    }

    //Tarea AsÃ­ncrona para llamar al WS de listado en segundo plano
    private class TareaWSListarCategorias extends AsyncTask<String,Integer,Boolean> {

        private List<Categoria> listaCategorias;

        protected void onPreExecute() {
            super.onPreExecute();
            loading.show();
        }

        protected Boolean doInBackground(String... params) {

            String urlApiFinal = Constantes.URL_SERVER_API+"categoria/listar";
            boolean resul = true;

            try {
                RestConnection restConnection = new RestConnection(urlApiFinal,"GET");
                JSONObject parentObject = restConnection.getData();
                JSONArray parentArray = parentObject.getJSONArray("categorias");
                listaCategorias = new ArrayList<Categoria>();
                listaCategorias.add(new Categoria(0,"Listar todo"));
                for(int i=0; i<parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    int id = finalObject.getInt("id");
                    String nombre = finalObject.getString("nombre");
                    listaCategorias.add(new Categoria(id,nombre));
                }
            } catch(Exception ex) {
                Log.e("ServicioRest","Error!", ex);
                resul = false;
            }
            return resul;
        }

        protected void onPostExecute(Boolean result) {
            if (result) {
                loading.dismiss();
                ArrayAdapter spinnerAdapter = new ArrayAdapter(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, listaCategorias);
                cmbCategoria.setAdapter(spinnerAdapter);
                cmbCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Categoria item = (Categoria)parent.getItemAtPosition(position);
                        listarProductos(Integer.toString(item.getId()));
                    }
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        }
    }

    //Tarea AsÃ­ncrona para llamar al WS de listado en segundo plano
    private class TareaWSListarProductos extends AsyncTask<String,Integer,Boolean> {

        private List<Producto> listaProductos;

        protected Boolean doInBackground(String... params) {
            String urlApiFinal = Constantes.URL_SERVER_API+"producto/listar/"+params[0];
            boolean resul = true;

            try {
                RestConnection restConnection = new RestConnection(urlApiFinal,"GET");
                JSONObject parentObject = restConnection.getData();
                JSONArray parentArray = parentObject.getJSONArray("productos");
                listaProductos = new ArrayList<Producto>();
                for(int i=0; i<parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    int id = finalObject.getInt("id");
                    String nombre = finalObject.getString("nombre");
                    Double precio = finalObject.getDouble("precio");
                    String imagen = finalObject.getString("imagen");
                    int stock = finalObject.getInt("stock");
                    int cantidad=finalObject.getInt("cantidad");
                    int idcategoria = finalObject.getInt("categoria_id");
                    listaProductos.add(new Producto(id,nombre,precio,imagen,stock,idcategoria,cantidad));
                //    System.out.println("hello world:  "+listaProductos.get(i));


                }
            } catch(Exception ex) {
                Log.e("ServicioRest","Error!", ex);
                resul = false;
            }
            return resul;
        }

        protected void onPostExecute(Boolean result) {
            if (result) {
                ProductoListViewAdapter adaptador = new ProductoListViewAdapter(PrincipalActivity.this, listaProductos);
                lstProductos.setAdapter(adaptador);
                lstProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Producto elegido = (Producto) parent.getItemAtPosition(position);
                        CharSequence texto = "Seleccionado: " + elegido.getNombre();

                        String a =elegido.getNombre()+"";
                        int b=elegido.getCantidad();
                        String b1=""+b;

                        Double d=elegido.getPrecio();
                        String c=""+d;
                       String q=elegido.getImagen();
                        Intent i = new Intent( PrincipalActivity.this,Main6Activity.class);
                        i.putExtra("v1",a);
                        i.putExtra("v2",b1);
                        i.putExtra("v3",c);
                        i.putExtra("v4",q);
                        System.out.println("mierda");
                        startActivity(i);

                    }
                });
            }
        }
    }
}
