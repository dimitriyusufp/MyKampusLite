package com.example.mykampuslite.ui;

import android.os.Bundle;
import android.text.TextUtils;
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
import com.example.mykampuslite.model.Dosen;
import com.example.mykampuslite.repository.AkademikRepository;
import com.example.mykampuslite.repository.DosenRepositoryImpl;
import com.example.mykampuslite.viewmodel.DosenViewModel;
import com.example.mykampuslite.viewmodel.ViewModelFactory;

public class DosenFragment extends Fragment {

    private DosenViewModel viewModel;
    private PersonAdapter adapter;
    private EditText etNama, etNidn, etEmail, etMataKuliah;
    private Button btnSimpan;
    private long selectedDosenId = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dosen, container, false);

        etNama = view.findViewById(R.id.etNama);
        etNidn = view.findViewById(R.id.etNidn);
        etEmail = view.findViewById(R.id.etEmail);
        etMataKuliah = view.findViewById(R.id.etMataKuliah);
        btnSimpan = view.findViewById(R.id.btnSimpan);

        // Langsung ambil ID rvDosen dari fragment_dosen.xml
        RecyclerView recyclerView = view.findViewById(R.id.rvDosen);

        MyKampusDatabase db = MyKampusDatabase.getInstance(requireContext());
        AkademikRepository<Dosen> repository = new DosenRepositoryImpl(db.akademikDao());
        ViewModelFactory factory = new ViewModelFactory(null, repository);
        viewModel = new ViewModelProvider(this, factory).get(DosenViewModel.class);

        adapter = new PersonAdapter(
                person -> {
                    if (person instanceof Dosen) {
                        Dosen d = (Dosen) person;
                        selectedDosenId = d.getId();
                        etNama.setText(d.getNama());
                        etNidn.setText(d.getNidn());
                        etEmail.setText(d.getEmail());
                        if (d.getMataKuliahDiampu() != null) {
                            etMataKuliah.setText(TextUtils.join(", ", d.getMataKuliahDiampu()));
                        }
                        btnSimpan.setText("Update Dosen");
                    }
                },
                person -> {
                    if (person instanceof Dosen) {
                        viewModel.deleteDosen((Dosen) person);
                        Toast.makeText(requireContext(), "Data Dosen dihapus", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
            recyclerView.setAdapter(adapter);
        }

        viewModel.getDosenList().observe(getViewLifecycleOwner(), list -> {
            adapter.submitList(list != null ? (java.util.List) list : new java.util.ArrayList<>());
        });

        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(requireContext(), selectedDosenId > 0 ? "Data Dosen diperbarui" : "Data Dosen disimpan", Toast.LENGTH_SHORT).show();
                clearForm();
            }
        });

        btnSimpan.setOnClickListener(v -> {
            String nama = etNama.getText().toString();
            String nidn = etNidn.getText().toString();
            String email = etEmail.getText().toString();
            String mk = etMataKuliah.getText().toString();

            viewModel.simpanDosen(selectedDosenId, nama, nidn, email, mk);
        });

        return view;
    }

    private void clearForm() {
        selectedDosenId = 0;
        etNama.setText("");
        etNidn.setText("");
        etEmail.setText("");
        etMataKuliah.setText("");
        btnSimpan.setText("Simpan Dosen");
    }
}