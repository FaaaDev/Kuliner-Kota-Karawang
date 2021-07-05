package com.example.kulinerkotakarawang;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.kulinerkotakarawang.http.PostHandler;
import com.example.kulinerkotakarawang.http.Preferences;
import com.example.kulinerkotakarawang.http.ReqProperty;
import com.example.kulinerkotakarawang.model.LapakModel;
import com.example.kulinerkotakarawang.ui.dashboard.DashboardFragment;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import static java.security.AccessController.getContext;

public class AddActivity extends AppCompatActivity {

    private ImageButton add, caim;
    private ImageView img;
    private ConstraintLayout container;
    private EditText menuname, price, desc;
    private TextView start, end;
    private Button submit;
    private Uri uri;
    private String nama, harga, deskripsi, operasional, nohp, wilayah, alamat, gambar, starttime, endtime;
    private String shour, sminute, ehour, eminute;
    private String check = "";
    private String cameraFilePath, url, message, imageurl;
    private ReqProperty rp = new ReqProperty();
    private LapakModel lm = new LapakModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

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
            AddActivity.this.getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        _init();
        _prep();

    }

    public void _init(){
        add = findViewById(R.id.add);
        caim = findViewById(R.id.caim);
        img = findViewById(R.id.img);
        submit = findViewById(R.id.submit);
        menuname = findViewById(R.id.menuname);
        price = findViewById(R.id.price);
        desc = findViewById(R.id.desc);
        start = findViewById(R.id.start);
        end = findViewById(R.id.end);
        container = findViewById(R.id.container);
    }

    public void _prep(){
        caim.setVisibility(View.GONE);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(AddActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                        ActivityCompat.checkSelfPermission(AddActivity.this,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                        ActivityCompat.checkSelfPermission(AddActivity.this,
                                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(AddActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.CAMERA},
                            1);

                } else {
                    try {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(AddActivity.this, "com.example.kulinerkotakarawang", createImageFile()));
                        startActivityForResult(intent, 1);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        caim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img.setImageURI(null);
                uri = null;
                caim.setVisibility(View.GONE);
                add.setVisibility(View.VISIBLE);
                check = "";
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uri==null){
                    Toast.makeText(AddActivity.this, "Tambahkan Foto terlebih dahulu", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(menuname.getText()) || TextUtils.isEmpty(price.getText())
                        || TextUtils.isEmpty(desc.getText())){
                    Toast.makeText(AddActivity.this, "Lengkapi data terlebih dahulu", Toast.LENGTH_LONG).show();
                } else {
                    submit.setEnabled(false);
                    new UploadImage().execute();
                }
                //System.out.println(uri.toString());
            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                final int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if (selectedHour < 10 || selectedMinute < 10) {
                            if (selectedMinute < 10) {
                                sminute = "0" + selectedMinute;
                            } else {
                                sminute = String.valueOf(selectedMinute);
                            }
                            if (selectedHour < 10) {
                                shour = "0" + selectedHour;
                            } else {
                                shour = String.valueOf(selectedHour);
                            }
                            start.setText(shour + ":" + sminute);
                            starttime = shour + ":" + sminute;
                        } else {
                            start.setText(selectedHour + ":" + selectedMinute);
                            starttime = selectedHour + ":" + selectedMinute;
                        }
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                final int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if (selectedHour < 10 || selectedMinute < 10) {
                            if (selectedMinute < 10) {
                                eminute = "0" + selectedMinute;
                            } else {
                                eminute = String.valueOf(selectedMinute);
                            }
                            if (selectedHour < 10) {
                                ehour = "0" + selectedHour;
                            } else {
                                ehour = String.valueOf(selectedHour);
                            }
                            end.setText(ehour + ":" + eminute);
                            endtime = ehour + ":" + eminute;
                        } else {
                            end.setText(selectedHour + ":" + selectedMinute);
                            endtime = selectedHour + ":" + selectedMinute;
                        }
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        //This is the directory in which the file will be created. This is the default location of Camera photos
        File storageDir = this.getExternalFilesDir(Environment.DIRECTORY_DCIM);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for using again
        cameraFilePath = "file://" + image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case 1:
                    uri = Uri.parse(cameraFilePath);
                    img.setImageURI(uri);
                    caim.setVisibility(View.VISIBLE);
                    add.setVisibility(View.GONE);
                    check = "ok";
                    break;
            }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private class UploadImage extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Snackbar snackbar = Snackbar
                    .make(container, "Mengupload Gambar", Snackbar.LENGTH_INDEFINITE);
            snackbar.show();

            url = rp.getBASE_URL() + "upload.php";

        }

        @Override
        protected Void doInBackground(Void... voids) {
            PostHandler sh = new PostHandler();


            String jsonStr = sh.uploadFile(url, uri, AddActivity.this);

            System.out.println("RESPONSE : "+jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    message = jsonObj.getString("status");
                    imageurl = jsonObj.getString("url");
                } catch (final JSONException e) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Snackbar snackbar = Snackbar
                                    .make(container, "Gagal Menghubungi Server!", Snackbar.LENGTH_LONG);
                            snackbar.show();
                            submit.setEnabled(true);
                        }
                    });

                }
            } else {
                Log.e("ERROR : ", "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Snackbar snackbar = Snackbar
                                .make(container, "Gagal Menghubungi Server!", Snackbar.LENGTH_LONG);
                        snackbar.show();
                        submit.setEnabled(true);
                    }
                });

            }
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if ("success".equals(message)){
                gambar = imageurl;
                Snackbar snackbar = Snackbar
                        .make(container, "Berhasil Mengupload Gambar", Snackbar.LENGTH_LONG);
                snackbar.show();
                new AddMenu().execute();
            } else {
                Snackbar snackbar = Snackbar
                        .make(container, "Gagal Mengupload Gambar", Snackbar.LENGTH_LONG);
                snackbar.show();
                submit.setEnabled(true);
            }

        }
    }

    private class AddMenu extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Snackbar snackbar = Snackbar
                    .make(container, "Menyimpan Data", Snackbar.LENGTH_INDEFINITE);
            snackbar.show();

            url = rp.getBASE_URL()+"addMenu.php";

            nama = menuname.getText().toString();
            nohp = lm.getNohp();
            harga = price.getText().toString();
            alamat = lm.getAlamat();
            wilayah = lm.getWilayah();
            deskripsi = desc.getText().toString();
            operasional = starttime+"-"+endtime;

        }

        @Override
        protected Void doInBackground(Void... voids) {
            PostHandler sh = new PostHandler();

            JSONObject data = new JSONObject();
            try {
                data.put("nama_produk", nama);
                data.put("harga", harga);
                data.put("nohp", nohp);
                data.put("desk", deskripsi);
                data.put("operasional", operasional);
                data.put("alamat", alamat);
                data.put("wilayah", wilayah);
                data.put("gambar", gambar);


                System.out.println("DATA : "+data.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }

            String jsonStr = sh.makeServiceCall(url, data);
            System.out.println("JSONSTR : "+jsonStr);
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
                        submit.setEnabled(true);
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
                        .make(container, "Berhasil Menambahkan Menu", Snackbar.LENGTH_LONG);
                snackbar.show();

                onBackPressed();

            } else {
                Snackbar snackbar = Snackbar
                        .make(container, "Gagal Menambah Menu, Cobalagi!", Snackbar.LENGTH_LONG);
                snackbar.show();
                submit.setEnabled(true);

            }

            //Toast.makeText(CreateActivity.this, message, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}