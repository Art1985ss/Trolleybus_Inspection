package com.art.trolleybusinspection.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.RecyclerView;

import com.art.trolleybusinspection.R;
import com.art.trolleybusinspection.config.ValueConstants;
import com.art.trolleybusinspection.entity.Trolley;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TrolleyAdapter extends RecyclerView.Adapter<TrolleyAdapter.TrolleyHolder> {
    private List<Trolley> trolleys = new ArrayList<>();
    private OnItemClickListener listener;
    private boolean sortByDate = false;

    public TrolleyAdapter(boolean sortByDate) {
        this.sortByDate = sortByDate;
    }

    @NonNull
    @Override
    public TrolleyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trolley_item, parent, false);
        return new TrolleyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TrolleyHolder holder, int position) {
        Trolley trolley = trolleys.get(position);
        holder.setTrolley(trolley);
    }

    @Override
    public int getItemCount() {
        return trolleys.size();
    }

    public void setTrolleys(List<Trolley> trolleys) {
        if (sortByDate) {
            this.trolleys = trolleys.stream().sorted(Comparator.comparing(Trolley::getDate).reversed()).collect(Collectors.toList());
        } else {
            this.trolleys = trolleys;
        }
        notifyDataSetChanged();
    }

    public Trolley getTrolleyAt(int position) {
        return trolleys.get(position);
    }


    public interface OnItemClickListener {
        void onItemClick(Trolley trolley);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setSortByDate(boolean sortByDate) {
        this.sortByDate = sortByDate;
    }

    class TrolleyHolder extends RecyclerView.ViewHolder {
        private final TextView textViewTrolleyId;
        private final TextView textViewTrollModel;
        private final TextView text_view_date;

        public TrolleyHolder(@NonNull View itemView) {
            super(itemView);
            textViewTrolleyId = itemView.findViewById(R.id.text_view_trolley_id);
            textViewTrollModel = itemView.findViewById(R.id.text_view_trolley_model);
            text_view_date = itemView.findViewById(R.id.text_view_date);
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(trolleys.get(position));
                }
            });
        }

        public void setTrolley(Trolley trolley) {
            textViewTrolleyId.setText(String.valueOf(trolley.getId()));
            textViewTrollModel.setText(trolley.getModel().toString());
            text_view_date.setText(String.valueOf(trolley.getDate().format(ValueConstants.DATE_FORMAT)));
            text_view_date.setBackgroundColor(dateColor(trolley));
        }

        private int dateColor(Trolley trolley) {
            LocalDate dateNow = LocalDate.now();
            LocalDate dateOld = trolley.getDate();
            long days = ChronoUnit.DAYS.between(dateOld, dateNow);
            if (days > 120){
                return Color.rgb(223, 0, 254);
            }
            float[] hsl = {0, 1, 0.5f};
            hsl[0] = (float) ((float) (90 - days) / 90.0) * 130;
            return ColorUtils.HSLToColor(hsl);
        }

    }
}
