package com.example.mykampuslite.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.mykampuslite.model.Dosen;
import com.example.mykampuslite.model.Mahasiswa;

@Database(entities = {Mahasiswa.class, Dosen.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class}) // Tambahkan baris ini
public abstract class MyKampusDatabase extends RoomDatabase {

    private static volatile MyKampusDatabase INSTANCE;

    public abstract AkademikDao akademikDao();

    public static MyKampusDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (MyKampusDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            MyKampusDatabase.class,
                            "mykampus_db"
                    ).fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }
}