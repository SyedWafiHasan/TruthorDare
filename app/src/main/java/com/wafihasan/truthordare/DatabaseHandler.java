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
        // next line not needed ever
        // sqLiteDatabase.execSQL(databaseName);


        // when you create a table, you should really include an ID column to keep track of entries,
        //make it integer primary key autoincrement. meaning that every entry into the table
        //this column will increase by 1 all by itself.

        String query = "CREATE TABLE " + tableName + "(_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + columnsName + " TEXT NOT NULL" + ");";
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
/*
        here you were trying to insert values into a column .. instead you want to populate
        the ContentValues with data (which you did) and insert those values into your TABLE -
        see you values have key value pair to put the name.getName() into column columnName
        once you have your values set to do what you want, insert into the table.


*/
        //  sqLiteDatabase.insert(columnsName, null, values);
        sqLiteDatabase.insert(tableName, null, values);
        sqLiteDatabase.close();
    }

    public void deleteName(String name)
    {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM "+tableName+" WHERE "+columnsName+" = '"+name+"';");
        sqLiteDatabase.close();
    }

    public String databaseToString()
    {
        String dbString = "";
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String query = "SELECT "+columnsName+" FROM "+tableName+" ORDER BY RANDOM() LIMIT 1;";
        //  String query = "SELECT * FROM "+tableName;
        Cursor c = sqLiteDatabase.rawQuery(query, null);
        c.moveToFirst();
        dbString = c.getString(0);
        if (dbString.length() == 0)
            dbString = "Database Empty";
        sqLiteDatabase.close();
        return dbString;
    }

    public String getTableAsString()
    {
        String db = "";
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        //I changed query, plus there is no column called name - I changed to your column variable

        //String query = "SELECT * FROM "+tableName+" WHERE 1;";

        String query = "SELECT * FROM "+tableName+";";
        Cursor c = sqLiteDatabase.rawQuery(query, null);

        //look at how I changed this

        /*
        c.moveToFirst();

        while (!c.isAfterLast())
        {
            if(c.getString(c.getColumnIndex("name"))!= null)
            {
                db += c.getString(c.getColumnIndex("name"));
                db += "\n";
            }
        }

        */

        if(c.moveToFirst() && c != null){
            do{
                db += c.getString(c.getColumnIndex(columnsName));
                db += "\n";
            }while(c.moveToNext());
        }
        sqLiteDatabase.close();
        return db;
    }
}