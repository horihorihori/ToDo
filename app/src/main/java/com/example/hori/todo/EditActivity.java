package com.example.hori.todo;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.AdapterView.AdapterContextMenuInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hori on 2015/01/01.
 */
public class EditActivity extends Activity {
    private static final int CONTEXT_MENU_ID_1 = 0 ;
    private static final int CONTEXT_MENU_ID_2 = 1 ;
    private SQLiteDatabase todoDB;

    //Adapterをあとで使うかもしれないので一応
    static ArrayAdapter<String> adapter;

    ListView listView;
    static List<Task> taskList = new ArrayList<Task>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);


        //DBからListViewつくってセット
        SQLiteOpenHelper helper = new ToDoSQLiteOpenHelper(getApplicationContext());
        todoDB = helper.getReadableDatabase();


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


        listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(cursorAdapter);


        //コンテキストメニュー使えるようにするやつ
        registerForContextMenu(listView);

        loadTask();

        }

    //taskListにDB情報を格納
    protected void loadTask(){
        taskList.clear();

        Cursor c = todoDB.query(
                ToDoSQLiteOpenHelper.TODO_TABLE,
                new String[]{"_id","formula"}, null, null, null, null, "_id DESC"
        );

        //暫定的にこのメソッドを使用あとで変更
        startManagingCursor(c);

        if(c.moveToFirst()){
            do {
                Task task = new Task(
                        c.getInt(c.getColumnIndex("_id")),
                        c.getString(c.getColumnIndex("formula"))
                );
                taskList.add(task);
            } while (c.moveToNext());
        }

        stopManagingCursor(c);

        /*
        Adapterを使っていないためAdapter関連のメソッドは使用せず
         */

    }

    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);

        menu.setHeaderTitle("コンテキストメニュー");

        menu.add(0, CONTEXT_MENU_ID_1, 0, "削除");

        loadTask();

    }

    //コンテキストメニューの操作
    public boolean onContextItemSelected(MenuItem item) {

        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();

        Task task = taskList.get(info.position);

        int taskId = task.getId();


        String str = "_id=" + Integer.toString(taskId);
        //削除押したらDBから消したい
        switch (item.getItemId()) {
            case CONTEXT_MENU_ID_1:
                //選ばれた場所を指定して削除
                todoDB.delete("todo", str, null);
                loadTask();


                String[] from = new String[]{"formula"};
                int[] to = new int[]{R.id.formula};

                Cursor cursor = todoDB.query(
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

                return true;

            default:
                return super.onContextItemSelected(item);
        }
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


    public void onClickEditButton2(View view){
        switch (view.getId()){
            case R.id.button_edit:
                Intent intent = new Intent(this,MyActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    public void onClickAllDeleteButton(View view){

        SQLiteOpenHelper helper = new ToDoSQLiteOpenHelper(getApplicationContext());
        todoDB = helper.getWritableDatabase();

        todoDB.delete("todo", null, null);

        String[] from = new String[]{"formula"};
        int[] to = new int[]{R.id.formula};

        Cursor cursor = todoDB.query(
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


}
