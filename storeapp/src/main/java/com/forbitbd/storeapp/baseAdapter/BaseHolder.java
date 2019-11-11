package com.forbitbd.storeapp.baseAdapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseHolder<T,L extends BaseListener> extends RecyclerView.ViewHolder {
    private L listener;

    public BaseHolder(@NonNull View itemView) {
        super(itemView);
    }





    public BaseHolder(View itemView, L listener) {
        super(itemView);
        this.listener = listener;
    }

    public abstract void bind(T t);


    protected L getListener() {
        return listener;
    }
}
