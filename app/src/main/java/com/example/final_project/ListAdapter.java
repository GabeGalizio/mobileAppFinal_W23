package com.example.final_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter  extends BaseAdapter {
    private final Context context;
    private final List<Character> charList;
    private LayoutInflater inflater;
    public ListAdapter(Context context, List<Character> charList){
        this.context=context;
        this.charList=charList;
        inflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount(){
        return charList.size();
    }

    @Override
    public Object getItem(int i) {
        return charList.get(i);
    }

    @Override
    public long getItemId(int i){
        return i;
    }
    public void removeData(int index){
        charList.remove(index);
        notifyDataSetChanged();
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup){
        ViewHolder vh=null;
        if(view==null){
            view=inflater.inflate(R.layout.item_list, viewGroup, false);
            vh=new ViewHolder(view);
            view.setTag(vh);
        }
        else {
            vh=(ViewHolder) view.getTag();
        }
        Character chara=charList.get(i);
        vh.bindData(chara);
        return view;
    }
    private static class ViewHolder{
        private TextView label;
        public ViewHolder(View view){
            label=view.findViewById(R.id.item_data);
        }
        void bindData(Character chara){
            label.setText(chara.getCharName());
        }
    }
}
