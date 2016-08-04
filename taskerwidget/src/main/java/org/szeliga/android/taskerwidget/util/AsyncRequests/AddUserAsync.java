package org.szeliga.android.taskerwidget.util.AsyncRequests;

import android.app.Notification;
import android.os.AsyncTask;

import org.szeliga.android.taskerwidget.R;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Filip on 8/5/2016.
 */
public class AddUserAsync extends AsyncTask<Void, Void,Boolean> {
    private final String mUsername;
    private AsyncCallback mListener;

    public interface AsyncCallback {
        public void myMethod(boolean result);
    }

    public  AddUserAsync(String username ,AsyncCallback mListener){
        this.mListener = mListener;
        this.mUsername = username;
    }

    protected Boolean doInBackground(Void... username) {
        try {
            URL url = new URL("http://szeliga.org/striche/android/add_user.php");
            HttpURLConnection conn =(HttpURLConnection) url.openConnection(); //remember to close
            //conn.setReadTimeout(10000);
            //conn.setConnectTimeout(15000);
            //conn.setDoInput(true);
            conn.setDoOutput(true);
            // Write data to Server
            //exctract parameters
            //encode parameters
            String data  = URLEncoder.encode("username", "UTF-8")+ "=" + URLEncoder.encode(mUsername, "UTF-8");
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes( data );
            wr.flush();
            wr.close();

            InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mListener.myMethod(true);

        return true;
    }

}
