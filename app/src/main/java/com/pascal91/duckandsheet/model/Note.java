package com.pascal91.duckandsheet.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Note {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "content")
    public String content;

    @ColumnInfo(name = "text_color")
    public int textColor;

    @ColumnInfo(name = "content_color")
    public int contentColor;

    public Note(int uid, String title, String content) {
        this.uid = uid;
        this.title = title;
        this.content = content;
    }
}