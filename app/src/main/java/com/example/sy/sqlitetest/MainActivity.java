package com.example.sy.sqlitetest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    MyDBHelper myHelper;
    Button btnInit, btnInsert,btnSelect;
    EditText editName, editNumder, editNameResult, editNumberResult;
    SQLiteDatabase sqldb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("아이돌 그룹 관리 DB");

        btnInit = findViewById(R.id.btnInit);
        btnInsert = findViewById(R.id.btnInsert);
        btnSelect = findViewById(R.id.btnSelect);

        editName = findViewById(R.id.edit_name);
        editNumder =findViewById(R.id.edtNumber);
        editNameResult = findViewById(R.id.edit_name_result);
        editNumberResult = findViewById(R.id.edit_number_result);

 


    }

    public class MyDBHelper extends SQLiteOpenHelper{
//   groupDB라는 이름의 DB생성

        public MyDBHelper(Context context) {
            super(context, "groupDB", null, 1);
        }

        //새 테이블 생성
        @Override
        public void onCreate(SQLiteDatabase db) {
                db.execSQL("Create table groupTBL (gName CHAR(40) PRIMARY KEY, gNumber INTEGER);");
        }
        //기존 테이블 삭제하고 새 테이블 생성
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS groupTBL");
            onCreate(db);
        }
    }
}
