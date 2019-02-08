package com.pascal91.duckandsheet;

import android.content.Intent;
import android.graphics.Color;
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
import java.util.List;

import yuku.ambilwarna.AmbilWarnaDialog;

public class NoteViewActivity extends AppCompatActivity {

    EditText titleEdit;

    EditText contentEdit;

    int uid;

    int textColor;

    int contentColor;

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

        AppDatabase db = AppDatabase.getInstance(NoteViewActivity.this);
        List<Note> notes = db.noteDao().loadAllByIds(new int[]{uid});

        if(!notes.isEmpty()) {
            Note note = notes.get(0);
            textColor = note.textColor;
            contentColor = note.contentColor;
        }else{
            textColor = Color.BLACK;
            contentColor = Color.WHITE;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_noteview, menu);
        return true;
    }

    private void openDialog(int color, boolean isText) {
        AmbilWarnaDialog dialog = new AmbilWarnaDialog(this, color, false, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {

                Toast.makeText(getApplicationContext(), String.valueOf(color), Toast.LENGTH_SHORT).show();

                if (isText) {
                    textColor = color;
                } else {
                    contentColor = color;
                }

            }

            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
                Toast.makeText(getApplicationContext(), "Action canceled!", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
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
                Note newNote = new Note(uid, titleEdit.getText().toString(), contentEdit.getText().toString());
                newNote.textColor = this.textColor;
                newNote.contentColor = this.contentColor;
                DatabaseAsyncTask task = new DatabaseAsyncTask(db, newNote);
                task.execute();

                Toast.makeText(NoteViewActivity.this, "Сохранено", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(NoteViewActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.text_color_action:
                openDialog(this.textColor, true);
                return true;
            case R.id.content_color_action:
                openDialog(this.contentColor, false);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
