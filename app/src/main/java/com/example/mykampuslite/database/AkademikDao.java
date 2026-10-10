package com.example.mykampuslite.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mykampuslite.model.Dosen;
import com.example.mykampuslite.model.Mahasiswa;

import java.util.List;

@Dao
public interface AkademikDao {

    // --- Mahasiswa CRUD ---
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMahasiswa(Mahasiswa mahasiswa);

    @Update
    void updateMahasiswa(Mahasiswa mahasiswa);

    @Delete
    void deleteMahasiswa(Mahasiswa mahasiswa);

    @Query("SELECT * FROM mahasiswa ORDER BY id DESC")
    LiveData<List<Mahasiswa>> getAllMahasiswa();

    // --- Dosen CRUD ---
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDosen(Dosen dosen);

    @Update
    void updateDosen(Dosen dosen);

    @Delete
    void deleteDosen(Dosen dosen);

    @Query("SELECT * FROM dosen ORDER BY id DESC")
    LiveData<List<Dosen>> getAllDosen();
}