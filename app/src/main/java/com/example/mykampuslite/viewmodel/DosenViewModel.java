package com.example.mykampuslite.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.mykampuslite.model.Dosen;
import com.example.mykampuslite.repository.AkademikRepository;
import java.util.ArrayList;
import java.util.List;

public class DosenViewModel extends ViewModel {
    private final AkademikRepository<Dosen> repository;
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public DosenViewModel(AkademikRepository<Dosen> repository) {
        this.repository = repository;
    }

    public LiveData<List<Dosen>> getDosenList() {
        return repository.getAll();
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void simpanDosen(long id, String nama, String nidn, String email, String mkInputText) {
        try {
            Dosen d = new Dosen();
            if (id > 0) {
                d.setId(id);
            }

            d.setNama(nama);
            d.setNidn(nidn);
            d.setEmail(email);

            List<String> listMk = new ArrayList<>();
            if (mkInputText != null && !mkInputText.trim().isEmpty()) {
                String[] items = mkInputText.split(",");
                for (String item : items) {
                    if (!item.trim().isEmpty()) {
                        listMk.add(item.trim());
                    }
                }
            }
            d.setMataKuliahDiampu(listMk);

            if (id > 0) {
                d.setUpdatedAt(System.currentTimeMillis());
                repository.update(d);
            } else {
                repository.insert(d);
            }

            errorMessage.setValue(null);
        } catch (IllegalArgumentException e) {
            errorMessage.setValue(e.getMessage());
        }
    }

    public void deleteDosen(Dosen dosen) {
        repository.delete(dosen);
    }
}