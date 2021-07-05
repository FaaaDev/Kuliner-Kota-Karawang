package com.example.kulinerkotakarawang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kulinerkotakarawang.http.PostHandler;
import com.example.kulinerkotakarawang.http.Preferences;
import com.example.kulinerkotakarawang.http.ReqProperty;
import com.example.kulinerkotakarawang.model.LapakModel;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Login extends AppCompatActivity {

    private TextView goregist;
    private EditText nohp, password;
    private ConstraintLayout container;
    private Button login_action;
    private String user, pass;
    private String url, message;
    private ReqProperty rp = new ReqProperty();
    private String nama, no, alamat, wilayah, pwd, gambar;
    private LapakModel lm = new LapakModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

        _init();
        _prep();

    }

    private void _init(){
        goregist = findViewById(R.id.goregist);
        nohp = findViewById(R.id.nohp);
        password = findViewById(R.id.password);
        container = findViewById(R.id.container);
        login_action = findViewById(R.id.login_action);
    }

    private void _prep(){
        goregist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Registrasi.class));
                finish();
            }
        });

        login_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(nohp.getText()) || TextUtils.isEmpty(password.getText())){
                    if (TextUtils.isEmpty(nohp.getText()) ){
                        nohp.setError("Nomor HP harus diisi");
                        nohp.requestFocus();
                    }
                    if (TextUtils.isEmpty(password.getText()) ){
                        password.setError("Password harus diisi");
                        password.requestFocus();
                    }
                } else {
                    login_action.setEnabled(false);
                    new Auth().execute();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Login.this, MainActivity.class));
        finish();
    }

    private class Auth extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Snackbar snackbar = Snackbar
                    .make(container, "Memproses Permintaan", Snackbar.LENGTH_INDEFINITE);
            snackbar.show();

            url = rp.getBASE_URL()+"login.php";

            user = nohp.getText().toString();
            pass = password.getText().toString();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            PostHandler sh = new PostHandler();

            JSONObject data = new JSONObject();
            try {
                data.put("nohp", user);
                data.put("pass", pass);

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
                    nama = jsonObj.getString("nama");
                    no = jsonObj.getString("nohp");
                    pwd = jsonObj.getString("pass");
                    wilayah = jsonObj.getString("wilayah");
                    alamat = jsonObj.getString("alamat");
                    gambar = jsonObj.getString("gambar");

                    //System.out.println("MESSAGE : "+message);
                } catch (final JSONException e) {

                }
            } else {
                Log.e("ERROR : ", "Couldn't get json from server.");

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Snackbar snackbar = Snackbar
                                .make(container, "Gagal Menghubungi Server, Cobalagi!", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if ("true".equals(message)){
                Snackbar snackbar = Snackbar
                        .make(container, "Berhasil Masuk Ke Lapak", Snackbar.LENGTH_LONG);
                snackbar.show();

                Preferences.setRegisteredUser(Login.this, no);
                Preferences.setRegisteredPass(Login.this, pass);
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
                        onBackPressed();
                    }
                }, 500);
            } else {
                Snackbar snackbar = Snackbar
                        .make(container, "Gagak Masuk Ke Lapak, Cobalagi!", Snackbar.LENGTH_LONG);
                snackbar.show();
                login_action.setEnabled(true);
            }
            //Toast.makeText(CreateActivity.this, message, Toast.LENGTH_LONG).show();
        }
    }
}
