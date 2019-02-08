package com.pascal91.duckandsheet;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.pascal91.duckandsheet.db.AppDatabase;
import com.pascal91.duckandsheet.model.Note;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static final String UID = "com.pascal91.duckandsheet.UID";
    public static final String TITLE_STRING = "com.pascal91.duckandsheet.TITLE_STRING";
    public static final String CONTENT_STRING = "com.pascal91.duckandsheet.CONTENT_STRING";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NoteViewActivity.class);
            startActivity(intent);
        });

        AppDatabase db = AppDatabase.getInstance(MainActivity.this);

        LinearLayout myRoot = findViewById(R.id.linLayoutMain);

        List<Note> notes = db.noteDao().getAll();

        if(!notes.isEmpty()) {

            for (Note note : notes) {

                CardView card = new CardView(getApplicationContext());

                LayoutParams cardLayoutParams = new LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT
                );

                cardLayoutParams.setMargins(8, 0, 8, 16);

                card.setLayoutParams(cardLayoutParams);
                card.setContentPadding(8, 8, 8, 8);
                card.setCardBackgroundColor(note.contentColor);

//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    card.setBackground(getDrawable(R.drawable.ic_launcher_background));
//                }

                TextView noteTitleTextView = new TextView(getApplicationContext());
                TextView noteContentTextView = new TextView(getApplicationContext());

                noteTitleTextView.setTextColor(note.textColor);
                noteContentTextView.setTextColor(note.textColor);

                LinearLayout cardLinearLayout = new LinearLayout(getApplicationContext());

                cardLinearLayout.setOrientation(LinearLayout.VERTICAL);

                noteTitleTextView.setTypeface(Typeface.DEFAULT_BOLD);
                noteTitleTextView.setText(note.title);
                noteContentTextView.setText(note.content);

                noteTitleTextView.setLayoutParams(new LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.WRAP_CONTENT
                ));

                noteContentTextView.setLayoutParams(new LayoutParams(
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT
                ));

                cardLinearLayout.addView(noteTitleTextView);
                cardLinearLayout.addView(noteContentTextView);
                card.addView(cardLinearLayout);

                card.setOnClickListener(view -> {
                    Toast.makeText(MainActivity.this, note.title + "\n\n" + note.content, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MainActivity.this, NoteViewActivity.class);
                    intent.putExtra(MainActivity.TITLE_STRING, note.title);
                    intent.putExtra(MainActivity.CONTENT_STRING, note.content);
                    intent.putExtra(MainActivity.UID, note.uid);
                    startActivity(intent);
                });


                card.setOnLongClickListener(view -> {
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                    } else {
                        v.vibrate(500);
                    }

                    AlertDialog diaBox = askDeleteOption(note);
                    diaBox.show();

                    return true;
                });

                myRoot.addView(card);
            }
        }else{
            TextView textView = new TextView(getApplicationContext());
            textView.setText("Нет заметок");
            textView.setTextColor(Color.GRAY);
            myRoot.addView(textView);
        }
    }

    private AlertDialog askDeleteOption(Note note){
        return new AlertDialog.Builder(this)
                .setTitle("Удалить")
                .setMessage("Вы хотите удалить заметку: " + note.title + "?")
                .setIcon(R.drawable.ic_delete_icon)

                .setPositiveButton("Удалить", (dialog, whichButton) -> {
                    AppDatabase db = AppDatabase.getInstance(MainActivity.this);
                    db.noteDao().delete(note);
                    dialog.dismiss();
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                })
                .setNegativeButton("Отмена", (dialog, which) -> dialog.dismiss())
                .create();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            case R.id.app_bar_search:
                Toast.makeText(MainActivity.this, "Поиск", Toast.LENGTH_LONG).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
