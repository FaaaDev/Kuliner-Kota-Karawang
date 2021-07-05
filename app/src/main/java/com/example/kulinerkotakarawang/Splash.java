package com.example.kulinerkotakarawang;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.kulinerkotakarawang.http.PostHandler;
import com.example.kulinerkotakarawang.http.Preferences;
import com.example.kulinerkotakarawang.http.ReqProperty;
import com.example.kulinerkotakarawang.model.LapakModel;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Splash extends AppCompatActivity {

    private int waktu_loading=2000;
    private String user, pass;
    private String url, message;
    private ReqProperty rp = new ReqProperty();
    private String nama, no, alamat, wilayah, pwd, gambar;
    private LapakModel lm = new LapakModel();
    private ConstraintLayout container;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View main = findViewById(R.id.container);
            int flags = main.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                flags |= View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
                this.getWindow().setNavigationBarColor(Color.WHITE);
            }
            main.setSystemUiVisibility(flags);
            this.getWindow().setStatusBarColor(Color.WHITE);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        _prep();

    }

    private void _prep(){
        container = findViewById(R.id.container);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(Splash.this, MainActivity.class));
                finish();
            }
        },waktu_loading);

        if (TextUtils.isEmpty(Preferences.getRegisteredUser(this))){

        } else {
            //new GetUser().execute();
        }
    }

    private class GetUser extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            url = rp.getBASE_URL()+"getuserdata.php";

            user = Preferences.getRegisteredUser(Splash.this);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            PostHandler sh = new PostHandler();

            JSONObject data = new JSONObject();
            try {
                data.put("nohp", user);

                //System.out.println("DATA : "+data.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }

            String jsonStr = sh.makeServiceCall(url, data);
            //System.out.println("JSONSTR : "+jsonStr);
            //Toast.makeText(CreateActivity.this, "JSONSTR : "+jsonStr, Toast.LENGTH_LONG).show();


            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    message = jsonObj.getString("status");
                    JSONArray u = jsonObj.getJSONArray("result");

                    for (int i = 0; i < u.length(); i++){
                        JSONObject o = u.getJSONObject(i);
                        nama = o.getString("nama");
                        no = o.getString("nohp");
                        pwd = o.getString("pass");
                        wilayah = o.getString("wilayah");
                        alamat = o.getString("alamat");
                        gambar = o.getString("gambar");
                    }

                    //System.out.println("MESSAGE : "+message);
                } catch (final JSONException e) {

                }
            } else {
                Log.e("ERROR : ", "Couldn't get json from server.");

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Handler hh = new Handler();
                        hh.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(Splash.this, MainActivity.class));
                                finish();
                            }
                        }, 1500);
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if ("true".equals(message)){

                lm.setNama(nama);
                lm.setNohp(no);
                lm.setWilayah(wilayah);
                lm.setAlamat(alamat);
                lm.setPass(pwd);
                lm.setGambar(gambar);

                Handler hh = new Handler();
                hh.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(Splash.this, MainActivity.class));
                        finish();
                    }
                }, 1500);
            } else {
                Handler hh = new Handler();
                hh.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(Splash.this, MainActivity.class));
                        finish();
                    }
                }, 1500);
            }
            //Toast.makeText(CreateActivity.this, message, Toast.LENGTH_LONG).show();
        }
    }
}

