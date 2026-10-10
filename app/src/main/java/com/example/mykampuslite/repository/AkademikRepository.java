package com.example.mykampuslite.repository;

import androidx.lifecycle.LiveData;
import java.util.List;

public interface AkademikRepository<T> {
    LiveData<List<T>> getAll();
    void insert(T item);
    void update(T item);
    void delete(T item);
}