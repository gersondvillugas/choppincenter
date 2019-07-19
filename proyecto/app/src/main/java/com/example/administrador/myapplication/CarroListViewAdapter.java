package com.example.administrador.myapplication;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

class CarroListViewAdapter extends BaseAdapter {
    LayoutInflater inflater;
    List<CardItem> items;
    private Context mContext;
    ImageView dardo ;


    private String urlServer = "http://cybertrom.000webhostapp.com/";
    public CarroListViewAdapter(Activity context, List<CardItem> items) {
        super();
        this.mContext = context;
        this.items = items;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CardItem item = items.get(position);

        if(convertView==null)
            convertView = inflater.inflate(R.layout.lista_carrito, null);

      //  ImageView imgThumbnail = (ImageView)convertView.findViewById(R.id.imageView_imagen);
        TextView txtTitle = (TextView)convertView.findViewById(R.id.textView_nombre);
        TextView txtSubtitle = (TextView)convertView.findViewById(R.id.textView_cantidad);
        TextView txtCenter=(TextView)convertView.findViewById(R.id.textView_precio);
        TextView txtMidel=(TextView)convertView.findViewById(R.id.textView_total);


        //  Picasso.with(mContext).load(urlServer+"imagenes/"+item.getImagen()).into(imgThumbnail);
        txtTitle.setText(item.getNombre());
        txtMidel.setText("total:"+item.getTotal());
        txtSubtitle.setText("S/"+item.getPrecio());
        txtCenter.setText("cantidad "+ item.getCantidad());
        return convertView;
    }
}