package com.pascal91.duckandsheet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.pascal91.duckandsheet.db.AppDatabase;
import com.pascal91.duckandsheet.model.Note;
import com.pascal91.duckandsheet.tasks.DatabaseAsyncTask;

import java.util.Date;

public class NoteViewActivity extends AppCompatActivity {

    EditText titleEdit;

    EditText contentEdit;

    int uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_view);

        Intent intent = getIntent();

        titleEdit = findViewById(R.id.noteTitleEditText);
        contentEdit = findViewById(R.id.noteContentEditText);

        titleEdit.setText(intent.getStringExtra(MainActivity.TITLE_STRING));
        contentEdit.setText(intent.getStringExtra(MainActivity.CONTENT_STRING));

        uid = intent.getIntExtra(MainActivity.UID, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_noteview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                MyDialogFragment dialogFragment = new MyDialogFragment();
                dialogFragment.show(this.getFragmentManager(), "span");
                return true;
            case R.id.save_file_action:

                if (titleEdit.getText().length() == 0) {
                    titleEdit.setText(DateFormat.format("dd-MM-yyyy HH:mm:ss", new Date()));
                }

                AppDatabase db = AppDatabase.getInstance(NoteViewActivity.this);
                DatabaseAsyncTask task = new DatabaseAsyncTask(db, new Note(uid, titleEdit.getText().toString(), contentEdit.getText().toString()));
                task.execute();


                Toast.makeText(NoteViewActivity.this, "Сохранено", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(NoteViewActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
