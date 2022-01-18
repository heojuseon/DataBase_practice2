package com.example.database_practice2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {
    DataBaseHelper dbHelper;
    SQLiteDatabase database;

    EditText editname;
    EditText edithealth;
    EditText editcount;

    Button btnin;
    Button btnse;
    Button btnde;
    Button btnup;

    TextView textView;

    String tableName = "groupTBL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editname = findViewById(R.id.editName);
        edithealth = findViewById(R.id.editHealth);
        editcount = findViewById(R.id.editCount);

        textView = findViewById(R.id.result);
        createDatabase();
        createTable(tableName);

        btnin = findViewById(R.id.insert);
        btnin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertRecord(editname.getText().toString()
                        ,edithealth.getText().toString()
                        ,Integer.parseInt(editcount.getText().toString()));
            }
        });

        btnse = findViewById(R.id.query);
        btnse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                executeQuery();
            }
        });

        btnde = findViewById(R.id.delete);
        btnde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteRecord(editname.getText().toString()
                        ,edithealth.getText().toString()
                        ,Integer.parseInt(editcount.getText().toString()));
            }
        });

        btnup = findViewById(R.id.update);
        btnup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateRecord("허주선", "운ㄷ", 3);
            }
        });
    }

    private void createDatabase() {
        Log.d("DB","createDatabase 호출됨.");

        dbHelper = new DataBaseHelper(this);
        database = dbHelper.getWritableDatabase();

        Log.d("DB","데이터베이스 생성함 ");
    }

    private void createTable(String tableName) {
        if (database == null) {
            Log.d("DB","데이터베이스를 먼저 생성하세요.");
            return;
        }

        database.execSQL("create table if not exists " + tableName + "("
                + " _id integer PRIMARY KEY autoincrement, "
                + " name text, "
                + " health text, "
                + " count integer)");

    }

    private void insertRecord(String userName, String userHealth, int userCount) {
        if (database == null) {
            Log.d("DB","데이터베이스를 생성하세요.");
            return;
        }

        database.execSQL("insert into " + tableName
                + " (name, health, count) "
                + " values "
                + "('" + userName
                + "','" + userHealth
                + "'," + userCount
                + ")");
    }

    private void executeQuery() {

        Cursor cursor = database.rawQuery("select _id, name, health, count from "+tableName, null);
        int recordCount = cursor.getCount(); //레코드 개수

        for (int i = 0; i < recordCount; i++) {
            //for문 사용시, getCount()메소드를 이용해 전체 레코드 개수를 알아내어 moveToNext()메소드 사용

            cursor.moveToNext();    //cursor를 다음으로 이동

            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String health = cursor.getString(2);
            int count = cursor.getInt(3);

            textView.append(name + "\n" + health + "\n" + count + "\n");

        }

        cursor.close();
    }
    private void deleteRecord(String userName, String userHealth, int userCount){
        database.execSQL("DELETE FROM " + tableName + " WHERE name = '" + userName + "';");

    }
    private void updateRecord(String userName, String userHealth, int userCount){
        database.execSQL("UPDATE " + tableName + " SET health = '" + userHealth + "' WHERE name = '" + userName + "';");

    }
}