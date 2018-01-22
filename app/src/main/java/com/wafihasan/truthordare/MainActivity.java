package com.wafihasan.truthordare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity
{
    EditText editText;
    TextView result;
    Random r = new Random();

    DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        result = findViewById(R.id.result);
        Button viewdb = findViewById(R.id.viewdb);
        Button add = findViewById(R.id.add);
        Button del = findViewById(R.id.delete);
        Button dropdb = findViewById(R.id.drop);
        Button play = findViewById(R.id.play);

        databaseHandler = new DatabaseHandler(this, null, null,1);
        String db = databaseHandler.getTableAsString();
        result.setText(db);
        editText.setText("");

        viewdb.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                printDatabase();
            }
        });

        play.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(result.getText().toString().length() != 0)
                    playit();
                else
                    Toast.makeText(getApplicationContext(),"Database Empty",Toast.LENGTH_LONG).show();

            }
        });

        add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (editText.getText().toString().length() != 0)
                    addName();
                else
                    Toast.makeText(getApplicationContext(),"Enter a name!",Toast.LENGTH_LONG).show();

            }
        });

        del.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (editText.getText().toString().length() != 0)
                    deleteName();
                else
                    Toast.makeText(getApplicationContext(),"Enter a name!",Toast.LENGTH_LONG).show();
            }
        });

        dropdb.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dropTable();
            }
        });
    }

    public void playit()
    {
        int ran = r.nextInt(2);
        String name = databaseHandler.databaseToString();
        if (ran == 1)
            result.setText("Truth : " + name);
        else if (ran == 0)
            result.setText("Dare : " + name);
    }

    public void printDatabase()
    {
        String db = databaseHandler.getTableAsString();
        result.setText(db);
        editText.setText("");
    }

    public void addName()
    {
        namesclass names = new namesclass(editText.getText().toString());
        databaseHandler.addName(names);
        printDatabase();
    }

    public void deleteName()
    {
        String string = editText.getText().toString();
        databaseHandler.deleteName(string);
        printDatabase();
    }

    public void dropTable()
    {
        databaseHandler.delete();
        Toast.makeText(getApplicationContext(),"Table Deleted",Toast.LENGTH_LONG).show();
        result.setText("");
    }
}