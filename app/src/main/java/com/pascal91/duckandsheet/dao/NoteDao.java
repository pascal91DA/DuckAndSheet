package com.pascal91.duckandsheet.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import com.pascal91.duckandsheet.model.Note;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM Note")
    List<Note> getAll();

    @Query("SELECT * FROM Note WHERE uid IN (:userIds)")
    List<Note> loadAllByIds(int[] userIds);

    //@Query("SELECT * FROM Note WHERE title LIKE :first AND " + "content LIKE :last LIMIT 1")
    @Query("SELECT * FROM  Note WHERE Note.title LIKE '%:text%' OR Note.content LIKE '%:text%'")
    Note findNote(String text);

    @Insert
    void insertAll(Note... notes);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);
}
