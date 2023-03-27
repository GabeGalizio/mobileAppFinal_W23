package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.HandlerCompat;
import androidx.room.Room;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
Button addChar;
private ExecutorService execSer= Executors.newSingleThreadExecutor();
private Handler handler= HandlerCompat.createAsync(Looper.getMainLooper());
private CharDB db;
private ListAdapter listAdapter;
private String charName=" ";
ListView charList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addChar=findViewById(R.id.addChar);
        charList=findViewById(R.id.lv_chars);
        db= Room.databaseBuilder(getApplicationContext(), CharDB.class,"charDB").fallbackToDestructiveMigration().allowMainThreadQueries().build();



    }
    public void saveData(View view){
        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Add new Character");
        final EditText input=new EditText(MainActivity.this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        builder.setView(input);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                charName=input.getText().toString();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();

            }
        });
        builder.show();

        Character chara=new Character();
        if(charName!=null)
         chara.setCharName(charName);
        else
            chara.setCharName("wrong!");
        chara.setCp(0);
        chara.setSp(0);
        chara.setGp(0);
        chara.setPp(0);
        execSer.execute(new Runnable() {
            @Override
            public void run() {
                long id=db.charDao().insert(chara);
                List<Character> characterList=db.charDao().getAllChars();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (id>0){
                            Toast.makeText(MainActivity.this, "Character creation success!",Toast.LENGTH_SHORT);
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Character creation failed.",Toast.LENGTH_SHORT);
                        }
                        listAdapter=new ListAdapter(MainActivity.this, characterList);
                        charList.setAdapter(listAdapter);
                    }
                });
            }
        });
    }

}