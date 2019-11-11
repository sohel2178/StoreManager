package com.forbitbd.storeapp.baseAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T, L extends BaseListener,VH extends BaseHolder<T,L>> extends RecyclerView.Adapter<VH> {

    private List<T> items;
    private L listener;
    private LayoutInflater layoutInflater;


    public BaseAdapter(Context context, L listener) {
        this.listener = listener;
        this.items = new ArrayList<>();
        this.layoutInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public abstract VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        T t = items.get(position);
        holder.bind(t);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void add(T t){
        items.add(t);
        int position = items.indexOf(t);
        notifyItemInserted(position);
    }

    public void addToFirst(T t){
        items.add(0,t);
        notifyItemInserted(0);
    }

    public void addToposition(T t, int position){
        items.add(position,t);
        notifyItemInserted(position);
    }

    public void update(int position){

    }

    public void update(T t,int position){
        items.set(position,t);
        notifyItemChanged(position);
    }

    public void remove(int position){
        items.remove(position);
        notifyItemRemoved(position);
    }

    public void clear(){
        items.clear();
        notifyDataSetChanged();
    }

    public void setItems(List<T> items){
        this.items = items;
        notifyDataSetChanged();
    }

    public List<T> getItems(){
        return items;
    }

    public T getItem(int position){
        return items.get(position);
    }




    protected View inflate(@LayoutRes final int layout, final @Nullable ViewGroup parent){
        return layoutInflater.inflate(layout,parent,false);
    }


    public L getListener() {
        return listener;
    }

}
