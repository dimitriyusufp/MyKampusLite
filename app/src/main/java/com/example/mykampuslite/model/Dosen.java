package com.example.mykampuslite.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(tableName = "dosen")
public class Dosen extends Person implements EvaluasiAkademik {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String nidn;
    private List<String> mataKuliahDiampu;

    public Dosen() {
        long currentTime = System.currentTimeMillis();
        this.createdAt = currentTime;
        this.updatedAt = currentTime;
        this.mataKuliahDiampu = new ArrayList<>();
    }

    @Override
    public String getRole() {
        return "Dosen";
    }

    @Override
    public String getStatusAkademik() {
        int jumlah = (mataKuliahDiampu != null) ? mataKuliahDiampu.size() : 0;
        return (jumlah > 3) ? "Beban Lebih" : "Normal";
    }

    @Override
    public long getId() { return id; }

    @Override
    public void setId(long id) { this.id = id; }

    public String getNidn() { return nidn; }

    public void setNidn(String nidn) {
        if (nidn == null) {
            throw new IllegalArgumentException("NIDN tidak boleh kosong");
        }
        String cleanNidn = nidn.trim();
        if (cleanNidn.isEmpty()) {
            throw new IllegalArgumentException("NIDN tidak boleh kosong");
        }
        this.nidn = cleanNidn;
    }

    public List<String> getMataKuliahDiampu() {
        return mataKuliahDiampu != null ? mataKuliahDiampu : new ArrayList<>();
    }

    public void setMataKuliahDiampu(List<String> mataKuliahDiampu) {
        this.mataKuliahDiampu = (mataKuliahDiampu != null) ? mataKuliahDiampu : new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dosen d = (Dosen) o;
        return getId() == d.getId() &&
                Objects.equals(nidn, d.nidn) &&
                Objects.equals(getNama(), d.getNama()) &&
                Objects.equals(getEmail(), d.getEmail()) &&
                Objects.equals(mataKuliahDiampu, d.mataKuliahDiampu);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), nidn, getNama(), getEmail(), mataKuliahDiampu);
    }
}