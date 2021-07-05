package com.example.kulinerkotakarawang.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kulinerkotakarawang.R;
import com.example.kulinerkotakarawang.model.MenuModel;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    Context mContext;
    List<MenuModel> mData;
    ItemClickListener itemClickListener;

    public MenuAdapter(Context mContext, List<MenuModel> mData, ItemClickListener itemClickListener) {
        this.mContext = mContext;
        this.mData = mData;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.menu_layout, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MenuAdapter.ViewHolder holder, int position) {
        holder.menuname.setText(mData.get(position).getNama());
        holder.operasional.setText("Jam "+mData.get(position).getOperasional());
        holder.price.setText("Rp. "+mData.get(position).getHarga());
        Glide.with(mContext)
                .load(mData.get(position).getGambar())
                .into(holder.imagemenu);
        holder.deleteitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                alertDialogBuilder.setTitle("Hapus");

                alertDialogBuilder
                        .setMessage("Anda Yakin Ingin Menghapus Menu")
                        .setCancelable(false)
                        .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                itemClickListener.onItemClicked(mData.get(position).getId());
                                mData.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, mData.size());
                            }
                        })
                        .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView menuname, operasional, price;
        LinearLayout deleteitem;
        ImageView imagemenu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            menuname = itemView.findViewById(R.id.menuname);
            operasional = itemView.findViewById(R.id.operasional);
            price = itemView.findViewById(R.id.price);
            deleteitem = itemView.findViewById(R.id.deleteitem);
            imagemenu = itemView.findViewById(R.id.imgmenu);
        }
    }
}
