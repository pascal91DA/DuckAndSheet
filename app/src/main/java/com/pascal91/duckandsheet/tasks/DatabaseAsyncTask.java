package com.pascal91.duckandsheet.tasks;

import android.os.AsyncTask;

import com.pascal91.duckandsheet.db.AppDatabase;
import com.pascal91.duckandsheet.model.Note;

public class DatabaseAsyncTask extends AsyncTask<Void, Void, Void> {

    private AppDatabase db;
    private Note note;

    public DatabaseAsyncTask(AppDatabase db, Note note) {
        this.db = db;
        this.note = note;
    }

    public AppDatabase getDb() {
        return db;
    }

    public void setDb(AppDatabase db) {
        this.db = db;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {

            if(this.db.userDao().findByName(this.note.title, this.note.content) == null){
                this.db.userDao().insertAll(this.note);
            }

            for(Note note : this.db.userDao().getAll()){
                System.out.println(note.title + " " + note.content);
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