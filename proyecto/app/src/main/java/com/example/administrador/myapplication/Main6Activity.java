package com.example.administrador.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Main6Activity extends AppCompatActivity {
    Button bOrder;

    private static final String TAG = "ShoppingCartActivity";

    ListView lvCartItems;
    Button bClear;
    Button bShop;
    TextView tvTotalPrice;
    private TextView titulo,medio,info,nom;
    private ImageView qwe;
    private ImageView qw;
    Producto product;
    private EditText cant;

    private String urlServer = "http://cybertrom.000webhostapp.com/";
    private List<CardItem> listaComprado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        recibirdatos();
        bShop=(Button)findViewById(R.id.bShop);
        cant=(EditText)findViewById(R.id.editText);
        nom=(TextView)findViewById(R.id.textview1);
      info=(TextView) findViewById(R.id.textview3);
        qw= (ImageView)findViewById(R.id.imageview);

        bShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String r =""+cant.getText();
                String p=""+info.getText();
                String z=""+nom.getText();
                //Log.d(TAG, "Adding product: " + product.getNombre());
                // cart.add(product, Integer.valueOf(spQuantity.getSelectedItem().toString()));
                Intent intent = new Intent(Main6Activity.this, ShoppingCartActivity.class);
                intent.putExtra("v1",r);
                intent.putExtra("v2",p);
                intent.putExtra("v3",z);
                startActivity(intent);
            }
        });

    }




    public void recibirdatos(){
        Bundle extras = getIntent().getExtras();
        String a = extras.getString("v1");
        String b = extras.getString("v2");
        String c = extras.getString("v3");
        String d = extras.getString("v4");

        titulo = (TextView) findViewById(R.id.textview1);
        medio = (TextView) findViewById(R.id.textview2);
        info = (TextView) findViewById(R.id.textview3);
        qw= (ImageView)findViewById(R.id.imageview);
        Picasso.with(getApplicationContext()).load(urlServer+"imagenes/"+d).into(qw);

        titulo.setText(a);
        medio.setText(b);
        info.setText(c);

        //qwe.setImageBitmap(decodedByte);

    }



}
