package com.art.trolleybusinspection.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.art.trolleybusinspection.R;
import com.art.trolleybusinspection.database.TrolleyRepository;
import com.art.trolleybusinspection.entity.Trolley;
import com.art.trolleybusinspection.entity.enums.TrolleyModel;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Statistics extends AppCompatActivity {
    private final TrolleyRepository repository = TrolleyRepository.getInstance(getApplication());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        List<Trolley> trolleys = repository.getAll().getValue();
        String text = Arrays.stream(TrolleyModel.values()).map(model -> {
            List<Trolley> trolleysModel = trolleys.stream().filter(trolley -> trolley.getModel().equals(model)).collect(Collectors.toList());
            long count = trolleysModel.size();
            double average = trolleysModel.stream().mapToDouble(Trolley::getMileage).average().orElse(0);
            return model + "\t(" + model.getText() + ")\n" + count + "\tAverage km : " + Math.round(average);
        }).collect(Collectors.joining("\n"));

        TextView statistics = findViewById(R.id.statistics);
        statistics.setText(text);
    }
}