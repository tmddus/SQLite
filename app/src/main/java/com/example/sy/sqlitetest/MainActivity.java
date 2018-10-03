package com.example.sy.sqlitetest;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    MyDBHelper myHelper;
    Button btnInit, btnInsert,btnSelect;
    EditText editName, editNumber, editNameResult, editNumberResult;
    SQLiteDatabase sqldb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("아이돌 그룹 관리 DB");
        myHelper = new MyDBHelper(this);
        btnInit = findViewById(R.id.btnInit);
        btnInsert = findViewById(R.id.btnInsert);
        btnSelect = findViewById(R.id.btnSelect);

        editName = findViewById(R.id.edit_name);
        editNumber =findViewById(R.id.edit_count);
        editNameResult = findViewById(R.id.edit_name_result);
        editNumberResult = findViewById(R.id.edit_number_result);

        sqldb =myHelper.getWritableDatabase();
        myHelper.onUpgrade(sqldb,1,2);
        sqldb.close();


        btnInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqldb=myHelper.getWritableDatabase();
                myHelper.onUpgrade(sqldb, 1, 2);
                sqldb.close();
            }
        });



        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(editName.length() != 0 && editNumber.length() != 0) {
                    sqldb = myHelper.getWritableDatabase();
                    sqldb.execSQL("INSERT INTO groupTBL values('" +
                            editName.getText().toString() + "', " +
                            editNumber.getText().toString() + ");");
                    sqldb.close();
                    refresh();
                    Toast.makeText(getApplicationContext(), "입력됨", Toast.LENGTH_SHORT).show();
                    editNumber.setText("");
                    editName.setText("");
                }else Toast.makeText(getApplicationContext(), "내용을 입력해주세요", Toast.LENGTH_SHORT).show();
            }
        });

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });


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

    void refresh(){
        sqldb = myHelper.getWritableDatabase();
        Cursor cursor;
        cursor = sqldb.rawQuery("select * from groupTBL;", null);

        String strNames = "그룹 이름" + "\r\n" + "--------" + "\r\n";
        String strNumbers = "인원" +  "\r\n" + "--------" + "\r\n";

        while(cursor.moveToNext()){
            strNames += cursor.getString(0)+ "\r\n";
            strNumbers += cursor.getString(1) + "\r\n";
        }

        editNameResult.setText(strNames);
        editNumberResult.setText(strNumbers);

        cursor.close();
        sqldb.close();
    }
}