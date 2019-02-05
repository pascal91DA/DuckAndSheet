package com.pascal91.duckandsheet.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.pascal91.duckandsheet.dao.UserDao;
import com.pascal91.duckandsheet.model.User;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}