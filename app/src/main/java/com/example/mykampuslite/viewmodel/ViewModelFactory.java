package com.example.mykampuslite.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.example.mykampuslite.model.Dosen;
import com.example.mykampuslite.model.Mahasiswa;
import com.example.mykampuslite.repository.AkademikRepository;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private final AkademikRepository<Mahasiswa> mRepo;
    private final AkademikRepository<Dosen> dRepo;

    public ViewModelFactory(AkademikRepository<Mahasiswa> mRepo, AkademikRepository<Dosen> dRepo) {
        this.mRepo = mRepo;
        this.dRepo = dRepo;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MahasiswaViewModel.class)) {
            return (T) new MahasiswaViewModel(mRepo);
        } else if (modelClass.isAssignableFrom(DosenViewModel.class)) {
            return (T) new DosenViewModel(dRepo);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}