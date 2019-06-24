package com.example.carlos.casopratico1;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.provider.SyncStateContract;
import android.support.annotation.Nullable;

public class CriarDatabase extends SQLiteOpenHelper {
  private static final String NOME_DATABASE ="cidades.db";
  private static  final int DB_VERSION=1;

    public CriarDatabase(Context context) {
        super(context, NOME_DATABASE,null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
      if(db.isReadOnly()){

          db= getWritableDatabase();
      }

      db.execSQL("CREATE TABLE cidades("+BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+" cidade TEXT" + ");");
    }


    public void adicionar(SQLiteDatabase bd, String data){
        String x=data.toString();

        bd.execSQL("INSERT INTO cidades(cidade) VALUES('"+x+"')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
db.execSQL("DROP TABLE IF EXISTS cidades");
onCreate(db);

    }
}



