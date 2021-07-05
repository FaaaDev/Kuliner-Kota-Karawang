package com.example.kulinerkotakarawang.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kulinerkotakarawang.BottomDetail;
import com.example.kulinerkotakarawang.R;
import com.example.kulinerkotakarawang.http.SendWhatsapp;
import com.example.kulinerkotakarawang.model.DetailModel;
import com.example.kulinerkotakarawang.model.MenuModel;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class KulinerAdapter extends RecyclerView.Adapter<KulinerAdapter.ViewHolder> {

    private Context mContext;
    private List<MenuModel> mData;
    DetailModel dm = new DetailModel();

    public KulinerAdapter(Context mContext, List<MenuModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public KulinerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.kuliner_layout, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull KulinerAdapter.ViewHolder holder, final int position) {
        holder.menuname.setText(mData.get(position).getNama());
        holder.operasional.setText("Jam "+mData.get(position).getOperasional());
        holder.price.setText("Rp. "+mData.get(position).getHarga());
        Glide.with(mContext)
                .load(mData.get(position).getGambar())
                .into(holder.imagemenu);
        holder.additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dm.setNama(mData.get(position).getNama());
                dm.setAlamat(mData.get(position).getAlamat());
                dm.setDeskripsi(mData.get(position).getDeskripsi());
                dm.setHarga(mData.get(position).getHarga());
                dm.setNohp(mData.get(position).getNohp());
                dm.setOperasional(mData.get(position).getOperasional());
                dm.setGambar(mData.get(position).getGambar());

                BottomDetail bottomDetail = new BottomDetail();
                bottomDetail.show(((AppCompatActivity)mContext).getSupportFragmentManager(), bottomDetail.getTag());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView menuname, operasional, price;
        LinearLayout additem;
        ImageView imagemenu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            additem = itemView.findViewById(R.id.additem);
            menuname = itemView.findViewById(R.id.menuname);
            operasional = itemView.findViewById(R.id.operasional);
            price = itemView.findViewById(R.id.price);
            imagemenu = itemView.findViewById(R.id.imgmenu);
        }
    }
}
