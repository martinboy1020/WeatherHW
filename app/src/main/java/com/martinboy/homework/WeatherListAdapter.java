package com.martinboy.homework;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WeatherListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Activity mAct;
    ArrayList<WeatherBean.WeatherElementBean> list;

    public WeatherListAdapter(Activity act) {
        mAct = act;
    }

    public void setList(ArrayList<WeatherBean.WeatherElementBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;

        if (viewType == 0) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_weather_text, parent, false);
            return new ViewHolderText(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_weather_pic, parent, false);
            return new ViewHolderPic(view);
        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        if (list.get(holder.getAdapterPosition()).isData()) {
            ViewHolderText holderText = (ViewHolderText) holder;
            holderText.text_start_time.setText(list.get(holder.getAdapterPosition()).getStartTime());
            holderText.text_end_time.setText(list.get(holder.getAdapterPosition()).getEndTime());
            String sb = list.get(holder.getAdapterPosition()).getParameterName() +
                    list.get(holder.getAdapterPosition()).getParameterUnit();
            holderText.text_temperature.setText(sb);
            holderText.bg_list_weather.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mAct instanceof MainActivity) {
                        ((MainActivity) mAct).goToDetailPage(list.get(holder.getAdapterPosition()));
                    }
                }
            });
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).isData()) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        if (list != null)
            return list.size();
        return 0;
    }

    public class ViewHolderText extends RecyclerView.ViewHolder {

        RelativeLayout bg_list_weather;
        TextView text_start_time, text_end_time, text_temperature;

        public ViewHolderText(@NonNull View itemView) {
            super(itemView);
            bg_list_weather = itemView.findViewById(R.id.bg_list_weather);
            text_start_time = itemView.findViewById(R.id.text_start_time);
            text_end_time = itemView.findViewById(R.id.text_end_time);
            text_temperature = itemView.findViewById(R.id.text_temperature);

        }
    }

    public class ViewHolderPic extends RecyclerView.ViewHolder {
        public ViewHolderPic(@NonNull View itemView) {
            super(itemView);
        }
    }
}
