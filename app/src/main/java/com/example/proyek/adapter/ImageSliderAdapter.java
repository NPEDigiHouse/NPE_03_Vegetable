package com.example.proyek.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.proyek.ImageSliderData;
import com.example.proyek.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class ImageSliderAdapter extends SliderViewAdapter<ImageSliderAdapter.SliderAdapterViewHolder> {
    private final List<ImageSliderData> imageSliderList;

    public ImageSliderAdapter(Context context, ArrayList<ImageSliderData> imageSliderList) {
        this.imageSliderList = imageSliderList;
    }

    @Override
    public SliderAdapterViewHolder onCreateViewHolder(ViewGroup parent) {
        @SuppressLint("InflateParams")
        View inflate = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_slider, null);

        return new SliderAdapterViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterViewHolder viewHolder, final int position) {
        ImageSliderData sliderItem = imageSliderList.get(position);
        ImageView ivAd = viewHolder.itemView.findViewById(R.id.ivAd);
        ivAd.setImageResource(sliderItem.getImg());
    }

    @Override
    public int getCount() {
        return imageSliderList.size();
    }

    static class SliderAdapterViewHolder extends SliderViewAdapter.ViewHolder {
        View itemView;
        ImageView imageViewBackground;

        public SliderAdapterViewHolder(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.ivAd);
            this.itemView = itemView;
        }
    }
}
