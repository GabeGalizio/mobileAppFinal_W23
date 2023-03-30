package com.example.final_project;

import android.content.Context;
import android.graphics.Color;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

public class CurrencyView extends CardView {
    private LinearLayout layout;
    private EditText et;
    private TextView tv;

    public CurrencyView(@NonNull Context context) {
        super(context);
        layout = new LinearLayout(context);
        et = new EditText(context);
        tv = new TextView(context);
        this.addView(layout);
        // CARD PARAMS
        this.setLayoutParams(new LayoutParams(300, 350));
        this.setCardBackgroundColor(Color.parseColor("#4A9A9999"));
        // LAYOUT PARAMS
        layout.setOrientation(LinearLayout.VERTICAL);
        ViewGroup.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // EDIT TEXT PARAMS
        //et.setInputType(InputType.TYPE_NUMBER_VARIATION_NORMAL);
        et.setText("0");
        et.setTextSize(34);
        et.setTextIsSelectable(true);
        et.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        // TEXTVIEW PARAMS
        tv.setText("Currency Name");
        tv.setTextSize(18);
        tv.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        // Adding views to layout
        layout.addView(et, params);
        layout.addView(tv, params);
    }

    public void setEditText(String text) {
        this.et.setText(text);
    }

    public String getText(){
        return this.et.getText().toString();
    }

    public void setLabelText(String text) {
        this.tv.setText(text);
    }
}
