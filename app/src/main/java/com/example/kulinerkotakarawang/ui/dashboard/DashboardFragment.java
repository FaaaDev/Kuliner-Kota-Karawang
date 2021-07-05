package com.example.kulinerkotakarawang.ui.dashboard;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kulinerkotakarawang.AddActivity;
import com.example.kulinerkotakarawang.Login;
import com.example.kulinerkotakarawang.MainActivity;
import com.example.kulinerkotakarawang.R;
import com.example.kulinerkotakarawang.Registrasi;
import com.example.kulinerkotakarawang.Splash;
import com.example.kulinerkotakarawang.adapter.ItemClickListener;
import com.example.kulinerkotakarawang.adapter.MenuAdapter;
import com.example.kulinerkotakarawang.http.PostHandler;
import com.example.kulinerkotakarawang.http.Preferences;
import com.example.kulinerkotakarawang.http.ReqProperty;
import com.example.kulinerkotakarawang.model.LapakModel;
import com.example.kulinerkotakarawang.model.MenuModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private Button regist, login;
    private FloatingActionButton add_menu;
    private LinearLayout before, after, loading, empty, notempty;
    private LapakModel lm = new LapakModel();
    private String url, message;
    private ReqProperty rp = new ReqProperty();
    private String nohp, jsonStr;
    private List<MenuModel> mm;
    private RecyclerView rv_menu;
    private MenuAdapter ma;
    private LinearLayoutManager llm;
    private ItemClickListener itemClickListener;
    private String id;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tambahprod, container, false);

        _init(root);
        _prep();

        return root;
    }

    private void _init(View root){
        regist = root.findViewById(R.id.regist);
        login = root.findViewById(R.id.login);
        before = root.findViewById(R.id.before);
        after = root.findViewById(R.id.after);
        add_menu = root.findViewById(R.id.fab_add);
        rv_menu = root.findViewById(R.id.rv_menu);
        loading = root.findViewById(R.id.loading);
        empty = root.findViewById(R.id.empty);
        notempty = root.findViewById(R.id.notempty);
    }

    private void _prep(){
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Registrasi.class));
                getActivity().finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Login.class));
                getActivity().finish();
            }
        });

        add_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddActivity.class));
            }
        });

        itemClickListener = new ItemClickListener() {
            @Override
            public void onItemClicked(String params) {
                deleteMenu(params);
            }
        };

    }

    private void deleteMenu(String id){
        this.id = id;

        new DeleteMenu().execute();
    }

    private class GetMenu extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            url = rp.getBASE_URL()+"menu.php";

            nohp = lm.getNohp();

            mm = new ArrayList<>();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            PostHandler sh = new PostHandler();

            JSONObject data = new JSONObject();
            try {
                data.put("nohp", nohp);

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
                        String id = o.getString("id_menu");
                        String name = o.getString("nama_produk");
                        String harga = o.getString("harga");
                        String nohp = o.getString("nohp");
                        String desk  = o.getString("desk");
                        String operasional = o.getString("operasional");
                        String alamat = o.getString("alamat");
                        String wilayah = o.getString("wilayah");
                        String gambar = o.getString("gambar");

                        mm.add(new MenuModel(id, name, harga, nohp, desk, operasional, alamat, wilayah, gambar));
                    }

                    //System.out.println("MESSAGE : "+message);
                } catch (final JSONException e) {

                }
            } else {
                Log.e("ERROR : ", "Couldn't get json from server.");

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "Periksa Koneksi Internet!", Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if ("true".equals(message)){
                if (mm.size()!=0){
                    ma = new MenuAdapter(getContext(), mm, itemClickListener);
                    llm = new LinearLayoutManager(getContext());
                    rv_menu.setLayoutManager(llm);
                    rv_menu.setAdapter(ma);
                    after.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);
                    notempty.setVisibility(View.VISIBLE);
                    empty.setVisibility(View.GONE);
                }
            } else{
                after.setVisibility(View.VISIBLE);
                loading.setVisibility(View.GONE);
                notempty.setVisibility(View.GONE);
                empty.setVisibility(View.VISIBLE);
            }
            //Toast.makeText(CreateActivity.this, message, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (TextUtils.isEmpty(lm.getNohp())){
            before.setVisibility(View.VISIBLE);
            after.setVisibility(View.GONE);
            loading.setVisibility(View.GONE);
            notempty.setVisibility(View.GONE);
            empty.setVisibility(View.GONE);
        } else {
            before.setVisibility(View.GONE);
            after.setVisibility(View.GONE);
            loading.setVisibility(View.VISIBLE);
            notempty.setVisibility(View.GONE);
            empty.setVisibility(View.GONE);
            new GetMenu().execute();
        }
    }

    private class DeleteMenu extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            url = rp.getBASE_URL()+"deleteMenu.php";

        }

        @Override
        protected Void doInBackground(Void... voids) {
            PostHandler sh = new PostHandler();

            JSONObject data = new JSONObject();
            try {
                data.put("id_menu", id);

                //System.out.println("DATA : "+data.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }

            jsonStr = sh.makeServiceCall(url, data);
            System.out.println("JSONSTR : "+jsonStr);
            //Toast.makeText(CreateActivity.this, "JSONSTR : "+jsonStr, Toast.LENGTH_LONG).show();



            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (jsonStr!=null){
                if ("Berhasil Menghapus Menu".equals(jsonStr)){
                    ma.notifyDataSetChanged();
                }
            }
            //Toast.makeText(CreateActivity.this, message, Toast.LENGTH_LONG).show();
        }
    }
}
