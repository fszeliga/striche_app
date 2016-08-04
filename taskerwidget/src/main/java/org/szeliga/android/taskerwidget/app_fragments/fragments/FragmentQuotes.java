package org.szeliga.android.taskerwidget.app_fragments.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.szeliga.android.taskerwidget.NavigationActivity;
import org.szeliga.android.taskerwidget.R;
import org.szeliga.android.taskerwidget.app_fragments.viewadapters.FragmentQuotesViewAdapter;
import org.szeliga.android.taskerwidget.app_fragments.viewadapters.FragmentUsersViewAdapter;
import org.szeliga.android.taskerwidget.app_fragments.viewadapters.FragmentViewAdapterAbstract;
import org.szeliga.android.taskerwidget.strokes.Quote;
import org.szeliga.android.taskerwidget.strokes.StrokesDataModel;
import org.szeliga.android.taskerwidget.strokes.User;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link FragmentUsers.OnListFragmentInteractionListener}
 * interface.
 */
public class FragmentQuotes extends FragmentAbstract {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count-1";
    private User user = null;
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private FragmentQuotes.OnListFragmentInteractionListener mListener;
    private boolean userQuotes = false;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FragmentQuotes(OnListFragmentInteractionListener mListener) {
        this.mListener = mListener;
    }

    public FragmentQuotes(OnListFragmentInteractionListener mListener, User item) {
        this.mListener = mListener;
        userQuotes = true;
        this.user = item;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_quotes_list, container, false);
        Log.d(this.toString(),"on create view");
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            //TODO make sure the items come from me!!!
            if(userQuotes){
                fragmentAdapter = new FragmentQuotesViewAdapter(StrokesDataModel.getInstance().getUserQuotes(this.user), mListener);
                userQuotes = false;
            }
            else
                fragmentAdapter = new FragmentQuotesViewAdapter(StrokesDataModel.getInstance().getAllQuotes(), mListener);
            recyclerView.setAdapter(fragmentAdapter);
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Quote item);
    }

    public void showUserQuotes(User u){
        fragmentAdapter.changeDataSet(StrokesDataModel.getInstance().getUserQuotes(u));
    }
}
