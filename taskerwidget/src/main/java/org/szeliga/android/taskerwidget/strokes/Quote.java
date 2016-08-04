package org.szeliga.android.taskerwidget.strokes;

import java.sql.Timestamp;

/**
 * Created by Filip on 7/8/2016.
 */
public class Quote {
    private int id = -1;
    private String quote = null;
    private String name = null;
    private String timestamp = null;

    public Quote(int id, String quote,String name, String timestamp) {
        this.id = id;
        this.quote = quote;
        this.name = name;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public String getQuote() {
        return quote;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return timestamp;
    }
}
