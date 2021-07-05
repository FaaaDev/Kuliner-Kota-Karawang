package com.example.kulinerkotakarawang.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kulinerkotakarawang.R;
import com.example.kulinerkotakarawang.model.CityModel;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {

    private Context mContext;
    private List<CityModel> mData;
    private int selectedItem = 0;
    private int sebelum = 0;
    private ItemClickListener itemClickListener;

    public LocationAdapter(Context mContext, List<CityModel> mData, ItemClickListener itemClickListener) {
        this.mContext = mContext;
        this.mData = mData;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public LocationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.location_layout, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final LocationAdapter.ViewHolder holder, final int position) {
        holder.cityname.setText(mData.get(position).getCity());
        if (mData.get(position).isSelected()) {
            holder.main.setBackgroundColor(Color.argb(255, 253, 201, 19));
            holder.aksen.setBackgroundColor(Color.WHITE);
            holder.source.setImageResource(R.drawable.ic_next_black);
        } else {
            holder.main.setBackgroundColor(Color.WHITE);
            holder.aksen.setBackgroundColor(Color.argb(255, 241, 107, 104));
            holder.source.setImageResource(R.drawable.ic_next_white);
        }

        holder.body.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position != sebelum){
                    mData.get(position).setSelected(true);
                    mData.get(sebelum).setSelected(false);
                    sebelum = position;
                    notifyDataSetChanged();
                }
                itemClickListener.onItemClicked(mData.get(position).getCity());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout main, aksen;
        private ImageView source;
        private CardView body;
        private TextView cityname;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            main = itemView.findViewById(R.id.main);
            aksen = itemView.findViewById(R.id.aksen);
            source = itemView.findViewById(R.id.source);
            body = itemView.findViewById(R.id.body);
            cityname = itemView.findViewById(R.id.cityname);

        }
    }
}
