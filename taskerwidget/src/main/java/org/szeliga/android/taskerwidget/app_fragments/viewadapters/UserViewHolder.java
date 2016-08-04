package org.szeliga.android.taskerwidget.app_fragments.viewadapters;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.szeliga.android.taskerwidget.R;
import org.szeliga.android.taskerwidget.app_fragments.fragments.FragmentUsers;
import org.szeliga.android.taskerwidget.strokes.User;

import butterknife.BindString;
import butterknife.BindView;

public class UserViewHolder extends BaseViewholder<User> {
    private final FragmentUsers.OnListFragmentInteractionListener mListener;
    @BindView(R.id.textview_listitem_users_name) TextView mName;
    public TextView mID;
    public TextView mStrokesCount;

    public UserViewHolder(ViewGroup itemView, FragmentUsers.OnListFragmentInteractionListener mListener) {
        super(itemView, R.layout.fragment_item_users);
        this.mListener = mListener;
    }

    @Override
    protected void onBind(User item, int position) {
        mItem = item;

        mName = (TextView) itemView.findViewById(R.id.textview_listitem_users_name);
        mID = (TextView)itemView.findViewById(R.id.textview_listitem_users_id);
        mStrokesCount=(TextView)itemView.findViewById(R.id.textview_listitem_users_count);
        mName.setText(mItem.getName());
        mStrokesCount.setText("" + mItem.getStrokesCount());
        mID.setText("" + mItem.getId());

    }

    @Override
    protected void onClick(View view, User item) {
        mListener.onListFragmentInteraction(item);
    }

    @Override
    protected boolean onLongClick(View view, User item) {
        return false;
    }


    @Override
    public String toString() {
        return super.toString() + " '" + mName.getText() + "'";
    }

}
