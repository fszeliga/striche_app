package org.szeliga.android.taskerwidget.strokes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Filip on 7/8/2016.
 */
public class User {
    private String name = null;
    private int id = -1;
    private int strokesCount = -1;
    private List<Quote> quotes = new ArrayList<>();

    public User(String name, int id, int strokesCount, List<Quote> quotes) {
        this.quotes = quotes;
        this.name = name;
        this.id = id;
        this.strokesCount = strokesCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStrokesCount() {
        return strokesCount;
    }

    public void setStrokesCount(int strokesCount) {
        this.strokesCount = strokesCount;
    }

    public List<Quote> getQuotes() {
        return quotes;
    }

    public void setQuotes(List<Quote> quotes) {
        this.quotes = quotes;
    }
}
