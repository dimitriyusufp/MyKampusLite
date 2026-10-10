package com.example.mykampuslite.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.Objects;

@Entity(tableName = "mahasiswa")
public class Mahasiswa extends Person implements EvaluasiAkademik {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String nim;
    private String jurusan;
    private double ipk;

    public Mahasiswa() {
        long currentTime = System.currentTimeMillis();
        this.createdAt = currentTime;
        this.updatedAt = currentTime;
    }

    @Override
    public String getRole() {
        return "Mahasiswa";
    }

    @Override
    public String getStatusAkademik() {
        if (ipk > 3.5) return "Cumlaude";
        if (ipk > 3.0) return "Memuaskan";
        if (ipk < 2.0) return "Peringatan";
        return "Sangat Memuaskan";
    }

    @Override
    public long getId() { return id; }

    @Override
    public void setId(long id) { this.id = id; }

    public String getNim() { return nim; }

    public void setNim(String nim) {
        if (nim == null) {
            throw new IllegalArgumentException("NIM tidak boleh kosong");
        }
        String cleanNim = nim.trim();
        if (!cleanNim.matches("^[0-9]{10}$")) {
            throw new IllegalArgumentException("NIM harus tepat 10 digit angka");
        }
        this.nim = cleanNim;
    }

    public String getJurusan() { return jurusan; }

    public void setJurusan(String jurusan) {
        if (jurusan == null || jurusan.trim().isEmpty()) {
            throw new IllegalArgumentException("Jurusan tidak boleh kosong");
        }
        this.jurusan = jurusan.trim();
    }

    public double getIpk() { return ipk; }

    public void setIpk(double ipk) {
        if (ipk < 0.0 || ipk > 4.0) {
            throw new IllegalArgumentException("IPK harus berada pada rentang 0.0 - 4.0");
        }
        this.ipk = ipk;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mahasiswa m = (Mahasiswa) o;
        return getId() == m.getId() &&
                Double.compare(m.ipk, ipk) == 0 &&
                Objects.equals(nim, m.nim) &&
                Objects.equals(getNama(), m.getNama()) &&
                Objects.equals(getEmail(), m.getEmail()) &&
                Objects.equals(jurusan, m.jurusan);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), nim, getNama(), getEmail(), jurusan, ipk);
    }
}