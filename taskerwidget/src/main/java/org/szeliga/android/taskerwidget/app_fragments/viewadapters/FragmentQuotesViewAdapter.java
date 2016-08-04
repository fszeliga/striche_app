package org.szeliga.android.taskerwidget.app_fragments.viewadapters;

import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.szeliga.android.taskerwidget.R;
import org.szeliga.android.taskerwidget.app_fragments.fragments.FragmentQuotes;
import org.szeliga.android.taskerwidget.strokes.Quote;
import org.szeliga.android.taskerwidget.strokes.StrokesDataModel;
import org.szeliga.android.taskerwidget.strokes.User;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Quote} and makes a call to the
 * specified {@link FragmentQuotes.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class FragmentQuotesViewAdapter extends BaseAdapter<Quote, QuotesViewHolder>{

    private final FragmentQuotes.OnListFragmentInteractionListener mListener;

    public FragmentQuotesViewAdapter(List<Quote> items, FragmentQuotes.OnListFragmentInteractionListener listener) {
        super();
        this.mListener = listener;
        this.items = new SortedList<>(Quote.class, new SortedList.Callback<Quote>() {
            @Override
            public int compare(Quote o1, Quote o2) {
                switch (currentSortType){
                    case NAME_ASCENDING:
                        return o1.getName().compareTo(o2.getName());
                    case NAME_DESCENDING:
                        return o2.getName().compareTo(o1.getName());
                    case DATE_ASCENDING:
                        return o1.getDate().compareTo(o2.getDate());
                    case DATE_DESCENDING:
                        return o2.getDate().compareTo(o1.getDate());
                    default:
                        return o1.getName().compareTo(o2.getName());
                 }

            }

            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                notifyItemRangeRemoved(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyItemMoved(fromPosition, toPosition);
            }

            @Override
            public void onChanged(int position, int count) {
                notifyItemRangeChanged(position, count);
            }

            @Override
            public boolean areContentsTheSame(Quote oldItem, Quote newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areItemsTheSame(Quote item1, Quote item2) {
                return item1.getId() == item2.getId();
            }
        });

        addAll(items);
    }

    @Override
    public QuotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item_quotes, parent, false);
        return new QuotesViewHolder(parent, mListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void notifyAdapterSortChange(SortType type) {
        this.currentSortType = type;
        this.items.clear();
        this.items.addAll(StrokesDataModel.getInstance().getAllQuotes());
    }

}
