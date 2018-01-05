package com.wafihasan.truthordare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity
{
    EditText editText = (EditText)findViewById(R.id.editText);
    TextView result = (TextView)findViewById(R.id.result);
    Random r;
    String t = "Truth";
    String d = "Dare";

    DatabaseHandler databaseHandler = new DatabaseHandler(this, null, null,1);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String db = databaseHandler.getTableAsString();
        result.setText(db);
        editText.setText("");
    }

    public void playit(View view)
    {
        int ran = r.nextInt(2);
        String name = databaseHandler.databaseToString();
        if(ran == 1)
            result.setText(t+" : "+name);
        else if(ran == 0)
            result.setText(d+" : "+name);
    }

    public void printDatabase()
    {
        String db = databaseHandler.getTableAsString();
        result.setText(db);
        editText.setText("");
    }

    public void addName(View view)
    {
        namesclass names = new namesclass(editText.getText().toString());
        databaseHandler.addName(names);
        printDatabase();
    }

    public void deleteName(View view)
    {
        String string = editText.getText().toString();
        databaseHandler.deleteName(string);
        printDatabase();
    }
}
