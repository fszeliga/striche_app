package org.szeliga.android.taskerwidget.util.views;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.Button;

/**
 * Created by Filip on 7/9/2016.
 */
public class ButtonFontawesome extends Button {
    public ButtonFontawesome(Context context) {
        super(context);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(),"fontawesome-webfont.ttf"));
    }
}
