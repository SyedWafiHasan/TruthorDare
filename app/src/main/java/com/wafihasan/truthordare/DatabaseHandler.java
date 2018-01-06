package com.wafihasan.truthordare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper
{
    private static final int databaseVersion = 1;
    private static final String databaseName = "names.db";
    private static final String tableName = "namesTable";
    private static final String columnsName = "Names";

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, databaseName, factory, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL(databaseName);
        String query = "CREATE TABLE " + tableName + "(" + columnsName + " TEXT NOT NULL" + ");";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+tableName);
        onCreate(sqLiteDatabase);
    }

    public void delete()
    {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete("namesTable", "1", null);
    }

    public void addName(namesclass name)
    {
        ContentValues values = new ContentValues();
        values.put(columnsName, name.getName());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.insert(columnsName, null, values);
        sqLiteDatabase.close();
    }

    public void deleteName(String name)
    {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM "+tableName+" WHERE "+columnsName+" =\" "+name+"\";");
        sqLiteDatabase.close();
    }

    public String databaseToString()
    {
        String dbString = "";
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String query = "SELECT * FROM "+tableName+" ORDER BY RANDOM() LIMIT 1;";

        Cursor c = sqLiteDatabase.rawQuery(query, null);
        dbString = c.getString(0);
        sqLiteDatabase.close();
        return dbString;
    }

    public String getTableAsString()
    {
        String db = "";
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String query = "SELECT * FROM "+tableName+" WHERE 1;";
        Cursor c = sqLiteDatabase.rawQuery(query, null);
        c.moveToFirst();

        while (!c.isAfterLast())
        {
            if(c.getString(c.getColumnIndex("name"))!= null)
            {
                db += c.getString(c.getColumnIndex("name"));
                db += "\n";
            }
        }
        sqLiteDatabase.close();
        return db;
    }
}

