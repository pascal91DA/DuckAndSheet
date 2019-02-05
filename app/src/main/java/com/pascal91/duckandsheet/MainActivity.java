package com.pascal91.duckandsheet;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pascal91.duckandsheet.db.AppDatabase;
import com.pascal91.duckandsheet.model.User;
import com.pascal91.duckandsheet.tasks.DatabaseAsyncTask;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.pascal91.duckandsheet.MESSAGE";

    AlertDialog.Builder ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            MyDialogFragment dialogFragment = new MyDialogFragment();
            dialogFragment.show(this.getFragmentManager(), "span");
        });

        AppDatabase db = AppDatabase.getInstance(MainActivity.this);

        LinearLayout myRoot = findViewById(R.id.linLayoutMain);

        for(User user: db.userDao().getAll()){
            TextView textView = new TextView(getApplicationContext());
            textView.setText(user.firstName + " " + user.lastName);

            textView.setOnClickListener(view -> Toast.makeText(
                    MainActivity.this, user.firstName + " " + user.lastName,
                    Toast.LENGTH_SHORT).show());

            myRoot.addView(textView);
        }

        DatabaseAsyncTask task = new DatabaseAsyncTask(db, new User("Станислав", "Поляков"));
        task.execute();

        Toast.makeText(MainActivity.this, "Пук", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.action_settings:
                Toast.makeText(MainActivity.this, "Настройки", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, NoteViewActivity.class);
                String message = "Test Стринг";//editText.getText().toString();
                intent.putExtra(EXTRA_MESSAGE, message);
                startActivity(intent);
                return true;
            case R.id.app_bar_search:
                Toast.makeText(MainActivity.this, "Поиск", Toast.LENGTH_LONG).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
