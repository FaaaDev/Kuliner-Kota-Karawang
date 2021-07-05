package com.example.kulinerkotakarawang;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.kulinerkotakarawang.http.PostHandler;
import com.example.kulinerkotakarawang.http.Preferences;
import com.example.kulinerkotakarawang.http.ReqProperty;
import com.example.kulinerkotakarawang.model.DetailModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BottomDetail extends BottomSheetDialogFragment {

    private Button order;
    private TextView menuname, operasional, price, desc, lapakname, address;
    private ImageView imgmenu;
    private String url, message;
    private ReqProperty rp = new ReqProperty();
    private String nama;
    private DetailModel dm = new DetailModel();
    private LinearLayout after, loading;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.bottom_detail, container, false);

        _init(root);
        _prep();

        return root;
    }

    private void _init(View root){
        order = root.findViewById(R.id.order);
        menuname = root.findViewById(R.id.menuname);
        operasional = root.findViewById(R.id.operasional);
        price = root.findViewById(R.id.price);
        desc = root.findViewById(R.id.desc);
        lapakname = root.findViewById(R.id.lapakname);
        address = root.findViewById(R.id.address);
        imgmenu = root.findViewById(R.id.imgmenu);
        after = root.findViewById(R.id.after);
        loading = root.findViewById(R.id.loading);
    }

    private void _prep(){
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomOrder bottomOrder = new BottomOrder();
                bottomOrder.show(getParentFragmentManager(), bottomOrder.getTag());
                dismiss();
            }
        });

        new GetUser().execute();
    }

    private class GetUser extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            url = rp.getBASE_URL()+"getuserdata.php";

        }

        @Override
        protected Void doInBackground(Void... voids) {
            PostHandler sh = new PostHandler();

            JSONObject data = new JSONObject();
            try {
                data.put("nohp", dm.getNohp());

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
                    }

                    //System.out.println("MESSAGE : "+message);
                } catch (final JSONException e) {

                }
            } else {
                Log.e("ERROR : ", "Couldn't get json from server.");

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if ("true".equals(message)){
                loading.setVisibility(View.GONE);
                after.setVisibility(View.VISIBLE);
                menuname.setText(dm.getNama());
                operasional.setText("Jam "+dm.getOperasional());
                price.setText("Rp. "+dm.getHarga());
                address.setText(dm.getAlamat());
                desc.setText(dm.getDeskripsi());
                lapakname.setText(nama);
                Glide.with(getContext())
                        .load(dm.getGambar())
                        .into(imgmenu);
            } else {
                Toast.makeText(getContext(), "Gagal mendapatkan data", Toast.LENGTH_LONG);
            }
            //Toast.makeText(CreateActivity.this, message, Toast.LENGTH_LONG).show();
        }
    }
}
