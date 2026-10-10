package com.example.mykampuslite.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.mykampuslite.model.Mahasiswa;
import com.example.mykampuslite.repository.AkademikRepository;
import java.util.List;

public class MahasiswaViewModel extends ViewModel {
    private final AkademikRepository<Mahasiswa> repository;
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public MahasiswaViewModel(AkademikRepository<Mahasiswa> repository) {
        this.repository = repository;
    }

    public LiveData<List<Mahasiswa>> getMahasiswaList() {
        return repository.getAll();
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void simpanMahasiswa(long id, String nama, String nim, String jurusan, String ipkText) {
        try {
            if (ipkText == null || ipkText.trim().isEmpty()) {
                errorMessage.setValue("IPK tidak boleh kosong");
                return;
            }

            double ipk = Double.parseDouble(ipkText.trim());

            Mahasiswa m = new Mahasiswa();
            if (id > 0) {
                m.setId(id);
            }

            m.setNama(nama);
            m.setNim(nim);
            m.setJurusan(jurusan);
            m.setIpk(ipk);

            if (id > 0) {
                m.setUpdatedAt(System.currentTimeMillis());
                repository.update(m);
            } else {
                repository.insert(m);
            }

            errorMessage.setValue(null);
        } catch (NumberFormatException e) {
            errorMessage.setValue("IPK harus berupa angka desimal yang valid");
        } catch (IllegalArgumentException e) {
            errorMessage.setValue(e.getMessage());
        }
    }

    public void deleteMahasiswa(Mahasiswa mahasiswa) {
        repository.delete(mahasiswa);
    }
}