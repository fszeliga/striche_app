package org.szeliga.android.taskerwidget.app_fragments.viewadapters;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.szeliga.android.taskerwidget.R;
import org.szeliga.android.taskerwidget.app_fragments.fragments.FragmentQuotes;
import org.szeliga.android.taskerwidget.app_fragments.fragments.FragmentUsers;
import org.szeliga.android.taskerwidget.strokes.Quote;

public class QuotesViewHolder extends BaseViewholder<Quote> {
    private final FragmentQuotes.OnListFragmentInteractionListener mListener;
    public TextView mQuote;
    public TextView mDate;
    public TextView mName;

    public QuotesViewHolder(ViewGroup view, FragmentQuotes.OnListFragmentInteractionListener mListener) {
        super(view, R.layout.fragment_item_quotes);
        this.mListener = mListener;
    }

    @Override
    protected void onBind(Quote item, int position) {

        mQuote = (TextView) itemView.findViewById(R.id.textview_quote);
        mDate = (TextView) itemView.findViewById(R.id.textView_quote_date);
        mName = (TextView) itemView.findViewById(R.id.textView_quote_name);

        mQuote.setText(mItem.getQuote());
        mName.setText(mItem.getName());
        mDate.setText(""+mItem.getDate());
    }

    @Override
    protected void onClick(View view, Quote item) {
        mListener.onListFragmentInteraction(item);
    }

    @Override
    protected boolean onLongClick(View view, Quote item) {
        return false;
    }

    @Override
    public String toString() {
        return super.toString() + " '" + mQuote.getText() + "'";
    }
}