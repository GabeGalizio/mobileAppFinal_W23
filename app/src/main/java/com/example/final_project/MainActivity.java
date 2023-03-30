package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.HandlerCompat;
import androidx.room.Room;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.BaseAdapter;
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
        List<Character> characterList = db.charDao().getAllChars();
        listAdapter=new ListAdapter(MainActivity.this, characterList);
        charList.setAdapter(listAdapter);
        charList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Character c = (Character) listAdapter.getItem(i);
                execSer.execute(new Runnable() {
                    @Override
                    public void run() {
                        int j = db.charDao().delete(c);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if(j > 0){
                                    Toast.makeText(MainActivity.this, "removed", Toast.LENGTH_SHORT).show();
                                    listAdapter.removeData(i);
                                }
                            }
                        });
                    }
                });
            }
        });

        charList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Character c = (Character) listAdapter.getItem(i);
                Intent edit_Class = new Intent(MainActivity.this, CharacterActivity.class);
                edit_Class.putExtra("Name",c.getCharName());
                edit_Class.putExtra("cp", c.getCp());
                System.out.println(c.getCp() +" cp before send");
                edit_Class.putExtra("ep", c.getEp());
                edit_Class.putExtra("gp", c.getGp());
                edit_Class.putExtra("pp", c.getPp());
                edit_Class.putExtra("sp", c.getSp());
                edit_Class.putExtra("ID", c.getId());
                startActivity(edit_Class);
                return false;

            }
        });
    }

    public void saveData(View view){
        //TODO
        // -add edit functionality (im just lazy for that one, its pretty easily copied from the in class example
        // & i'll do it later tonight if no one bothers)
        // -implement currency conversion (should be easy, we have the second screen for it already)
        // if you'd allow this ratty bitch to share their ideas, i think you can pass each Character as an extra through the intents
        // so just send it over and edit it on the character intent
        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Add new Character");
        final EditText input=new EditText(MainActivity.this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        builder.setView(input);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                charName=input.getText().toString();//this only goes through after you click add character a second time, need to fix the timing
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
                                    Toast.makeText(MainActivity.this, "Character creation success!",Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(MainActivity.this, "Character creation failed.",Toast.LENGTH_SHORT).show();
                                }
                                listAdapter=new ListAdapter(MainActivity.this, characterList);
                                charList.setAdapter(listAdapter);

                            }
                        });
                    }
                });
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();

            }
        });
        builder.show();


    }

    public void helpPopup(View view) {
        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        /*AlertDialog ad = builder.create();
        ad.getWindow().setLayout(); */
        builder.setTitle("Help");
        final TextView helpText = new TextView(MainActivity.this);
        helpText.setTextSize(20);
        helpText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        helpText.setText("Select ADD NEW CHARACTER to add a new character\nTAP a character to delete it\nLONG PRESS a character to edit a character");
        builder.setView(helpText);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();

            }
        });
        builder.show();
    }
    @Override
    public void onResume() {

        super.onResume();
        Intent intent=getIntent();
        Character chara=new Character();
        listAdapter.notifyDataSetChanged();
        chara.setCharName(intent.getStringExtra("Name"));
        chara.setCp(intent.getIntExtra("cp",0));
        chara.setSp(intent.getIntExtra("sp",0));
        chara.setGp(intent.getIntExtra("gp",0));
        chara.setEp(intent.getIntExtra("ep",0));
        chara.setPp(intent.getIntExtra("pp",0));
        chara.setId(intent.getIntExtra("ID",0));
        execSer.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("here");
                int j=db.charDao().update(chara);
            }
        });
    }

}