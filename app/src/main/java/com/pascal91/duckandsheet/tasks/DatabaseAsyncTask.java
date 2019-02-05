package com.pascal91.duckandsheet.tasks;

import android.os.AsyncTask;

import com.pascal91.duckandsheet.db.AppDatabase;
import com.pascal91.duckandsheet.model.User;

import java.util.concurrent.TimeUnit;

public class DatabaseAsyncTask extends AsyncTask<Void, Void, Void> {

    private static int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();

    private AppDatabase db;
    private User user;

    public DatabaseAsyncTask(AppDatabase db, User user) {
        this.db = db;
        this.user = user;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {

            this.db.userDao().delete(user);

            this.db.userDao().insertAll(this.user);

            this.db.close();

            System.out.println("Записей: " + this.db.userDao().getAll().size());

            TimeUnit.SECONDS.sleep(2);

            System.out.println("Ядра: " + NUMBER_OF_CORES);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // tvInfo.setText("Begin");
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        // tvInfo.setText("End");
    }

}