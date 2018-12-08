package com.example.abbes.marvelapp.BaseDeDonnées;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import com.example.abbes.marvelapp.ClassObject.FavorisItem;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Marvelfavoris.db";
    public static final String Marvel_TABLE_NAME = "testt ";
    public static final String Marvel_COLUMN_ID = "id";
    public static final String Marvel_COLUMN_NAME = "name";
    public static final String Marvel_COLUMN_URL = "url";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table " + Marvel_TABLE_NAME  +
                        "(id integer primary key AUTOINCREMENT NOT NULL, name text NOT NULL UNIQUE, url text NOT NULL)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + Marvel_TABLE_NAME);
        onCreate(db);
    }

    public boolean Insertfavoris (String name,String url) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("url" ,  url);
        long check =   db.insert(Marvel_TABLE_NAME, null, contentValues);
        return check != -1;
    }

    public boolean getData(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+ Marvel_TABLE_NAME +"where name = '"+name+"'", null );
        return res.moveToFirst();

    }


    public Integer deleteModel (String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(Marvel_TABLE_NAME,
                "name = ? ",
                new String[] { name });

    }


    public List<FavorisItem> getAllModels() {
        List<FavorisItem> array_list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + Marvel_TABLE_NAME+" ORDER BY name", null );
        res.moveToFirst();

        while(!res.isAfterLast()){

            array_list.add(new FavorisItem(
                            res.getString(res.getColumnIndex(Marvel_COLUMN_NAME)),
                            res.getString(res.getColumnIndex(Marvel_COLUMN_URL))));
            res.moveToNext();
        }

        return array_list;
    }
    
}