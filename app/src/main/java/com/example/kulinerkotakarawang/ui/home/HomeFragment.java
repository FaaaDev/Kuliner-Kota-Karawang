package com.example.kulinerkotakarawang.ui.home;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import com.example.kulinerkotakarawang.R;
import com.example.kulinerkotakarawang.adapter.ItemClickListener;
import com.example.kulinerkotakarawang.adapter.KulinerAdapter;
import com.example.kulinerkotakarawang.adapter.LocationAdapter;
import com.example.kulinerkotakarawang.adapter.MenuAdapter;
import com.example.kulinerkotakarawang.adapter.SliderAdapter;
import com.example.kulinerkotakarawang.http.PostHandler;
import com.example.kulinerkotakarawang.http.ReqProperty;
import com.example.kulinerkotakarawang.model.CityModel;
import com.example.kulinerkotakarawang.model.MenuModel;
import com.example.kulinerkotakarawang.model.SlideModel;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView rv_lokasi, rv_kuliner;
    private LocationAdapter la;
    private KulinerAdapter ka;
    private LinearLayoutManager llm, llm2;
    private List<CityModel> cmod = new ArrayList<>();
    private SliderView slider_berita;
    private List<SlideModel> slideModels = new ArrayList<>();
    private String url, message;
    private String wilayah;
    private ReqProperty rp = new ReqProperty();
    private List<MenuModel> mm;
    private LinearLayout after, loading, empty;
    private ItemClickListener listener;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        _init(root);
        _prep();

        return root;
    }

    private void _init(View root){
        slider_berita = root.findViewById(R.id.slider_berita);
        rv_lokasi = root.findViewById(R.id.rv_lokasi);
        rv_kuliner = root.findViewById(R.id.rv_kuliner);
        after = root.findViewById(R.id.after);
        loading = root.findViewById(R.id.loading);
        empty = root.findViewById(R.id.empty);
    }

    private void _prep(){
        slideModels.add(new SlideModel("https://images.unsplash.com/photo-1482049016688-2d3e1b311543?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=353&q=80"));
        slideModels.add(new SlideModel("https://images.unsplash.com/photo-1504674900247-0877df9cc836?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=750&q=80"));
        slideModels.add(new SlideModel("https://images.unsplash.com/photo-1504754524776-8f4f37790ca0?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=750&q=80"));
        slideModels.add(new SlideModel("https://images.unsplash.com/photo-1505253716362-afaea1d3d1af?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        slideModels.add(new SlideModel("https://images.unsplash.com/photo-1496412705862-e0088f16f791?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=750&q=80"));

        slider_berita.setSliderAdapter(new SliderAdapter(getContext(), slideModels));
        slider_berita.setIndicatorAnimation(IndicatorAnimations.WORM);
        slider_berita.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        slider_berita.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);

        cmod.add(new CityModel("Cikampek", true));
        cmod.add(new CityModel("Cilamaya", false));
        cmod.add(new CityModel("Jatisari", false));
        cmod.add(new CityModel("Klari", false));
        cmod.add(new CityModel("Kosambi", false));
        cmod.add(new CityModel("Pancawati", false));
        cmod.add(new CityModel("Purwasari", false));
        cmod.add(new CityModel("Teluk Jambe", false));

        listener = new ItemClickListener() {
            @Override
            public void onItemClicked(String params) {
                setMenu(params);
            }
        };

        la = new LocationAdapter(getContext(), cmod, listener);
        llm = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        llm2 = new LinearLayoutManager(getContext());
        rv_lokasi.setLayoutManager(llm);
        rv_lokasi.setAdapter(la);

        setMenu("Cikampek");

    }

    public void setMenu(String wilayah){
        this.wilayah = wilayah;
        loading.setVisibility(View.VISIBLE);
        after.setVisibility(View.GONE);
        empty.setVisibility(View.GONE);
        //new GetMenu().execute();
    }

    private class GetMenu extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            url = rp.getBASE_URL()+"tampilMenu.php";

            mm = new ArrayList<>();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            PostHandler sh = new PostHandler();

            JSONObject data = new JSONObject();
            try {
                data.put("wilayah", wilayah);

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
                    ka = new KulinerAdapter(getContext(), mm);
                    llm = new LinearLayoutManager(getContext());
                    rv_kuliner.setLayoutManager(llm2);
                    rv_kuliner.setAdapter(ka);
                    after.setVisibility(View.VISIBLE);
                    empty.setVisibility(View.GONE);
                    loading.setVisibility(View.GONE);
                }
            } else {
                after.setVisibility(View.GONE);
                empty.setVisibility(View.VISIBLE);
                loading.setVisibility(View.GONE);
            }
            //Toast.makeText(CreateActivity.this, message, Toast.LENGTH_LONG).show();
        }
    }
}
