package org.szeliga.android.taskerwidget.app_fragments.viewadapters;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public abstract class BaseViewholder<T> extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener{
    public View mView;
    public T mItem;

    protected BaseViewholder(ViewGroup parent, @LayoutRes int layoutID) {
        super(LayoutInflater.from(parent.getContext()).inflate(layoutID, parent, false));
        mView = parent;

        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    protected final void performBind(T item, int position) {
        this.mItem = item;
        onBind(item, position);
    }

    protected T getItem() {
        return mItem;
    }

    protected abstract void onBind(T item, int position);

    protected abstract void onClick(View view, T item);
    protected abstract boolean onLongClick(View view, T item);

    @Override
    public final void onClick(View view) {
        onClick(view, mItem);
    }

    @Override public final boolean onLongClick(View view) {
        return onLongClick(view, mItem);
    }

}