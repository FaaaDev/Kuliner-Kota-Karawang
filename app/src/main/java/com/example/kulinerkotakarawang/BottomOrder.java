package com.example.kulinerkotakarawang;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.kulinerkotakarawang.http.SendWhatsapp;
import com.example.kulinerkotakarawang.model.DetailModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class BottomOrder extends BottomSheetDialogFragment {

    private EditText nama, jumlah, alamat;
    private TextView total;
    private Button proses;
    private DetailModel dm = new DetailModel();
    private int ammount = 0;
    private SendWhatsapp send = new SendWhatsapp();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.bottom_order, container, false);

        _init(root);
        _prep();

        return root;
    }

    private void _init(View root){
        nama = root.findViewById(R.id.nama);
        jumlah = root.findViewById(R.id.jumlah);
        alamat = root.findViewById(R.id.alamat);
        total = root.findViewById(R.id.total);
        proses = root.findViewById(R.id.proses);
    }

    private void _prep(){
        ammount = Integer.parseInt(String.valueOf(dm.getHarga())) * Integer.parseInt(jumlah.getText().toString());
        total.setText("RP. "+ammount);
        jumlah.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s!=null){
                    if (TextUtils.isEmpty(s)){

                    }
                    ammount = Integer.parseInt(String.valueOf(dm.getHarga())) * (s.toString().equals("") ? 0 : Integer.parseInt(String.valueOf(s)));

                    //Toast.makeText(getContext(), "TOTAL : "+ammount, Toast.LENGTH_LONG).show();

                    total.setText("Rp. "+ammount);
                }
            }
        });

        proses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(nama.getText()) || ammount==0 || TextUtils.isEmpty(alamat.getText())){
                    if (TextUtils.isEmpty(nama.getText())){
                        nama.setError("Nama Harus Diisi");
                    }
                    if (ammount==0){
                        jumlah.setError("Jumlah tidak boleh kosong");
                    }
                    if (TextUtils.isEmpty(alamat.getText())){
                        alamat.setError("Alamat Harus Diisi");
                    }
                } else {
                    String urlencodedMessage = null;
                    try {
                        urlencodedMessage = URLEncoder.encode(
                                "Accept Order :\n" +
                                        "Menu : "+dm.getNama() +"\n"+
                                        "Jumlah : "+ jumlah.getText().toString() +"\n"+
                                        "Total : Rp. "+ammount+"\n\n"+
                                        "Detail Pengorder :\n" +
                                        "- Nama : "+nama.getText().toString()+"\n" +
                                        "- Alamat :\n"+alamat.getText().toString(), StandardCharsets.UTF_8.toString());
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    send.whatsapp(getActivity(), dm.getNohp().replace("08", "628"), urlencodedMessage);
                }
            }
        });
    }
}
