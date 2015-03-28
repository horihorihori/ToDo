package com.example.hori.todo;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;



public class PlusActivity extends Activity {

    private String todo;
    private SQLiteDatabase todoDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plus);

        SQLiteOpenHelper helper = new ToDoSQLiteOpenHelper(getApplicationContext());
        todoDB = helper.getWritableDatabase();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.plus, menu);
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

    public void onClickToDoButton(View view){
        EditText editText = (EditText) findViewById(R.id.editText);
        todo = editText.getText().toString();

        if(todo.equals("")) {
            //未入力
        }
        else {
            //データベースに保存
            ContentValues values = new ContentValues();
            values.put("formula", todo);
            todoDB.insert("todo", null, values);
        }


        Intent intent = new Intent(this,MyActivity.class);
        startActivity(intent);
        finish();

        editText.setText("");


    }
}
