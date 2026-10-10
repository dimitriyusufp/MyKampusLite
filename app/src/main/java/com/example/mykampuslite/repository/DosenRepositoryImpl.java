package com.example.mykampuslite.repository;

import androidx.lifecycle.LiveData;
import com.example.mykampuslite.database.AkademikDao;
import com.example.mykampuslite.model.Dosen;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DosenRepositoryImpl implements AkademikRepository<Dosen> {
    private final AkademikDao dao;
    private final ExecutorService executorService;

    public DosenRepositoryImpl(AkademikDao dao) {
        this.dao = dao;
        this.executorService = Executors.newSingleThreadExecutor();
    }

    @Override
    public LiveData<List<Dosen>> getAll() {
        return dao.getAllDosen();
    }

    @Override
    public void insert(Dosen item) {
        executorService.execute(() -> dao.insertDosen(item));
    }

    @Override
    public void update(Dosen item) {
        executorService.execute(() -> dao.updateDosen(item));
    }

    @Override
    public void delete(Dosen item) {
        executorService.execute(() -> dao.deleteDosen(item));
    }
}