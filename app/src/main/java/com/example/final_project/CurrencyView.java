package com.example.final_project;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.room.Room;

import org.w3c.dom.Text;

public class CurrencyView extends CardView {
    private LinearLayout layout;
    private Button ctv;
    private TextView tv;

    public CurrencyView(@NonNull Context context) {
        super(context);
        layout = new LinearLayout(context);
        ctv = new Button(context);
        tv = new TextView(context);
        this.addView(layout);
        // CARD PARAMS
        this.setLayoutParams(new LayoutParams(300, 350));
        this.setCardBackgroundColor(Color.parseColor("#4A9A9999"));
        // LAYOUT PARAMS
        layout.setOrientation(LinearLayout.VERTICAL);
        ViewGroup.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // CURRENCY TEXT PARAMS
        ctv.setText("0");
        ctv.setTextSize(34);
        ctv.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        // TEXTVIEW PARAMS
        tv.setText("Currency Name");
        tv.setTextSize(18);
        tv.setTextAlignment(TEXT_ALIGNMENT_CENTER);

        // Adding views to layout
        layout.addView(ctv, params);
        layout.addView(tv, params);
    }

    public void setButtonText(String text) {
        this.ctv.setText(text);
    }

    public String getText(){
        return this.ctv.getText().toString();
    }

    public void setLabelText(String text) {
        this.tv.setText(text);
    }

    public String getCurrencyName() {
        return this.tv.getText().toString();
    }

    public void setButtonOnClickListener(View.OnClickListener listener) {
        ctv.setOnClickListener(listener);
    }
}
