package com.example.shredder.imview.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.shredder.imview.Activity.DownloadActivity;
import com.example.shredder.imview.Model.PexelsStruct;
import com.example.shredder.imview.R;
import com.example.shredder.imview.Utils.Util;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

import static maes.tech.intentanim.CustomIntent.customType;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder> {

    private Context context;
    private ArrayList<PexelsStruct> arrayList;

    public DashboardAdapter(Context context, ArrayList<PexelsStruct> arrayList) {

        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Picasso.get().load(arrayList.get(position).getMedium()).into(holder.recyc_imageView);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RoundedImageView recyc_imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            recyc_imageView = (RoundedImageView) itemView.findViewById(R.id.recyc_imageView);
            recyc_imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String imageUrl = arrayList.get(getAdapterPosition()).getPortrait();

                    Intent intent = new Intent(context,DownloadActivity.class);
                    intent.putExtra(Util.IMAGE_URL,imageUrl);
                    context.startActivity(intent);
                    customType(context,"fadein-to-fadeout");

                }
            });
        }
    }
}
