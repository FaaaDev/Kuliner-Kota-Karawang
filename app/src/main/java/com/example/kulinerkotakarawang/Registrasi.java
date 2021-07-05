package com.example.kulinerkotakarawang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kulinerkotakarawang.http.PostHandler;
import com.example.kulinerkotakarawang.http.Preferences;
import com.example.kulinerkotakarawang.http.ReqProperty;
import com.example.kulinerkotakarawang.model.LapakModel;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Registrasi extends AppCompatActivity {

    private ConstraintLayout container;
    private TextView gologin;
    private EditText lapakname, phone, address, passwd, repass;
    private Button regist_action;
    private Spinner spinner_wilayah;
    private String nama, nohp, alamat, wilayah, pass, gambar;
    private String url, message;
    private ReqProperty rp = new ReqProperty();
    private LapakModel lm = new LapakModel();
    ArrayList<String> list_wilayah = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);

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
        container = findViewById(R.id.container);
        gologin = findViewById(R.id.gologin);
        spinner_wilayah = findViewById(R.id.spinner_wilayah);
        phone = findViewById(R.id.phone);
        passwd = findViewById(R.id.passwd);
        lapakname = findViewById(R.id.lapakname);
        address = findViewById(R.id.address);
        repass = findViewById(R.id.repass);
        regist_action = findViewById(R.id.regist_action);
    }

    private void _prep(){
        gologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registrasi.this, Login.class));
                finish();
            }
        });

        list_wilayah.add("Pilih Wilayah");
        list_wilayah.add("Cikampek");
        list_wilayah.add("Cilamaya");
        list_wilayah.add("Jatisari");
        list_wilayah.add("Klari");
        list_wilayah.add("Kosambi");
        list_wilayah.add("Pancawati");
        list_wilayah.add("Purwasari");
        list_wilayah.add("Teluk Jambe");

        adapter = new ArrayAdapter<>(this, R.layout.spinner_layout, R.id.txt_item, list_wilayah);
        spinner_wilayah.setAdapter(adapter);

        regist_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(lapakname.getText()) || TextUtils.isEmpty(phone.getText()) ||
                        TextUtils.isEmpty(address.getText()) || TextUtils.isEmpty(passwd.getText()) ||
                        TextUtils.isEmpty(repass.getText()) ||  "Pilih Wilayah".equals(spinner_wilayah.getSelectedItem())) {

                    if (TextUtils.isEmpty(lapakname.getText())){
                        lapakname.setError("Silahkan isi nama lapak");
                        lapakname.requestFocus();
                    }

                    if (TextUtils.isEmpty(phone.getText())){
                        phone.setError("Silahkan isi nomor handphone");
                        phone.requestFocus();
                    }

                    if ("Pilih Wilayah".equals(spinner_wilayah.getSelectedItem())){
                        Toast.makeText(Registrasi.this, "Pilih wilayah terlebih dahulu", Toast.LENGTH_LONG).show();
                    }

                    if (TextUtils.isEmpty(address.getText())){
                        address.setError("Silahkan isi alamat lengkap");
                        address.requestFocus();
                    }

                    if (TextUtils.isEmpty(passwd.getText())){
                        passwd.setError("Password tidak boleh kosong");
                        passwd.requestFocus();
                    }

                    if (TextUtils.isEmpty(repass.getText())){
                        repass.setError("Ketik ulang password");
                        repass.requestFocus();
                    }
                } else if (!repass.getText().toString().equals(passwd.getText().toString())){
                    repass.setText("");
                    repass.setError("Password tidak sama");
                    repass.requestFocus();
                } else {
                    regist_action.setEnabled(false);
                    new Regist().execute();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Registrasi.this, MainActivity.class));
        finish();
    }

    private class Regist extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Snackbar snackbar = Snackbar
                    .make(container, "Memproses permintaan", Snackbar.LENGTH_INDEFINITE);
            snackbar.show();

            url = rp.getBASE_URL()+"register.php";

            nama = lapakname.getText().toString();
            nohp = phone.getText().toString();
            pass = passwd.getText().toString();
            alamat = address.getText().toString();
            wilayah = spinner_wilayah.getSelectedItem().toString();
            pass = passwd.getText().toString();
            gambar = "";
        }

        @Override
        protected Void doInBackground(Void... voids) {
            PostHandler sh = new PostHandler();

            JSONObject data = new JSONObject();
            try {
                data.put("nama", nama);
                data.put("nohp", nohp);
                data.put("alamat", alamat);
                data.put("wilayah", wilayah);
                data.put("pass", pass);
                data.put("gambar", gambar);


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
                    message = jsonObj.getString("message");
                    //System.out.println("MESSAGE : "+message);
                } catch (final JSONException e) {

                }
            } else {
                Log.e("ERROR : ", "Couldn't get json from server.");

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Snackbar snackbar = Snackbar
                                .make(container, "Gagal Menghubungi Server!", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if ("success".equals(message)){
                Snackbar snackbar = Snackbar
                        .make(container, "Berhasil Membuka Lapak", Snackbar.LENGTH_LONG);
                snackbar.show();
                Preferences.setRegisteredUser(Registrasi.this, nohp);
                Preferences.setRegisteredPass(Registrasi.this, pass);
                lm.setNama(nama);
                lm.setNohp(nohp);
                lm.setWilayah(wilayah);
                lm.setAlamat(alamat);
                lm.setPass(pass);
                lm.setGambar(gambar);

                onBackPressed();

            } else {
                Snackbar snackbar = Snackbar
                        .make(container, "Gagal Membuat Lapak, Cobalagi!", Snackbar.LENGTH_LONG);
                snackbar.show();
                regist_action.setEnabled(true);
            }

            //Toast.makeText(CreateActivity.this, message, Toast.LENGTH_LONG).show();
        }
    }
}
