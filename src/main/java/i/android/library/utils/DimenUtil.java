package i.android.library.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;

/**
 * Created by root on 12/25/17.
 */

public final class DimenUtil {
    public static float getDeviceHeight(Activity context){
        Display display =context.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }
    public static int pxToDp(Context context, int px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int dp = Math.round(px/displayMetrics.density+0.5f);
        return dp;
    }

    public static int dpToPx(Context context,int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * displayMetrics.density+0.5f);
        return px;
    }
}
