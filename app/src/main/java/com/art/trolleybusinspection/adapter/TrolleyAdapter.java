package com.art.trolleybusinspection.adapter;

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
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class TrolleyAdapter extends RecyclerView.Adapter<TrolleyAdapter.TrolleyHolder> {
    private List<Trolley> trolleys = new ArrayList<>();
    private OnItemClickListener listener;
    private final boolean sortByDate;

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
            this.trolleys = trolleys.stream().sorted(Comparator.comparing(Trolley::getDateR0).reversed()).collect(Collectors.toList());
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


    class TrolleyHolder extends RecyclerView.ViewHolder {
        private final TextView textViewTrolleyId;
        private final TextView textViewTrollModel;
        private final TextView textViewDate;
        private final TextView textViewDays;

        public TrolleyHolder(@NonNull View itemView) {
            super(itemView);
            textViewTrolleyId = itemView.findViewById(R.id.text_view_trolley_id);
            textViewTrollModel = itemView.findViewById(R.id.text_view_trolley_model);
            textViewDate = itemView.findViewById(R.id.text_view_date);
            textViewDays = itemView.findViewById(R.id.text_view_days);
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
            fillDateField(trolley);
        }

        private void fillDateField(Trolley trolley) {
            float maxDays = 120.0f;
            float maxH = 130.0f;
            LocalDate dateNow = LocalDate.now();
            LocalDate dateOld = trolley.getDateR0();
            long days = ChronoUnit.DAYS.between(dateOld, dateNow);
            float[] hsl = {0, 1, 0.5f};
            hsl[0] = days > maxDays ? 0 : (maxDays - days) / maxDays * maxH;
            textViewDate.setText(trolley.getDateR0().format(ValueConstants.DATE_FORMAT));
            textViewDays.setBackgroundColor(ColorUtils.HSLToColor(hsl));
            textViewDays.setText(String.format(Locale.ENGLISH, "%d", days));

        }

    }
}
