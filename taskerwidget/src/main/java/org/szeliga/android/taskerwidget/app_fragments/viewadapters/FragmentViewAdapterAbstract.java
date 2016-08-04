package org.szeliga.android.taskerwidget.app_fragments.viewadapters;

import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import org.szeliga.android.taskerwidget.strokes.User;


/**
 * Created by Filip on 7/10/2016.
 */
public abstract class FragmentViewAdapterAbstract extends RecyclerView.Adapter{

    public abstract class ViewHolderAbstract<T> extends RecyclerView.ViewHolder {
        public final View mView;
        private T item;

        public ViewHolderAbstract(View itemView) {
            super(itemView);
            mView = itemView;
        }

        protected final void performBind(T item, int position) {
            this.item = item;
            onBind(item, position);
        }

        protected T getItem() {
            return item;
        }

        protected abstract void onBind(T item, int position);
    }//ViewHolderAbstract class
}
