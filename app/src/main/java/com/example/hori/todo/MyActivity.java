package com.example.hori.todo;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class MyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);


        android.database.sqlite.SQLiteOpenHelper helper = new ToDoSQLiteOpenHelper(getApplicationContext());
        SQLiteDatabase todoDB = helper.getReadableDatabase();


        String[] from = new String[]{"formula"};
        int[] to = new int[]{R.id.formula};

        final Cursor cursor = todoDB.query(
                ToDoSQLiteOpenHelper.TODO_TABLE,
                new String[]{"_id","formula"}, null, null, null, null, "_id DESC");

        SimpleCursorAdapter cursorAdapter =
                new SimpleCursorAdapter(
                        this,
                        R.layout.row_database,
                        cursor,
                        from,
                        to,
                        0
                );

        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(cursorAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //editボタンで編集画面に遷移
    public void onClickEditButton(View view){
        switch (view.getId()){
            case R.id.button:
                Intent intent = new Intent(this,EditActivity.class);
                startActivity(intent);
                break;
        }
    }

    //+ボタンを押してTODO入力画面へ遷移
    public void onClickPlusButton(View view){
        switch (view.getId()){
            case R.id.button_plus:
                Intent intent = new Intent(this,PlusActivity.class);
                startActivity(intent);
                break;
        }
    }

}
