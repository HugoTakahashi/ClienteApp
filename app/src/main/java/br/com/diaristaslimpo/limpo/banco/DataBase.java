package br.com.diaristaslimpo.limpo.banco;

/**
 * Created by Adelson on 05/08/2015.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DataBase extends SQLiteOpenHelper {

    public DataBase(Context context){
        super(context,"Limpo",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Tabelas.getCreateLogin());
        db.execSQL(Tabelas.getCreateCliente());
        db.execSQL(Tabelas.getCreateEndereco());
        db.execSQL(Tabelas.getCreateTempDiarista());
        db.execSQL(Tabelas.getCreateHistorico());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
