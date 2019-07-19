package com.example.administrador.myapplication;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity
    {
        public List<Producto> artefactos;

        private Toolbar toolbar;
    private Button liscel;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


    }





    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case android.R.id.home:
                //
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void irlog(View view) {
        Intent i = new Intent(Home.this, LoginActivity.class);
        startActivity(i);
    }
    public void irall(View view) {
            Intent i = new Intent(Home.this, PrincipalActivity.class);
            startActivity(i);
        System.out.println("no lo hace");
        }

  /*
   /* public void inicio(View view) {
        Intent i=new Intent(Home.this,Home.class);
        startActivity(i);
    }
    public void cuenta(View view) {
        Intent i=new Intent(Home.this,RegisterActivity.class);
        startActivity(i);
    }*/


    /*public  boolean onNavigationItenSelected(MenuItem item){


    }*/


    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


        public void irhistorial(View view) {
            Intent i = new Intent(Home.this, Compras.class);
            startActivity(i);
        }

        public void irSugerencia(View view) {
            Intent i = new Intent(Home.this, SugerenciaActivity.class);
            startActivity(i);
        }

        public void irContacto(View view) {
            Intent i = new Intent(Home.this, Contacto.class);
            startActivity(i);
        }
    }

