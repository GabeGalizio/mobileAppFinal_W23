package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

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
        LinearLayout parent = findViewById(R.id.currencies_row);
        /* If we're editing currencies dynamically then this should be a for loop for each currency
            instead */
        CurrencyView cv = new CurrencyView(this);
        parent.addView(cv);
        cv.setLabelText("Test");
        cv.setEditText("1");
        CurrencyView cv2 = new CurrencyView(this);
        parent.addView(cv2);
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
}

