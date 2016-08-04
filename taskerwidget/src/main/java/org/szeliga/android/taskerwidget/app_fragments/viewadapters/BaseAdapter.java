package org.szeliga.android.taskerwidget.app_fragments.viewadapters;

import android.content.DialogInterface;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.szeliga.android.taskerwidget.strokes.User;

import java.util.List;


/**
 * Created by Filip on 7/10/2016.
 */
public abstract class BaseAdapter<T, VH extends BaseViewholder<T>> extends RecyclerView.Adapter<VH>{
    public SortedList<T> items;

    SortType currentSortType = SortType.STROKES_DESCENDING;

    public enum SortType{
        ID_ASCENDING,ID_DESCENDING,STROKES_ASCENDING,STROKES_DESCENDING,NAME_DESCENDING,NAME_ASCENDING, DATE_ASCENDING,DATE_DESCENDING, DEFAULT
    }

    public void changeDataSet(List<T> items){
        this.items.beginBatchedUpdates();
        this.items.clear();
        this.items.endBatchedUpdates();
        this.addAll(items);
    }

    public void addAll(List<T> items) {
        this.items.beginBatchedUpdates();
        for (T item : items) {
            this.items.add(item);
        }
        this.items.endBatchedUpdates();
    }

    @Override
    public final void onBindViewHolder(VH vh, int position) {
        T item = items.get(position);
        vh.performBind(item, position);
    }

    public abstract void notifyAdapterSortChange(SortType sortType);
}
