package duckandsheet.pascal91.com.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import duckandsheet.pascal91.com.dao.UserDao;
import duckandsheet.pascal91.com.model.User;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}