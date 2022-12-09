package com.smartpill.dispenser.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartpill.dispenser.Constant;
import com.smartpill.dispenser.R;
import com.smartpill.dispenser.model.Alarm;
import com.smartpill.dispenser.utils.Utils;

import java.util.ArrayList;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.MyViewHolder> {

    private final ArrayList<Alarm> alarmData;
    private final Context context;
    Utils utils;
    SharedPreferences sp;

    public AlarmAdapter(Context context, ArrayList<Alarm> alarmData) {
        this.context = context;
        this.alarmData = alarmData;
        utils = new Utils();
        sp = context.getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alarm_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        String name = alarmData.get(position).getAlarmName();
        String time = alarmData.get(position).getAlarmTime();

        holder.txtName.setText(name);
        holder.txtTime.setText(time);
    }

    @Override
    public int getItemCount() {
        return alarmData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtName, txtTime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txt_alarm_name);
            txtTime = itemView.findViewById(R.id.txt_alarm_time);

//            itemView.setOnClickListener(this);
        }

//        @Override
//        public void onClick(View view) {
////            Intent i = new Intent(context, ViewPillActivity.class);
////            i.putExtra(Constant.PILL_ID, alarmData.get(getAdapterPosition()).getAlarmId());
////            context.startActivity(i);
//        }
    }
}