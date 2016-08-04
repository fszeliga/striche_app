package org.szeliga.android.taskerwidget.app_fragments.fragments;

import org.szeliga.android.taskerwidget.app_fragments.viewadapters.BaseAdapter;

/**
 * Created by Filip on 7/10/2016.
 */
public interface FragmentInterface{
    void notifyFragmentSortChange(BaseAdapter.SortType sortType);

}
