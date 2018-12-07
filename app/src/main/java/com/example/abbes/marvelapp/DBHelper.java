package com.example.abbes.marvelapp;
import java.util.ArrayList;
import java.util.HashMap;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Marvelfavoris.db";
    public static final String Marvel_TABLE_NAME = "testt ";
    public static final String Marvel_COLUMN_ID = "id";
    public static final String Marvel_COLUMN_NAME = "name";
    public static final String Marvel_COLUMN_URL = "url";


    private HashMap hp;

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
     if(check != -1) {
         return true;
     }
     else {
         return false ;
     }


    }

    public boolean getData(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+ Marvel_TABLE_NAME +"where name = '"+name+"'", null );
        if(res.moveToFirst()){
            return true;
        }

        else {
            return false ;
        }

    }





    public Integer deleteModel (String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(Marvel_TABLE_NAME,
                "name = ? ",
                new String[] { name });

    }

    public ArrayList<FavorisItem> getAllModels() {
        ArrayList<FavorisItem> array_list = new ArrayList<>();
        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + Marvel_TABLE_NAME, null );
        res.moveToFirst();

        while(!res.isAfterLast()){

            array_list.add(new FavorisItem(res.getString(res.getColumnIndex(Marvel_COLUMN_NAME)),

                    res.getString(res.getColumnIndex(Marvel_COLUMN_URL))));
            res.moveToNext();
        }
        return array_list;
    }
}