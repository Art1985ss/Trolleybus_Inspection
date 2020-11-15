package com.art.trolleybusinspection;

import androidx.lifecycle.LiveData;

import java.util.List;

public interface IOperations<T> {
    void insert(T t);

    void update(T t);

    void delete(T t);

    T findById(int id);

    LiveData<List<T>> getAll();
}
