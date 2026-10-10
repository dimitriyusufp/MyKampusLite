package com.example.mykampuslite.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mykampuslite.R;
import com.example.mykampuslite.adapter.PersonAdapter;
import com.example.mykampuslite.database.MyKampusDatabase;
import com.example.mykampuslite.model.Mahasiswa;
import com.example.mykampuslite.repository.AkademikRepository;
import com.example.mykampuslite.repository.MahasiswaRepositoryImpl;
import com.example.mykampuslite.viewmodel.MahasiswaViewModel;
import com.example.mykampuslite.viewmodel.ViewModelFactory;

public class MahasiswaFragment extends Fragment {

    private MahasiswaViewModel viewModel;
    private PersonAdapter adapter;
    private EditText etNama, etNim, etJurusan, etIpk;
    private Button btnSimpan;
    private long selectedMahasiswaId = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mahasiswa, container, false);

        etNama = view.findViewById(R.id.etNama);
        etNim = view.findViewById(R.id.etNim);
        etJurusan = view.findViewById(R.id.etJurusan);
        etIpk = view.findViewById(R.id.etIpk);
        btnSimpan = view.findViewById(R.id.btnSimpan);

        // Langsung ambil ID rvMahasiswa dari fragment_mahasiswa.xml
        RecyclerView recyclerView = view.findViewById(R.id.rvMahasiswa);

        MyKampusDatabase db = MyKampusDatabase.getInstance(requireContext());
        AkademikRepository<Mahasiswa> repository = new MahasiswaRepositoryImpl(db.akademikDao());
        ViewModelFactory factory = new ViewModelFactory(repository, null);
        viewModel = new ViewModelProvider(this, factory).get(MahasiswaViewModel.class);

        adapter = new PersonAdapter(
                person -> {
                    if (person instanceof Mahasiswa) {
                        Mahasiswa m = (Mahasiswa) person;
                        selectedMahasiswaId = m.getId();
                        etNama.setText(m.getNama());
                        etNim.setText(m.getNim());
                        etJurusan.setText(m.getJurusan());
                        etIpk.setText(String.valueOf(m.getIpk()));
                        btnSimpan.setText("Update Mahasiswa");
                    }
                },
                person -> {
                    if (person instanceof Mahasiswa) {
                        viewModel.deleteMahasiswa((Mahasiswa) person);
                        Toast.makeText(requireContext(), "Data Mahasiswa dihapus", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
            recyclerView.setAdapter(adapter);
        }

        viewModel.getMahasiswaList().observe(getViewLifecycleOwner(), list -> {
            adapter.submitList(list != null ? (java.util.List) list : new java.util.ArrayList<>());
        });

        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(requireContext(), selectedMahasiswaId > 0 ? "Data berhasil diperbarui" : "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                clearForm();
            }
        });

        btnSimpan.setOnClickListener(v -> {
            String nama = etNama.getText().toString();
            String nim = etNim.getText().toString();
            String jurusan = etJurusan.getText().toString();
            String ipk = etIpk.getText().toString();

            viewModel.simpanMahasiswa(selectedMahasiswaId, nama, nim, jurusan, ipk);
        });

        return view;
    }

    private void clearForm() {
        selectedMahasiswaId = 0;
        etNama.setText("");
        etNim.setText("");
        etJurusan.setText("");
        etIpk.setText("");
        btnSimpan.setText("Simpan Mahasiswa");
    }
}