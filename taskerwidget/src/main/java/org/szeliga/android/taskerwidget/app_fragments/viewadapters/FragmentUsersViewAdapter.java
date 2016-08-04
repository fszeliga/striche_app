package org.szeliga.android.taskerwidget.app_fragments.viewadapters;

import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import org.szeliga.android.taskerwidget.app_fragments.fragments.FragmentUsers;
import org.szeliga.android.taskerwidget.strokes.StrokesDataModel;
import org.szeliga.android.taskerwidget.strokes.User;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link User} and makes a call to the
 * specified {@link FragmentUsers.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class FragmentUsersViewAdapter extends BaseAdapter<User, UserViewHolder>{

    private final FragmentUsers.OnListFragmentInteractionListener mListener;

    public FragmentUsersViewAdapter(List<User> items, FragmentUsers.OnListFragmentInteractionListener listener) {
        super();
        this.mListener = listener;
        this.items = new SortedList<>(User.class, new SortedList.Callback<User>() {
            @Override
            public int compare(User o1, User o2) {
                switch (currentSortType){
                    case ID_ASCENDING:
                        return Integer.compare(o1.getId(),o2.getId());
                    case ID_DESCENDING:
                        return Integer.compare(o2.getId(),o1.getId());
                    case STROKES_ASCENDING:
                        return Integer.compare(o1.getStrokesCount(),o2.getStrokesCount());
                    case STROKES_DESCENDING:
                        return Integer.compare(o2.getStrokesCount(),o1.getStrokesCount());
                    case NAME_ASCENDING:
                        return o1.getName().compareTo(o2.getName());
                    case NAME_DESCENDING:
                        return o2.getName().compareTo(o1.getName());
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
            public boolean areContentsTheSame(User oldItem, User newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areItemsTheSame(User item1, User item2) {
                return item1.getId() == item2.getId();
            }
        });
        addAll(items);
    }

    @Override
    public final UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UserViewHolder(parent, mListener);
    }


    @Override
    public int getItemCount() {
        return this.items.size();
    }

    @Override
    public void notifyAdapterSortChange(SortType type){
        this.currentSortType = type;
        this.items.clear();
        this.items.addAll(StrokesDataModel.getInstance().getAllUsers());
    }

}
