package com.nfs.democours.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.nfs.democours.beans.Pony;
import com.nfs.democours.helper.TagLog;

import java.util.ArrayList;
import java.util.List;

public class SqlitePonyDao extends BaseDaoSqlite implements PonyDao {

    public SqlitePonyDao(Context context) {
        super(context);
    }

    @Override
    public List<Pony> getAll() {
        ArrayList<Pony> list = new ArrayList<Pony>();
        String[] cols = {Pony.ID, Pony.NAME, Pony.COULEUR, Pony.AGE};
        Log.d(TagLog.sql, "Pony get all");
        Cursor cursor = getDB().query(Pony.TABLE, cols, null, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            Pony pony = new Pony((int)cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3));
            list.add(pony);
            cursor.moveToNext();
        }
        cursor.close();     // obligatoire
        getDB().close();    // performance : on est dans une application qui ne vas pas taper dans le base de manière intensivement
        // puis retourner la liste de Ponys
        return list;
    }

    @Override
    public Pony get(int id) {
        Pony res = null;
        String[] cols = {Pony.ID, Pony.NAME, Pony.COULEUR, Pony.AGE};
        String[] params = { ""+id };
        Cursor cursor = getDB().query(Pony.TABLE, cols, "id=?", params, null, null, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {  // meilleur qu'un try catch
            res = new Pony((int)cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3));
            cursor.moveToNext();
        }
        cursor.close();
        getDB().close();
        return res;
    }

    @Override
    public void update(Pony pony) {
        // mapper pour modifier dans la table
        ContentValues values = mapFromPony(pony);
        // ajouter un paramètre dans la requête
        String[] params = { ""+pony.getId() };
        // executer la requête d'update
        getDB().update(Pony.TABLE, values, Pony.ID + " = ?", params);
        // puis fermer la connexion
        getDB().close();
    }

    @Override
    public int addPony(Pony pony) {
        // mapper pour insérer dans la table
        ContentValues values = mapFromPony(pony);
        // inserer ces valeurs dans la table
        int id = (int) getDB().insert(Pony.TABLE, null, values);
        // puis fermer la connexion
        getDB().close();
        return id;
    }

    private ContentValues mapFromPony(Pony pony){
        ContentValues values = new ContentValues();
        values.put(Pony.NAME, pony.getName());
        values.put(Pony.COULEUR, pony.getCouleur());
        values.put(Pony.AGE, pony.getAge());
        return values;
    }
}
