package org.szeliga.android.taskerwidget.util.sqlite;

import android.provider.BaseColumns;

/**
 * Created by Filip on 7/7/2016.
 */
public final class StrokesContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public StrokesContract() {}

    /* Inner class that defines the table contents */
    public static abstract class Strokes implements BaseColumns {
        public static final String TABLE_NAME = "striche";
        public static final String COLUMN_NAME_USER_ID = "id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_COUNT = "striche";
        public static final String COLUMN_NAME_CONFIRM = "confirm";
    }

    /* Inner class that defines the table contents */
    public static abstract class StrokesQuotes implements BaseColumns {
        public static final String TABLE_NAME = "quotes";
        public static final String COLUMN_NAME_QUOTE_ID = "quote_id";
        public static final String COLUMN_NAME_USERNAME = "name";
        public static final String COLUMN_NAME_QUOTE = "quote";
        public static final String COLUMN_NAME_TIME = "time";
    }
}

