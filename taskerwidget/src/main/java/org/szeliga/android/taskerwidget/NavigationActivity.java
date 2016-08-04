package org.szeliga.android.taskerwidget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.LayoutInflaterCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.context.IconicsLayoutInflater;

import org.szeliga.android.taskerwidget.app_fragments.AddUser;
import org.szeliga.android.taskerwidget.app_fragments.fragments.FragmentAbstract;
import org.szeliga.android.taskerwidget.app_fragments.fragments.FragmentCharts;
import org.szeliga.android.taskerwidget.app_fragments.fragments.FragmentQuotes;
import org.szeliga.android.taskerwidget.app_fragments.fragments.FragmentUsers;
import org.szeliga.android.taskerwidget.app_fragments.viewadapters.BaseAdapter;
import org.szeliga.android.taskerwidget.strokes.Quote;
import org.szeliga.android.taskerwidget.strokes.StrokesDataModel;
import org.szeliga.android.taskerwidget.strokes.User;

public class NavigationActivity extends AppCompatActivity implements StrokesDataModel.AsyncDataResponse, FragmentQuotes.OnListFragmentInteractionListener, FragmentUsers.OnListFragmentInteractionListener, NavigationView.OnNavigationItemSelectedListener{

    private Menu menu;

    private FragmentAbstract currentFragment;
    private static final int RESULT_ADD_USER = 1;

    private View mainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory(getLayoutInflater(), new IconicsLayoutInflater(getDelegate()));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        this.mainView = this.findViewById(android.R.id.content).getRootView();//TODO maybe chenge to the view passed by onClick

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentFragment instanceof FragmentUsers) {
                    Intent i = new Intent(NavigationActivity.this, AddUser.class);
                    startActivityForResult(i, RESULT_ADD_USER);
                }
                else if(currentFragment instanceof FragmentQuotes)
                    Snackbar.make(view, "Quotes", Snackbar.LENGTH_LONG).setAction("Action", null).show();//can add onClick here
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            /**
             * Called when a drawer has settled in a completely closed state.
             */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getActionBar().setTitle(mTitle);
                //invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /**
             * Called when a drawer has settled in a completely open state.
             */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getActionBar().setTitle(mDrawerTitle);
                //invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        StrokesDataModel.getInstance().initialize(this, this);

        replaceFragment(new FragmentCharts());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation, menu);
        this.menu = menu;

        menu.findItem(R.id.action_sort_users).setVisible(false);
        menu.findItem(R.id.action_sort_quotes).setVisible(false);

        int c = getResources().getColor(R.color.colorAccent);
        menu.findItem(R.id.action_sort_users).setIcon(new IconicsDrawable(this, FontAwesome.Icon.faw_sort).actionBar().color(c));
        menu.findItem(R.id.action_sort_users_name_asc).setIcon(new IconicsDrawable(this, FontAwesome.Icon.faw_sort_alpha_asc).actionBar().color(c));
        menu.findItem(R.id.action_sort_users_name_desc).setIcon(new IconicsDrawable(this, FontAwesome.Icon.faw_sort_alpha_desc).actionBar().color(c));
        menu.findItem(R.id.action_sort_users_id_asc).setIcon(new IconicsDrawable(this, FontAwesome.Icon.faw_sort_numeric_asc).actionBar().color(c));
        menu.findItem(R.id.action_sort_users_id_desc).setIcon(new IconicsDrawable(this, FontAwesome.Icon.faw_sort_numeric_desc).actionBar().color(c));
        menu.findItem(R.id.action_sort_users_quotes_asc).setIcon(new IconicsDrawable(this, FontAwesome.Icon.faw_sort_amount_asc).actionBar().color(c));
        menu.findItem(R.id.action_sort_users_quotes_desc).setIcon(new IconicsDrawable(this, FontAwesome.Icon.faw_sort_amount_desc).actionBar().color(c));

        menu.findItem(R.id.action_sort_quotes).setIcon(new IconicsDrawable(this, FontAwesome.Icon.faw_sort).actionBar().color(c));
        menu.findItem(R.id.action_sort_quotes_name_asc).setIcon(new IconicsDrawable(this, FontAwesome.Icon.faw_sort_alpha_asc).actionBar().color(c));
        menu.findItem(R.id.action_sort_quotes_name_desc).setIcon(new IconicsDrawable(this, FontAwesome.Icon.faw_sort_alpha_desc).actionBar().color(c));
        menu.findItem(R.id.action_sort_quotes_date_asc).setIcon(new IconicsDrawable(this, FontAwesome.Icon.faw_sort_numeric_asc).actionBar().color(c));
        menu.findItem(R.id.action_sort_quotes_date_desc).setIcon(new IconicsDrawable(this, FontAwesome.Icon.faw_sort_numeric_desc).actionBar().color(c));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if(id == R.id.action_sort_users || id == R.id.action_sort_quotes){
            return true;
        }else if(id == R.id.action_sort_users_name_asc || id == R.id.action_sort_quotes_name_asc){
            currentFragment.notifyFragmentSortChange(BaseAdapter.SortType.NAME_ASCENDING);
        }else if(id == R.id.action_sort_users_name_desc || id == R.id.action_sort_quotes_name_desc){
            currentFragment.notifyFragmentSortChange(BaseAdapter.SortType.NAME_DESCENDING);
        }else if(id == R.id.action_sort_users_id_asc){
            currentFragment.notifyFragmentSortChange(BaseAdapter.SortType.ID_ASCENDING);
        }else if(id == R.id.action_sort_users_id_desc){
            currentFragment.notifyFragmentSortChange(BaseAdapter.SortType.ID_DESCENDING);
        }else if(id == R.id.action_sort_users_quotes_asc){
            currentFragment.notifyFragmentSortChange(BaseAdapter.SortType.STROKES_ASCENDING);
        }else if(id == R.id.action_sort_users_quotes_desc){
            currentFragment.notifyFragmentSortChange(BaseAdapter.SortType.STROKES_DESCENDING);
        }else if(id == R.id.action_sort_quotes_date_asc){
            currentFragment.notifyFragmentSortChange(BaseAdapter.SortType.DATE_ASCENDING);
        }else if(id == R.id.action_sort_quotes_date_desc){
            currentFragment.notifyFragmentSortChange(BaseAdapter.SortType.DATE_DESCENDING);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //TODO check how many fragments are created!!! can be that through using "placeholder" that it doesn't really change them
        int id = item.getItemId();

        if (id == R.id.nav_charts) {
            menu.setGroupVisible(R.id.action_sort_quotes_group,false);
            menu.setGroupVisible(R.id.action_sort_users_group,false);
            replaceFragment(new FragmentCharts());//replaceFragment(fragmentCharts);
        } else if (id == R.id.nav_users) {
            menu.setGroupVisible(R.id.action_sort_quotes_group,false);
            menu.setGroupVisible(R.id.action_sort_users_group,true);
            replaceFragment(new FragmentUsers(this));
        } else if (id == R.id.nav_all_quotes) {
            menu.setGroupVisible(R.id.action_sort_quotes_group,true);
            menu.setGroupVisible(R.id.action_sort_users_group,false);
            replaceFragment(new FragmentQuotes(this));
        } else if (id == R.id.nav_manage) {
            menu.setGroupVisible(R.id.action_sort_quotes_group,false);
            menu.setGroupVisible(R.id.action_sort_users_group,false);
        } else if (id == R.id.nav_share) {
            StrokesDataModel.getInstance().syncChanges(this,this);
        } else if (id == R.id.nav_sync) {
            Toast.makeText(getApplicationContext(),"Syncing...",Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * callback from @StrokesDataModel that updated data
     **/
    @Override
    public void processFinish(String output) {
        Snackbar.make(findViewById(R.id.drawer_layout), "Sync successful",Snackbar.LENGTH_SHORT).show();
    }

    private void replaceFragment(FragmentAbstract fragmentType){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_placeholder, fragmentType);
        //TODO or ft.add(R.id.your_placeholder, new FooFragment());
        ft.commit();
        currentFragment = fragmentType;
    }

    @Override
    public void onListFragmentInteraction(User item) {
        replaceFragment(new FragmentQuotes(this, item));
    }

    @Override
    public void onListFragmentInteraction(Quote item) {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            Snackbar.make(mainView, data.getStringExtra("result"), Snackbar.LENGTH_LONG).setAction("Action", null).show();//can add onClick here
            Log.i("onActivityResult", data.getStringExtra("result"));
        }
        if(requestCode == Activity.RESULT_CANCELED){
            Snackbar.make(mainView, data.getStringExtra("result"), Snackbar.LENGTH_LONG).setAction("Action", null).show();//can add onClick here
            Log.i("onActivityResult cancel", data.getStringExtra("result"));
        }
    }
}
