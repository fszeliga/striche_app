package org.szeliga.android.taskerwidget.app_fragments.fragments;

import android.support.v4.app.Fragment;
import android.util.Log;

import org.szeliga.android.taskerwidget.app_fragments.viewadapters.BaseAdapter;
import org.szeliga.android.taskerwidget.strokes.User;

/**
 * Created by Filip on 7/9/2016.
 */
public class FragmentAbstract extends Fragment implements FragmentInterface{
    public BaseAdapter fragmentAdapter;

    @Override
    public void notifyFragmentSortChange(BaseAdapter.SortType sortType) {
        Log.d(this.toString(),"notifySort");
        if(fragmentAdapter == null)Log.d(this.toString(), "fragmentAdapter is null!!!");
        fragmentAdapter.notifyAdapterSortChange(sortType);
    }

}
