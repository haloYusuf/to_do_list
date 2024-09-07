package com.example.todolist.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.todolist.model.Content;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ToDo";
    public static final String TABLE_NAME = "content";
    public static final String ID_COLUMN = "id";
    public static final String DATA_COLUMN = "data";

    private static  final String CREATE_TABLE = String.format("CREATE TABLE IF NOT EXISTS %s"
            + " (%s INTEGER PRIMARY KEY AUTOINCREMENT, "
            + " %s TEXT NOT NULL)",
            TABLE_NAME,
            ID_COLUMN,
            DATA_COLUMN
    );

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addData(Content content){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DATA_COLUMN, content.getData());

        db.insert(TABLE_NAME, null, cv);
    }

    public List<Content> getAllData(){
        List<Content> listaAll = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (c.moveToFirst()){
            do {
                Content content = new Content();
                content.setId(Integer.parseInt(c.getString(0)));
                content.setData(c.getString(1));

                listaAll.add(content);
            }while (c.moveToNext());
        }
        return listaAll;
    }

    public int updateData(Content content){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DATA_COLUMN, content.getData());

        int data = db.update(TABLE_NAME, cv, "id = ?", new String[]{String.valueOf(content.getId())});
        return data;
    }

    public void deleteData(Content content){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, "id = ?", new String[]{String.valueOf(content.getId())});
        db.close();
    }
}
