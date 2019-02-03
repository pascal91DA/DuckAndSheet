package duckandsheet.pascal91.com;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "duckandsheet.pascal91.com.MESSAGE";

    SQLiteDatabase myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myDB = openOrCreateDatabase("my.db", MODE_PRIVATE, null);

        myDB.execSQL("CREATE TABLE IF NOT EXISTS user (name VARCHAR(200), age INT, is_single INT)");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ContentValues row1 = new ContentValues();
                row1.put("name", "Alice");
                row1.put("age", 25);
                row1.put("is_single", 1);

                ContentValues row2 = new ContentValues();
                row2.put("name", "Bob");
                row2.put("age", 20);
                row2.put("is_single", 0);

                myDB.insert("user", null, row1);
                myDB.insert("user", null, row2);

                Cursor myCursor = myDB.rawQuery("select name, age, is_single from user", null);

                while(myCursor.moveToNext()) {
                    String name = myCursor.getString(0);
                    int age = myCursor.getInt(1);
                    boolean isSingle = (myCursor.getInt(2)) == 1;

                    System.out.println(name + " (" + age + ") - " + isSingle);
                }

                myCursor.close();
                myDB.close();

                Intent intent = new Intent(MainActivity.this, NoteViewActivity.class);
                String message = "Test Стринг";//editText.getText().toString();
                intent.putExtra(EXTRA_MESSAGE, message);
                startActivity(intent);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
