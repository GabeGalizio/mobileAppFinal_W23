package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.nio.channels.InterruptedByTimeoutException;
import java.sql.Array;
import java.util.ArrayList;

public class CharacterActivity extends AppCompatActivity {

    Button btn;
    TextView tv_name;

    int Id, Cp, Ep, Gp, Pp, Sp;
    String Cname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);
        Intent intents = getIntent();

        btn = findViewById(R.id.backToHome);
        tv_name = findViewById(R.id.charName);

        Cname = intents.getStringExtra("Name");
        Id = intents.getIntExtra("ID",0);
        Cp = intents.getIntExtra("cp",0);
        Ep = intents.getIntExtra("ep",0);
        Gp = intents.getIntExtra("gp",0);
        Pp = intents.getIntExtra("pp",0);
        Sp = intents.getIntExtra("sp",0);

        tv_name.setText(Cname);

        /* Add currencies as views.
            --> Why do this programmatically? If we are going to implement diff currency systems
                they may have different numbers of currencies/"coins"
            --> Maybe make these custom if we want to make them look nice? I know you can do custom
                views
         */
        LinearLayout parent = findViewById(R.id.currencies_layout);
        /* If we're editing currencies dynamically then this should be a for loop for each currency
            instead */
        // ArrayList<EditText> currencies = new ArrayList<EditText>();
        EditText cpET = new EditText(this);
        EditText epET = new EditText(this);
        EditText gpET = new EditText(this);
        EditText ppET = new EditText(this);
        EditText spET = new EditText(this);
        //currencies.forEach((n) -> parent.addView(n));
        parent.addView(cpET);
        parent.addView(epET);
        parent.addView(gpET);
        parent.addView(ppET);
        parent.addView(spET);
        cpET.setText(Integer.toString(Cp));
        epET.setText(Integer.toString(Ep));
        gpET.setText(Integer.toString(Gp));
        ppET.setText(Integer.toString(Pp));
        spET.setText(Integer.toString(Sp));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backHome = new Intent(CharacterActivity.this, MainActivity.class);
                backHome.putExtra("Name",Cname);
                backHome.putExtra("cp", Cp);
                backHome.putExtra("ep", Ep);
                backHome.putExtra("gp", Gp);
                backHome.putExtra("pp", Pp);
                backHome.putExtra("sp", Sp);
                backHome.putExtra("ID", Id);
                startActivity(backHome);
            }
        });


    }
    public void addCurrency(EditText et, int value) {

    }
}

