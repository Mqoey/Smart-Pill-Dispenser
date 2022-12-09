package com.smartpill.dispenser.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.smartpill.dispenser.Constant;
import com.smartpill.dispenser.R;
import com.smartpill.dispenser.model.Pill;
import com.smartpill.dispenser.pill.ViewPillActivity;
import com.smartpill.dispenser.utils.Utils;

import java.io.File;
import java.util.ArrayList;

public class PillAdapter extends RecyclerView.Adapter<PillAdapter.MyViewHolder> {

    private final ArrayList<Pill> applianceData;
    private final Context context;
    Utils utils;
    SharedPreferences sp;

    public PillAdapter(Context context, ArrayList<Pill> applianceData) {
        this.context = context;
        this.applianceData = applianceData;
        utils = new Utils();
        sp = context.getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pill_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        String name = applianceData.get(position).getPillName();
        String consumption = applianceData.get(position).getPillConsumption();
        String image = applianceData.get(position).getPillImage();

        holder.txtApplianceName.setText(name);
        holder.txtApplianceConsumption.setText(consumption + " tablets per day");

        File imageUrl = new File(image);

        if (image != null) {
            if (image.length() < 3) {
                holder.applianceImage.setImageResource(R.drawable.image_placeholder);
            } else {
                Glide.with(context)
                        .load(imageUrl)
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.image_placeholder)
                        .into(holder.applianceImage);
            }
        }
    }

    @Override
    public int getItemCount() {
        return applianceData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txtApplianceName, txtApplianceCondition, txtApplianceConsumption;
        ImageView applianceImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtApplianceName = itemView.findViewById(R.id.txt_appliance_name);
            txtApplianceCondition = itemView.findViewById(R.id.txt_condition_supplier);
            txtApplianceConsumption = itemView.findViewById(R.id.txt_appliance_consumption);
            applianceImage = itemView.findViewById(R.id.product_image);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent i = new Intent(context, ViewPillActivity.class);
            i.putExtra(Constant.PILL_ID, applianceData.get(getAdapterPosition()).getPillId());
            context.startActivity(i);
        }
    }
}