package com.example.administrador.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Contacto extends AppCompatActivity {
  // Button  bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);
  //     bt=(Button)findViewById(R.id.btnContactar);
    }
    public void onClickLlamada(View v) {
        System.out.println("dando  click");

        Intent i = new Intent(Intent.ACTION_DIAL);
        i.setData(Uri.parse("tel:933074339"));

      /*  if (ActivityCompat.checkSelfPermission(Contacto.this, Manifest.permission.CALL_PHONE)!=
                PackageManager.PERMISSION_GRANTED
        )
            return;*/
        System.out.println("dando  click2");
      //  i.setData(Uri.parse("tel:960944981"));
        startActivity(i);
    }
}
