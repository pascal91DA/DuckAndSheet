package com.pascal91.duckandsheet.tasks;

import android.os.AsyncTask;

import com.pascal91.duckandsheet.db.AppDatabase;
import com.pascal91.duckandsheet.model.User;

public class DatabaseAsyncTask extends AsyncTask<Void, Void, Void> {

    private AppDatabase db;
    private User user;

    public DatabaseAsyncTask(AppDatabase db, User user) {
        this.db = db;
        this.user = user;
    }

    public AppDatabase getDb() {
        return db;
    }

    public void setDb(AppDatabase db) {
        this.db = db;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {

            if(this.db.userDao().findByName(this.user.firstName, this.user.lastName) == null){
                this.db.userDao().insertAll(this.user);
            }

            for(User user: this.db.userDao().getAll()){
                System.out.println(user.firstName + " " + user.lastName);
            }

        } catch (Exception e) {
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