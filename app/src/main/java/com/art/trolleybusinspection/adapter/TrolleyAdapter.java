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
        private final TextView textViewDateR0;
        private final TextView textViewDaysR0;
        private final TextView textViewDateR1;
        private final TextView textViewDaysR1;

        public TrolleyHolder(@NonNull View itemView) {
            super(itemView);
            textViewTrolleyId = itemView.findViewById(R.id.text_view_trolley_id);
            textViewTrollModel = itemView.findViewById(R.id.text_view_trolley_model);
            textViewDateR0 = itemView.findViewById(R.id.text_view_dateR0);
            textViewDaysR0 = itemView.findViewById(R.id.text_view_daysR0);
            textViewDateR1 = itemView.findViewById(R.id.text_view_dateR1);
            textViewDaysR1 = itemView.findViewById(R.id.text_view_daysR1);
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
            final float maxDaysR0 = 120.0f;
            final float maxH = 130.0f;
            final float maxDaysR1 = 210.0f;

            final LocalDate dateNow = LocalDate.now();
            LocalDate r0 = trolley.getDateR0();
            long days = ChronoUnit.DAYS.between(r0, dateNow);
            float[] hsl = {0, 1, 0.5f};
            hsl[0] = days > maxDaysR0 ? 0 : (maxDaysR0 - days) / maxDaysR0 * maxH;
            textViewDateR0.setText(r0.format(ValueConstants.DATE_FORMAT));
            textViewDaysR0.setBackgroundColor(ColorUtils.HSLToColor(hsl));
            textViewDaysR0.setText(String.format(Locale.ENGLISH, "%d", days));

            LocalDate r1 = trolley.getDateR1();
            days = ChronoUnit.DAYS.between(r1, dateNow);
            hsl[0] = days > maxDaysR1 ? 0 : (maxDaysR1 - days) / maxDaysR1 * maxH;
            textViewDateR1.setText(r1.format(ValueConstants.DATE_FORMAT));
            textViewDaysR1.setBackgroundColor(ColorUtils.HSLToColor(hsl));
            textViewDaysR1.setText(String.format(Locale.ENGLISH, "%d", days));
        }

    }
}
