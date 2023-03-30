package com.example.final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
/* TODO: Load data? */
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
        //
        Cp = intents.getIntExtra("cp",0);
        Ep = intents.getIntExtra("ep",0);
        Gp = intents.getIntExtra("gp",0);
        Pp = intents.getIntExtra("pp",0);
        Sp = intents.getIntExtra("sp",0);
        System.out.println(Cp+" " +Ep+" "+ Gp+" "+ Pp+" "+ Sp);
        tv_name.setText(Cname);

        /* Add currencies as views.
            --> Why do this programmatically? If we are going to implement diff currency systems
                they may have different numbers of currencies/"coins"
            --> Maybe make these custom if we want to make them look nice? I know you can do custom
                views
         */
        LinearLayout parent = findViewById(R.id.currencies_layout);
        LinearLayout firstrow = findViewById(R.id.currencies_row);
        LinearLayout secondrow = new LinearLayout(this);
        secondrow.setGravity(Gravity.CENTER);
        secondrow.setOrientation(LinearLayout.HORIZONTAL);
        parent.addView(secondrow);
        /* If we're editing currencies dynamically then this should be a for loop for each currency
            instead. Make a new horizontal linear layout for every 3 or so currencies */
        CurrencyView cpCV = addCurrency("CP", Cp, this);
        cpCV.setEditText(Integer.toString(Cp));
        CurrencyView epCV = addCurrency("EP", Ep, this);
        epCV.setEditText(Integer.toString(Ep));
        CurrencyView gpCV = addCurrency("GP", Gp, this);
        gpCV.setEditText(Integer.toString(Gp));
        firstrow.addView(cpCV);
        firstrow.addView(epCV);
        firstrow.addView(gpCV);

        CurrencyView ppCV = addCurrency("PP", Pp, this);
        ppCV.setEditText(Integer.toString(Pp));
        CurrencyView spCV = addCurrency("SP", Sp, this);
        spCV.setEditText(Integer.toString(Sp));
        secondrow.addView(ppCV);
        secondrow.addView(spCV);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backHome = new Intent(CharacterActivity.this, MainActivity.class);
                backHome.putExtra("Name",Cname);
                Cp=Integer.parseInt(cpCV.getText());
                Ep=Integer.parseInt(epCV.getText());
                Gp=Integer.parseInt(gpCV.getText());
                Pp=Integer.parseInt(ppCV.getText());
                Sp=Integer.parseInt(spCV.getText());
                System.out.println(Cp+" " +Ep+" "+ Gp+" "+ Pp+" "+ Sp);
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

    // addCurrency - Just sets up a currency view for the given count, name. Maybe add to layout
    // programmatically as well
    public CurrencyView addCurrency(String cname, int count, @NonNull Context context) {
        CurrencyView cv = new CurrencyView(context);
        cv.setLabelText(cname);
        cv.setEditText(Integer.toString(count));
        return cv;
    }
}

