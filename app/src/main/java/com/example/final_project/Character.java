package com.example.final_project;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="charTable")
public class Character {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="charNum")
    private int id;
    @ColumnInfo(name="system")
    private String system;//either pf2e or dnd5e, tho we can accommodate for more freakish ttrpg systems that don't follow either of these's conversions
    @ColumnInfo(name="character")
    private String charName;
    @ColumnInfo(name="copper")
    private int cp;
    @ColumnInfo(name="silver")
    private int sp;
    @ColumnInfo(name="electrum")//could make this nullable later, but this is just the basics for now
    private int ep;
    @ColumnInfo(name="gold")
    private int gp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getCharName() {
        return charName;
    }

    public void setCharName(String charName) {
        this.charName = charName;
    }

    public int getCp() {
        return cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }

    public int getSp() {
        return sp;
    }

    public void setSp(int sp) {
        this.sp = sp;
    }

    public int getEp() {
        return ep;
    }

    public void setEp(int ep) {
        this.ep = ep;
    }

    public int getGp() {
        return gp;
    }

    public void setGp(int gp) {
        this.gp = gp;
    }
}
