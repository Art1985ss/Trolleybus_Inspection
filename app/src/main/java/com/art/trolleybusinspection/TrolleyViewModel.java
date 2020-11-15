package com.art.trolleybusinspection;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.art.trolleybusinspection.database.TrolleyRepository;
import com.art.trolleybusinspection.entity.Trolley;

import java.util.List;

public class TrolleyViewModel extends AndroidViewModel implements IOperations<Trolley> {
    private final TrolleyRepository trolleyRepository;
    private final LiveData<List<Trolley>> trolleys;

    public TrolleyViewModel(@NonNull Application application) {
        super(application);
        trolleyRepository = TrolleyRepository.getInstance(application);
        trolleys = trolleyRepository.getAll();
    }

    @Override
    public void insert(Trolley trolley) {
        trolleyRepository.insert(trolley);
    }

    @Override
    public void update(Trolley trolley) {
        trolleyRepository.update(trolley);
    }

    @Override
    public void delete(Trolley trolley) {
        trolleyRepository.delete(trolley);
    }

    @Override
    public Trolley findById(int id) {
        return trolleyRepository.findById(id);
    }

    @Override
    public LiveData<List<Trolley>> getAll() {
        return trolleys;
    }
}
