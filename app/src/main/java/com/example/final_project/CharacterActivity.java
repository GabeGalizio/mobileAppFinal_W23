package com.example.final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/* TODO: Load data? */
public class CharacterActivity extends AppCompatActivity {

    Button btn, convertBtn;
    TextView tv_name;
    EditText amountFrom;
    TextView amountTo;
    Spinner currencyFrom, currencyTo;

    private CharDB db;

    int Id, Cp, Ep, Gp, Pp, Sp;
    String Cname, newName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);
        Intent intents = getIntent();

        db= Room.databaseBuilder(getApplicationContext(), CharDB.class,"charDB").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        List<Character> characterList = db.charDao().getAllChars();

        btn = findViewById(R.id.backToHome);
        convertBtn = findViewById(R.id.convertBtn);
        tv_name = findViewById(R.id.charName);
        amountFrom = findViewById(R.id.amountFrom);
        amountTo = findViewById(R.id.amountTo);
        currencyFrom = findViewById(R.id.currencyFrom);
        currencyTo = findViewById(R.id.currencyTo);

        Cname = intents.getStringExtra("Name");
        Id = intents.getIntExtra("ID",0);
        Character currentChar = null;
        for (Character c: characterList) {
            if(c.getId() == (Id)) {
                currentChar = c;
                break;
            }
        }
        Character finalCurrentChar = currentChar;

        // Fuck intents all my homies hate intents
        /* Cp = intents.getIntExtra("cp",0);
        System.out.println(Cp +" cp in chararters");
        Ep = intents.getIntExtra("ep",0);
        Gp = intents.getIntExtra("gp",0);
        Pp = intents.getIntExtra("pp",0);
        Sp = intents.getIntExtra("sp",0);
         */
        Cp = finalCurrentChar.getCp();
        Ep = finalCurrentChar.getEp();
        Gp = finalCurrentChar.getGp();
        Pp = finalCurrentChar.getPp();
        Sp = finalCurrentChar.getSp();

        System.out.println(Cp+" " +Ep+" "+ Gp+" "+ Pp+" "+ Sp);
        tv_name.setText(Cname);


        tv_name.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CharacterActivity.this);
                builder.setTitle("Change Name");
                final EditText input = new EditText(CharacterActivity.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                input.setText(Cname);
                builder.setView(input);
                builder.setPositiveButton("Change?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        newName = input.getText().toString();
                        if (newName.equals("")){
                            Toast.makeText(CharacterActivity.this, "Empty name not allowed", Toast.LENGTH_SHORT).show();
                        }else{
                            tv_name.setText(newName);
                            System.out.println(newName);
                            if(finalCurrentChar != null) {
                                    finalCurrentChar.setCharName(newName);
                                    db.charDao().update(finalCurrentChar);
                            }
                            Toast.makeText(CharacterActivity.this, "Named changed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
               builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                          dialogInterface.cancel();
                   }
               });
               builder.show();
               return false;
            }
        });



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
        CurrencyView cpCV = addCurrency("CP", Cp, this, finalCurrentChar);
        cpCV.setButtonText(Integer.toString(Cp));
        CurrencyView epCV = addCurrency("EP", Ep, this, finalCurrentChar);
        epCV.setButtonText(Integer.toString(Ep));
        CurrencyView gpCV = addCurrency("GP", Gp, this, finalCurrentChar);
        gpCV.setButtonText(Integer.toString(Gp));
        firstrow.addView(cpCV);
        firstrow.addView(epCV);
        firstrow.addView(gpCV);

        CurrencyView ppCV = addCurrency("PP", Pp, this, finalCurrentChar);
        ppCV.setButtonText(Integer.toString(Pp));
        CurrencyView spCV = addCurrency("SP", Sp, this, finalCurrentChar);
        spCV.setButtonText(Integer.toString(Sp));
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
                System.out.println("Name going back"+ Cname);
                backHome.putExtra("cp", Cp);
                backHome.putExtra("ep", Ep);
                backHome.putExtra("gp", Gp);
                backHome.putExtra("pp", Pp);
                backHome.putExtra("sp", Sp);
                backHome.putExtra("ID", Id);
                startActivity(backHome);
            }
        });

        // Currency conversion.
        convertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int amount = Integer.parseInt(amountFrom.getText().toString());

                String currency1 = currencyFrom.getSelectedItem().toString();
                String currency2 = currencyTo.getSelectedItem().toString();

                int factor = 0;
                // Get factor for converting currency 1 to currency 2.
                // How to handle remainder? Probably want the remainder as original currency?

                amountTo.setText(Integer.toString(amount * factor));

                // TODO: Get specific currency element?
            }
        });

    }

    // addCurrency - Just sets up a currency view for the given count, name. Maybe add to layout
    // programmatically as well
    public CurrencyView addCurrency(String cname, int count, @NonNull Context context, Character character) {
        CurrencyView cv = new CurrencyView(context);
        cv.setLabelText(cname);
        cv.setButtonText(Integer.toString(count));
        cv.setButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.AlertDialog.Builder builder=new android.app.AlertDialog.Builder(context);
                builder.setTitle("Enter Amount");
                final EditText input=new EditText(context);
                input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
                builder.setView(input);
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Update text
                        String text = input.getText().toString();
                        if(text.equals("")) {
                            text = "0";
                        }
                        cv.setButtonText(text);

                        //Update db entry
                        int value = Integer.parseInt(text);
                        if(character != null) {
                            // I'm sorry God for this switch case
                            switch(cname) {
                                case "cp":
                                    character.setCp(value);
                                    break;
                                case "ep":
                                    character.setEp(value);
                                    break;
                                case "gp":
                                    character.setGp(value);
                                    break;
                                case "pp":
                                    character.setPp(value);
                                    break;
                                case "sp":
                                    character.setSp(value);
                                    break;
                                default:
                                    break;
                            }
                            db.charDao().update(character);
                        }

                    }
                });
                builder.show();
            }
        });
        return cv;
    }
}

