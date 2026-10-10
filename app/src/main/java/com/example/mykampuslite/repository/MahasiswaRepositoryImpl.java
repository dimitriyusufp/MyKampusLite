package com.example.mykampuslite.repository;

import androidx.lifecycle.LiveData;
import com.example.mykampuslite.database.AkademikDao;
import com.example.mykampuslite.model.Mahasiswa;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MahasiswaRepositoryImpl implements AkademikRepository<Mahasiswa> {
    private final AkademikDao dao;
    private final ExecutorService executorService;

    public MahasiswaRepositoryImpl(AkademikDao dao) {
        this.dao = dao;
        this.executorService = Executors.newSingleThreadExecutor();
    }

    @Override
    public LiveData<List<Mahasiswa>> getAll() {
        return dao.getAllMahasiswa();
    }

    @Override
    public void insert(Mahasiswa item) {
        executorService.execute(() -> dao.insertMahasiswa(item));
    }

    @Override
    public void update(Mahasiswa item) {
        executorService.execute(() -> dao.updateMahasiswa(item));
    }

    @Override
    public void delete(Mahasiswa item) {
        executorService.execute(() -> dao.deleteMahasiswa(item));
    }
}