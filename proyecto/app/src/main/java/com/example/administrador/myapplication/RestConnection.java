package com.example.administrador.myapplication;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RestConnection {
    private String urlApi, metodoHTTP;
    private HttpURLConnection connection = null;

    public RestConnection(String urlApi, String metodoHTTP) {
        this.urlApi = urlApi;
        this.metodoHTTP = metodoHTTP;
        this.getConnection();
    }

    public void getConnection(){
        try {
            //Connection to URL
            URL url = new URL(urlApi);
            connection = (HttpURLConnection) url.openConnection();
            if(this.metodoHTTP.equals("POST")){
                connection.setRequestMethod(metodoHTTP);
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setUseCaches(false);
            }
            connection.connect();
        } catch(Exception ex) {
            Log.e("Connection","Error!", ex);
        }
    }

    public JSONObject sendData(JSONObject datos){
        try {
            // Send POST output.
            byte[] outputInBytes = datos.toString().getBytes("UTF-8");
            DataOutputStream printout = new DataOutputStream(connection.getOutputStream());
            printout.write(outputInBytes);
            printout.flush();
            printout.close();
        } catch(Exception ex) {
            Log.e("ServicioRest","Error!", ex);
        }
        return getData();
    }

    public JSONObject getData(){
        JSONObject parentObject = null;
        try {
            //GEt Post response
            InputStream stream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();
            String line ="";
            while ((line = reader.readLine()) != null){
                buffer.append(line);
            }
            String finalJson = buffer.toString();
            parentObject = new JSONObject(finalJson);
        } catch(Exception ex) {
            Log.e("InputStream","Error!", ex);
        }
        return parentObject;
    }

}

