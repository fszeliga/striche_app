package org.szeliga.android.taskerwidget.strokes;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.szeliga.android.taskerwidget.NavigationActivity;
import org.szeliga.android.taskerwidget.util.JSON.JSONHttpReader;
import org.szeliga.android.taskerwidget.util.sqlite.StrokesDBHelper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Filip on 7/8/2016.
 */
public class StrokesDataModel implements JSONHttpReader.AsyncResponse{
    private static StrokesDataModel ourInstance = new StrokesDataModel();
    private JSONHttpReader reader;
    private List<User> users = new ArrayList<>();
    private AsyncDataResponse delegate = null;
    private boolean firstTimeStart = true;
    private Context context = null;
    private StrokesDBHelper dbHelper = null;

    public static StrokesDataModel getInstance() {
        return ourInstance;
    }

    private StrokesDataModel() {
    }

    public void initialize(AsyncDataResponse delegate, Context context){
        this.context = context;
        this.delegate = delegate;

        //dbHelper = new StrokesDBHelper(context);

        //TODO check settings if offline
        //TODO check if online allowed
        reader = new JSONHttpReader(this, context);
        Log.d("create reader","");
        if(firstTimeStart) {
            Log.d("first time start","");
            reader.execute();
            Log.d("reader exec done","");
        }
    }

    public void syncQuotes(){

    }

    public List<User> getAllUsers(){
        return this.users;
    }

    public List<Quote> getAllQuotes(){
        List<Quote> quotes = new ArrayList<>();
        for (User u : users){
            quotes.addAll(u.getQuotes());
        }
        return quotes;
    }

    public List<Quote> getUserQuotes(User user){
        for (User u : users){
            if(user.getId()==u.getId()) return u.getQuotes();
        }
        return null;
    }

    /**
     * Signal from JSONHttpReader that pulling from server is done, including the requested JSON as String
     * @param output
     *
     * This function then singals whoever requested the update
     */
    @Override
    public void processFinish(String output) {
        processFirstTimeData(output);
        //TODO process received JSON string
        delegate.processFinish("Success/Not");
        Log.d("processFinish","called in datamodel");
    }

    private void processFirstTimeData(String output) {
        try {
            JSONObject jsonObj = new JSONObject(output);
            JSONArray usersArray = jsonObj.getJSONArray("victims");
            JSONArray quotesArray = jsonObj.getJSONArray("idiot_sayings");
            Log.d("processFirstTimeData", "usersArray.length() " + usersArray.length());

            for(int i = 0; i < usersArray.length(); i++){

                JSONObject jsonUser = usersArray.getJSONObject(i);

                String n = jsonUser.getString("name");
                int id = jsonUser.getInt("id");
                int strokes = jsonUser.getInt("striche");

                List<Quote> quotesList = new ArrayList<>();
                for (int q = 0; q < quotesArray.length();q++){
                    JSONObject quoteJSON = quotesArray.getJSONObject(q);
                    Log.d("timestamp",quoteJSON.getString("timestamp"));
                    if(quoteJSON.getString("name").equals(n)){
                        quotesList.add(new Quote(
                                quoteJSON.getInt("quote_id"),
                                quoteJSON.getString("quote"),
                                quoteJSON.getString("name"),
                                quoteJSON.getString("timestamp")
                                                )
                        );
                    }
                }

                User u = new User(n,id,strokes,quotesList);
                Log.d("processFirstTimeData", "adding user" + n);
                this.users.add(u);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void syncChanges(AsyncDataResponse delegate, Context context) {
        this.delegate = delegate;
        reader = new JSONHttpReader(this, context);
        reader.execute();
    }

    public interface AsyncDataResponse {
        void processFinish(String output);
    }
}
