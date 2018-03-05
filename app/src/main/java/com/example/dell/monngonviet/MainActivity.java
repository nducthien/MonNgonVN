package com.example.dell.monngonviet;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.dell.monngonviet.adapter.AdapterDanhMuc;
import com.example.dell.monngonviet.model.DanhMuc;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends Activity {
    private SearchView searchView;
    final String DATABASE_NAME = "MonNgonVietNamS.sqlite";
    SQLiteDatabase database;
    ArrayAdapter<String> search;
    public ListView lvDanhSach;
    ArrayList<DanhMuc> list;
    AdapterDanhMuc adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvDanhSach = (ListView) findViewById(R.id.lvDanhSach);
        ArrayList<String> arrayFood = new ArrayList<>();
        arrayFood.addAll(Arrays.asList(getResources().getStringArray(R.array.arry_food)));

        search = new ArrayAdapter<>(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                arrayFood);
        lvDanhSach.setAdapter(search);

        addControls();
        readData();
        lvDanhSach.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putExtra("Name", list.get(i).getClass().toString());
                intent.putExtra("id", list.get(i).getMaDanhMuc() + "");
                Toast.makeText(getApplicationContext(), list.get(i).getTenDanhMuc() + "", Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                search.getFilter().filter(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void addControls() {
        lvDanhSach = (ListView) findViewById(R.id.lvDanhSach);
        list = new ArrayList<>();
        adapter = new AdapterDanhMuc(this, list);
        lvDanhSach.setAdapter(search);
    }

    private void readData() {
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM DanhMuc", null);
        list.clear();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            int MaDanhMuc = cursor.getInt(0);
            String TenDanhMuc = cursor.getString(1);
            list.add(new DanhMuc(MaDanhMuc, TenDanhMuc));
        }
        adapter.notifyDataSetChanged();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_tim:
                Toast.makeText(MainActivity.this, "Yêu thích", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, LikeActivity.class);
                startActivity(intent);
                // Gọi màn hình AditionSportActivity
                break;
            case R.id.mnInfor:
                intent = new Intent(MainActivity.this, menuActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.mymenu, menu);
    }
}
