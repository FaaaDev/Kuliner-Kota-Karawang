package com.example.kulinerkotakarawang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kulinerkotakarawang.R;
import com.example.kulinerkotakarawang.model.SlideModel;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterVH> {

    private Context context;
    private List<SlideModel> data;

    public SliderAdapter(Context context, List<SlideModel> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, int position) {


        Glide.with(context)
                .load(data.get(position).getLink())
                .into(viewHolder.img_slide);

        viewHolder.slider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return data.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView img_slide;
        LinearLayout slider;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            img_slide = itemView.findViewById(R.id.img_slide);
            slider = itemView.findViewById(R.id.slider);
            this.itemView = itemView;
        }
    }

}
