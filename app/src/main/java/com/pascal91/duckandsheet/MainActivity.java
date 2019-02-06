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
            String message = "Test Стринг";
            intent.putExtra(MainActivity.CONTENT_STRING, message);
            startActivity(intent);
        });

        AppDatabase db = AppDatabase.getInstance(MainActivity.this);

        LinearLayout myRoot = findViewById(R.id.linLayoutMain);

        List<Note> notes = db.userDao().getAll();

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
                card.setCardBackgroundColor(
                        Color.rgb(
                                new Random().nextInt(255),
                                new Random().nextInt(255),
                                new Random().nextInt(255)
                        ));

                TextView noteTitleTextView = new TextView(getApplicationContext());
                TextView noteContentTextView = new TextView(getApplicationContext());

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
                    Toast.makeText(
                            MainActivity.this, note.uid + ":" + note.title + "\n\n" + "Зарегистрировано долгое нажатие!",
                            Toast.LENGTH_SHORT).show();
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                    } else {
                        v.vibrate(500);
                    }
                    return true;
                });

                myRoot.addView(card);
            }
        }else{
            TextView textView = new TextView(getApplicationContext());
            textView.setText("Нет заметок");
            myRoot.addView(textView);
        }
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
