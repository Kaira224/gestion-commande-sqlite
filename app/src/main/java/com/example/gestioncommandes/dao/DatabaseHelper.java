package com.example.gestioncommandes.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.gestioncommandes.activities.CommandeActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseHelper extends SQLiteOpenHelper {

    static String name = "database";
    static int version = 2;

    String createTableUser = "CREATE TABLE if not exists `user` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, `username` TEXT," +
            " `password` TEXT, `gender` TEXT)";
    String createTableCommande = "CREATE TABLE if not exists `commande` (`code` INTEGER PRIMARY KEY, `libelle` TEXT," +
            "`date` TEXT)";
    String createTableComposition = "CREATE TABLE if not exists `composition` (`code` INTEGER PRIMARY KEY, `cmd` INTEGER,`libelle` TEXT," +
            "`quantite` INTEGER)";

    public DatabaseHelper(@Nullable Context context) {
        super(context, name, null, version);
        getWritableDatabase().execSQL(createTableUser);
        /*getWritableDatabase().execSQL(createTableCommande);
        getWritableDatabase().execSQL(createTableComposition);*/
    }

    public void insertUser (ContentValues contentValues){
        getWritableDatabase().insert("user", null, contentValues);
    }
    public boolean insertCommande (ContentValues contentValues){
        Integer id = (Integer) contentValues.get("code");
        String sql = "SELECT COUNT(*) FROM commande WHERE code=" + id;
        SQLiteStatement statement = getReadableDatabase().compileStatement(sql);
        long l = statement.simpleQueryForLong();
        statement.close();
        if(l == 1){
            return false;
        }else{
            getWritableDatabase().insert("commande", null, contentValues);
            return true;
        }
    }

    public boolean insertComposition (ContentValues contentValues){
        Integer id = (Integer) contentValues.get("code");
        String sql = "SELECT COUNT(*) FROM composition WHERE code=" + id;
        SQLiteStatement statement = getReadableDatabase().compileStatement(sql);
        long l = statement.simpleQueryForLong();
        statement.close();
        if(l == 1){
            return false;
        }else{
            getWritableDatabase().insert("composition", null, contentValues);
            return true;
        }
    }

    public boolean isLoginValid (String username, String password){
        String sql = "SELECT COUNT(*) FROM user WHERE username = '" + username +
                "' AND password = '" + password + "'";
        SQLiteStatement statement = getReadableDatabase().compileStatement(sql);
        long l = statement.simpleQueryForLong();
        statement.close();

        if(l == 1){
            return true;
        }else{
            return false;
        }
    }

    public long lastRow(){
        String sql = "SELECT * FROM commande ORDER BY code DESC LIMIT 1";
        SQLiteStatement statement = getReadableDatabase().compileStatement(sql);
        long l = statement.simpleQueryForLong();
        statement.close();
        return l;
    }

    public long totalArticles(int idCmd){
        long total;
        String compositionCount = "SELECT COUNT(*) FROM composition where cmd="+idCmd;
        SQLiteStatement statement = getReadableDatabase().compileStatement(compositionCount);
        total = statement.simpleQueryForLong();
        statement.close();
        return total;
    }

    public String cmdLibelle(int idCmd){
        String lib;
        String commandName = "SELECT libelle FROM commande where code="+idCmd;
        SQLiteStatement statement = getReadableDatabase().compileStatement(commandName);
        lib = statement.simpleQueryForString();
        statement.close();
        return lib;
    }

    public ArrayList<HashMap<String, String>> getArticles(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> articlesList = new ArrayList<>();
        String query = "SELECT * FROM composition";
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> article = new HashMap<>();
            article.put("code",cursor.getString(cursor.getColumnIndex("code")));
            article.put("lib",cursor.getString(cursor.getColumnIndex("libelle")));
            article.put("qte",cursor.getString(cursor.getColumnIndex("quantite")));
            articlesList.add(article);
        }
        return  articlesList;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTableCommande);
        db.execSQL(createTableComposition);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if exist
        db.execSQL("DROP TABLE IF EXISTS commande");
        db.execSQL("DROP TABLE IF EXISTS composition");
        // Create tables again
        onCreate(db);
    }
}
