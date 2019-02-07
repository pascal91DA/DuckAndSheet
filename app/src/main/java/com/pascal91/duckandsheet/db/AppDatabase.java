package com.pascal91.duckandsheet.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.pascal91.duckandsheet.dao.NoteDao;
import com.pascal91.duckandsheet.model.Note;

@Database(entities = {Note.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract NoteDao noteDao();

    private static AppDatabase noteDB;

    public static AppDatabase getInstance(Context context) {
        if (null == noteDB) {
            noteDB = buildDatabaseInstance(context);
        }
        return noteDB;
    }

    private static AppDatabase buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "dnc-first.db")
                .allowMainThreadQueries().build();
    }

    public void cleanUp() {
        noteDB = null;
    }

}